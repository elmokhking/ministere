package com.ministere.cooperation.model.International;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Accord extends DocumentInter{

	
	@OneToMany(mappedBy="accord", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<ProgApplication> progApplications;

	
	
	

	public Accord() {
	}
	
	



	@Override
	public String toString() {
		return "Accord [progApplications=" + progApplications + "]";
	}





	public void add(ProgApplication e) {
		if (progApplications == null)
			progApplications = new ArrayList<>();
		progApplications.add(e);
		e.setAccord(this);
	}

	public List<ProgApplication> getProgApplications() {
		return progApplications;
	}

	public void setProgApplications(List<ProgApplication> progApplications) {
		this.progApplications = progApplications;
	}
	
}
