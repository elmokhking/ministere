package com.ministere.cooperation.model.International;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Inheritance
public class DocumentInter {

	@Id
	@GeneratedValue
	private int id;
	private String fileName;
	@NotNull
	private String continent; // private Continent continent?
	private String pays;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate dateSignature;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate dateExpiration;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String periode;
	private TypeDocumentInter type;
	private Etat etat;

	public DocumentInter(int id, String continent, String pays, LocalDate dateSignature, LocalDate dateExpiration,
			 String periode, TypeDocumentInter type) {

		this.id = id;
		this.continent = continent;
		this.pays = pays;
		this.dateSignature = dateSignature;
		this.dateExpiration = dateExpiration;
		this.periode = periode;
		this.type = type;
	}

	

	public TypeDocumentInter getType() {
		return type;
	}

	public void setType(TypeDocumentInter type) {
		this.type = type;
	}
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDate getDateSignature() {
		return dateSignature;
	}

	public LocalDate getDateExpiration() {
		return dateExpiration;
	}

	public int getId() {
		return id;
	}

	public void setDateSignature(LocalDate dateSignature) {
		this.dateSignature = dateSignature;
		if (this.dateExpiration != null) {
			Long p = ChronoUnit.YEARS.between(getDateSignature(), getDateExpiration());
			if (p == 0l)
				setPeriode(ChronoUnit.MONTHS.between(getDateSignature(), getDateExpiration()) + " mois");
			else
				setPeriode(p + " ans");

		}
	}

	public void setDateExpiration(LocalDate dateExpiration) {
		this.dateExpiration = dateExpiration;
		if (this.dateSignature != null) {
			Long p = ChronoUnit.YEARS.between(getDateSignature(), getDateExpiration());
			if (p == 0l)
				setPeriode(ChronoUnit.MONTHS.between(getDateSignature(), getDateExpiration()) + " mois");
			else
				setPeriode(p + " ans");

		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
		
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	

	public DocumentInter() {
		setEtat(Etat.EnCours);
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", continent=" + continent + ", pays=" + pays + ", dateSignature=" + dateSignature
				+ ", dateExpiration=" + dateExpiration +", periode=" + periode
				+ "]";
	}

}
