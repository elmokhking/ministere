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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ministere.cooperation.model.International.Accord;
import com.ministere.cooperation.model.International.DocumentFiltre;
import com.ministere.cooperation.model.International.DocumentInter;
import com.ministere.cooperation.model.International.Etat;
import com.ministere.cooperation.model.International.GeneratePdfReport;
import com.ministere.cooperation.model.International.ProgApplication;
import com.ministere.cooperation.repo.AccordRepository;
import com.ministere.cooperation.repo.DocumentInterRepository;
import com.ministere.cooperation.service.DocumentInterService;

@Controller
public class DocumentInterController {

	
	private static String UPLOADED_FOLDER = "/home/khalisl/Bureau/test/";
	
	@Autowired
	private DocumentInterService dis;

	@Autowired
	private AccordRepository ar;
	@Autowired
	private DocumentInterRepository dir;
	@RequestMapping("/International")
	public ModelAndView home() {
		List<DocumentInter> documents=dir.listerDocuments();
		ModelAndView mv=new ModelAndView("Continental");
		mv.addObject("documents",documents);
		DocumentFiltre filtre=new DocumentFiltre();
		mv.addObject("filter",filtre);
		mv.addObject("document",new DocumentInter());
		mv.addObject("count", dir.count());
		return mv;
	}
	
	
	
	@RequestMapping("/International/filter")
	public ModelAndView filtrer(DocumentFiltre filter) {
		List<DocumentInter> documents=dis.search(filter);
		ModelAndView mv=new ModelAndView("Continental");
		mv.addObject("documents",documents);
		mv.addObject("filter",filter);
		mv.addObject("document",new DocumentInter());
		return mv;
	}
	
	
	
	@RequestMapping("/International/save")
	public String save(@ModelAttribute("document") @Valid DocumentInter doc,BindingResult br,@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes,
			@RequestParam("accord") Optional<Integer> id,@RequestParam("autre") Optional<String> autre) {
		if(!br.hasErrors()) {
		int j;
		if(id != null && id.isPresent()) {
			j=id.get();
		}
		else {
			j=0;
		}
		if(autre.isPresent() && !autre.get().trim().isEmpty()) {
			doc.setPays(autre.get());
		}
		if (file.isEmpty()) {
//			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}
		String type;
		try {
		type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
		}
		catch(Exception e) {
			type="";
		}
		String newFileName = doc.getType()+"-"+doc.getPays()+"-"+doc.getDateSignature()+"-"+Instant.now().toEpochMilli()+type;
		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + newFileName);
			Files.write(path, bytes);

//			redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}
		doc.setFileName(newFileName);
		Optional<Accord> accordOp=ar.findById(j);
		if(accordOp.isPresent()) {
			Accord tempAccord=accordOp.get();
			tempAccord.add((ProgApplication)doc);
			dir.save(doc);
			ar.save(tempAccord);
		}
		else {
			dir.save(doc);

		}
		
		
		return "redirect:/International?success";
		}
		return "redirect:/International?failed";
	}
	
	@RequestMapping("/International/renouvler/{id}")
	public String renouveler(@PathVariable("id") int id) {
		Optional<DocumentInter> docOp=dir.findById(id);
		if(docOp.isPresent()) {
			DocumentInter doc=docOp.get();
			LocalDate dateDebut=doc.getDateSignature();
			LocalDate dateFin=doc.getDateExpiration();
			doc.setDateSignature(dateFin);
			doc.setDateExpiration(dateFin.plusDays(ChronoUnit.DAYS.between(dateDebut, dateFin)));
			doc.setEtat(Etat.Renouvle);
			dir.save(doc);
		}
		return "redirect:/International?renouvle";
	}
	
	
	@RequestMapping("/International/modifier")
	public String modifier(@RequestParam("file") MultipartFile file,@RequestParam("idModif") int idEdit) {
		Optional<DocumentInter> docOp=dir.findById(idEdit);
		if(docOp.isPresent()) {
			DocumentInter doc=docOp.get();
			if (file.isEmpty()) {
//				redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
				return "redirect:/International?failed";
			}
			String type;
			try {
			type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
			}
			catch(Exception e) {
				type="";
			}
			String newFileName = doc.getType()+"-"+doc.getPays()+"-"+doc.getDateSignature()+"-"+Instant.now().toEpochMilli()+type;
			try {

				// Get the file and save it somewhere
				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + newFileName);
				Files.write(path, bytes);

//				redirectAttributes.addFlashAttribute("message",
//					"You successfully uploaded '" + file.getOriginalFilename() + "'");

			} catch (IOException e) {
				e.printStackTrace();
			}
			doc.setFileName(newFileName);
			dir.save(doc);

		}
		return "redirect:/International?modifier";
	}
	
	
	




	@RequestMapping("/International/archive")
	
	public ModelAndView home2() {
		List<DocumentInter> documents=dir.findAll();
		ModelAndView mv=new ModelAndView("Continental");
		mv.addObject("documents",documents);
		DocumentFiltre filtre=new DocumentFiltre();
		mv.addObject("filter",filtre);
		mv.addObject("document",new DocumentInter());
		return mv;
	}
	
	
	@RequestMapping("/International/download/{fileName:.+}")
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
	
	
	
	@RequestMapping("/International/delete/{id}")
	public String deleteDoc(@PathVariable("id") int id) {
		dir.deleteById(id);
		return "redirect:/International?deletedsuccess";
	}
	
	@PostMapping(value = "/International/pdfreport",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport(@RequestParam("annee1") Integer annee1, @RequestParam("annee2") Integer annee2) throws IOException {
		
        List<DocumentInter> cities = (List<DocumentInter>) dir.findAll();
        cities.forEach((city)->{
        	if(city.getDateSignature().getYear() < annee1 || city.getDateSignature().getYear() > annee2) {
        		cities.remove(city);
        	}
        });
		

        ByteArrayInputStream bis = GeneratePdfReport.citiesReport(cities);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Cooperations.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
	
}
