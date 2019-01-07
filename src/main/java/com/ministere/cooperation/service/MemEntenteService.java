package com.ministere.cooperation.service;

import java.util.List;

import com.ministere.cooperation.model.International.MemEntente;

public interface MemEntenteService {
	public void saveOrUpdateMemEntente(MemEntente entente);
	public List<MemEntente> findAll();
	public void deleteMemEntente(int id);
}
