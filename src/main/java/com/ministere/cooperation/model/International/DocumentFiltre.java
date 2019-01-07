package com.ministere.cooperation.model.International;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class DocumentFiltre {
	
	public String continent;
	public String pays;
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	public LocalDate dateSignature;
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	public LocalDate dateExpiration;
	public Etat	etat;
	public TypeDocumentInter type;
	
	
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public String getPays() {
		return pays;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public DocumentFiltre() {
		super();
	}

	public LocalDate getDateSignature() {
		return dateSignature;
	}

	public void setDateSignature(LocalDate dateSignature) {
		this.dateSignature = dateSignature;
	}

	
	

	public TypeDocumentInter getType() {
		return type;
	}

	public void setType(TypeDocumentInter type) {
		this.type = type;
	}

	public LocalDate getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(LocalDate dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public DocumentFiltre(String continent, String pays, LocalDate dateSignature, LocalDate dateExpiration) {
		super();
		this.continent = continent;
		this.pays = pays;
		this.dateSignature = dateSignature;
		this.dateExpiration = dateExpiration;
	}


}
