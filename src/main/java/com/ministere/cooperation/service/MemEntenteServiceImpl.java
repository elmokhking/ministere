package com.ministere.cooperation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministere.cooperation.model.International.MemEntente;
import com.ministere.cooperation.repo.MemEntenteRepository;

@Service
public class MemEntenteServiceImpl implements MemEntenteService {

	@Autowired
	private MemEntenteRepository mer;

	@Override
	public void saveOrUpdateMemEntente(MemEntente entente) {
		mer.save(entente);

	}

	@Override
	public List<MemEntente> findAll() {
		return mer.findAll();
	}

	@Override
	public void deleteMemEntente(int id) {
		mer.deleteById(id);

	}

}
