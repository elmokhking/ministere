package com.ministere.cooperation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministere.cooperation.model.International.ProgApplication;
import com.ministere.cooperation.repo.ProgApplicationRepository;
@Service
public class ProgApplicationServiceImpl implements ProgApplicationService {

	
	@Autowired
	private ProgApplicationRepository par;
	@Override
	public void saveOrUpdateProgApplication(ProgApplication progApplication) {
		par.save(progApplication);

	}

	@Override
	public List<ProgApplication> findAll() {
		return par.findAll();
	}

	@Override
	public void deleteProgApplication(int id) {
		par.deleteById(id);

	}

}
