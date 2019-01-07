package com.ministere.cooperation.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ministere.cooperation.model.International.Etat;
import com.ministere.cooperation.model.National.DocumentNational;
import com.ministere.cooperation.model.National.GeneratePdfReport;
import com.ministere.cooperation.repo.NationalRepo;
import com.ministere.cooperation.repo.SearchRepository;

@Controller
@RequestMapping("/National")

public class NationalController {
	private static String UPLOADED_FOLDER = "/home/khalisl/Bureau/test/";

	@Autowired
	private NationalRepo nr;
	@Autowired
	private SearchRepository sr;

	@RequestMapping("")
	public ModelAndView home() {
		List<DocumentNational> documents = nr.listerDocuments();
		ModelAndView mv = new ModelAndView("National");
		mv.addObject("documents", documents);
		DocumentNational filtre = new DocumentNational();
		mv.addObject("filter", filtre);
		mv.addObject("document", new DocumentNational());
		mv.addObject("count", nr.count());
		return mv;
	}

	@RequestMapping("/filter")
	public ModelAndView filtrer(DocumentNational filter) {
		List<DocumentNational> documents = sr.search(filter);
		ModelAndView mv = new ModelAndView("National");
		mv.addObject("documents", documents);
		mv.addObject("filter", filter);
		mv.addObject("document", new DocumentNational());
		return mv;
	}

	@RequestMapping("/save")
	public String save(@ModelAttribute("document") @Valid DocumentNational doc, BindingResult br,
			@RequestParam("file") MultipartFile file) {
		if (!br.hasErrors()) {

			if (file.isEmpty()) {
				System.out.println("looooooooooooooooooooooooooooooooooooooooooooooooooool");
				return "redirect:/National?failed";
			}
			String type;
			try {
				type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
						file.getOriginalFilename().length());
			} catch (Exception e) {
				type = "";
			}
			String newFileName = doc.getType() + "--" + doc.getDateSignature() + "-" + Instant.now().toEpochMilli()
					+ type;
			try {

				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + newFileName);
				Files.write(path, bytes);

			} catch (IOException e) {
				e.printStackTrace();
			}
			doc.setFileName(newFileName);
			nr.save(doc);

			return "redirect:/National?success";
		}
		System.out.println(br.getAllErrors());
		return "redirect:/National?failed";
	}

	@RequestMapping("/renouvler/{id}")
	public String renouveler(@PathVariable("id") int id) {
		Optional<DocumentNational> docOp = nr.findById(id);
		if (docOp.isPresent()) {
			DocumentNational doc = docOp.get();
			LocalDate dateDebut = doc.getDateSignature();
			LocalDate dateFin = doc.getDateExpiration();
			doc.setDateSignature(dateFin);
			doc.setDateExpiration(dateFin.plusDays(ChronoUnit.DAYS.between(dateDebut, dateFin)));
			doc.setEtat(Etat.Renouvle);
			nr.save(doc);
		}
		return "redirect:/National?renouvle";
	}

	@RequestMapping("/modifier")
	public String modifier(@RequestParam("file") MultipartFile file, @RequestParam("idModif") int idEdit) {
		Optional<DocumentNational> docOp = nr.findById(idEdit);
		if (docOp.isPresent()) {
			DocumentNational doc = docOp.get();
			if (file.isEmpty()) {
				return "redirect:/National?failed";
			}
			String type;
			try {
				type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
						file.getOriginalFilename().length());
			} catch (Exception e) {
				type = "";
			}
			String newFileName = doc.getType() + "--" + doc.getDateSignature() + "-" + Instant.now().toEpochMilli()
					+ type;
			try {

				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + newFileName);
				Files.write(path, bytes);

			} catch (IOException e) {
				e.printStackTrace();
			}
			doc.setFileName(newFileName);
			nr.save(doc);

		}
		return "redirect:/National?modifier";
	}

	@RequestMapping("/archive")

	public ModelAndView home2() {
		List<DocumentNational> documents = nr.findAll();
		ModelAndView mv = new ModelAndView("National");
		mv.addObject("documents", documents);
		DocumentNational filtre = new DocumentNational();
		mv.addObject("filter", filtre);
		mv.addObject("document", new DocumentNational());
		return mv;
	}

	@RequestMapping("/download/{fileName:.+}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {

		File file = new File(UPLOADED_FOLDER + fileName);
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}

	@RequestMapping("/delete/{id}")
	public String deleteDoc(@PathVariable("id") int id) {
		nr.deleteById(id);
		return "redirect:/National?deletedsuccess";
	}

	@PostMapping(value = "/pdfreport", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> citiesReport(@RequestParam("annee1") Integer annee1,
			@RequestParam("annee2") Integer annee2) throws IOException {

		List<DocumentNational> cities = (List<DocumentNational>) nr.findAll();
		cities.forEach((city) -> {
			if (city.getDateSignature().getYear() < annee1 || city.getDateSignature().getYear() > annee2) {
				cities.remove(city);
			}
		});

		ByteArrayInputStream bis = GeneratePdfReport.citiesReport(cities);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Cooperations.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
}
