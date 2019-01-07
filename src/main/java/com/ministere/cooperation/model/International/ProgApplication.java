package com.ministere.cooperation.model.International;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProgApplication extends DocumentInter{
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="accord_id")
	private Accord accord;
	
	
	public Accord getAccord() {
		return accord;
	}

	public void setAccord(Accord accord) {
		this.accord = accord;
		accord.add(this);
	}




	
	
	

	
	
	
	
	

	public ProgApplication() {

	}

	@Override
	public String toString() {
		return "ProgApplication [accord=" + accord + "]";
	}
	
	
	

	
	
}
