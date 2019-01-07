package com.ministere.cooperation.service;

import java.util.List;

import com.ministere.cooperation.model.International.ProgApplication;

public interface ProgApplicationService {

	public void saveOrUpdateProgApplication(ProgApplication progApplication);
	public List<ProgApplication> findAll();
	public void deleteProgApplication(int id);
}
