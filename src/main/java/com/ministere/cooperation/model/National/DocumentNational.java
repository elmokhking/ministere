package com.ministere.cooperation.model.National;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.ministere.cooperation.model.International.Etat;

@Entity
public class DocumentNational {

	@Id
	@GeneratedValue
	public int id;
	public String fileName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	public LocalDate dateSignature;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	public LocalDate dateExpiration;
	public String detail;
	public String periode;
	public TypeNational type;
	public Etat etat;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDate getDateSignature() {
		return dateSignature;
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

	public LocalDate getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(LocalDate dateExpiration) {
		this.dateExpiration = dateExpiration;
		if (this.dateSignature!= null) {
			Long p = ChronoUnit.YEARS.between(getDateSignature(), getDateExpiration());
			if (p == 0l)
				setPeriode(ChronoUnit.MONTHS.between(getDateSignature(), getDateExpiration()) + " mois");
			else
				setPeriode(p + " ans");

		}
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public TypeNational getType() {
		return type;
	}

	public void setType(TypeNational type) {
		this.type = type;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public DocumentNational() {
		this.setEtat(Etat.EnCours);
	}

}
