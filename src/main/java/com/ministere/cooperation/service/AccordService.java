package com.ministere.cooperation.service;

import java.util.List;

import com.ministere.cooperation.model.International.Accord;


public interface AccordService {

	public void saveOrUpdateAccord(Accord accord);
	public List<Accord> findAll();
	public void deleteAccord(int id);
	
}
