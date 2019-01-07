package com.ministere.cooperation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministere.cooperation.model.International.Accord;
import com.ministere.cooperation.repo.AccordRepository;

@Service
public class AccordServiceImpl implements AccordService {

	@Autowired
	private AccordRepository ar;

	@Override
	public void saveOrUpdateAccord(Accord accord) {

		ar.save(accord);

	}

	@Override
	public List<Accord> findAll() {
		return ar.findAll();
	}

	@Override
	public void deleteAccord(int id) {
		ar.deleteById(id);
	}

}
