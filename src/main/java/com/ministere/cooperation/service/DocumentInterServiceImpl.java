package com.ministere.cooperation.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministere.cooperation.model.International.DocumentFiltre;
import com.ministere.cooperation.model.International.DocumentInter;
import com.ministere.cooperation.repo.DocumentInterRepository;
import com.ministere.cooperation.repo.SearchRepository;
 @Service
public class DocumentInterServiceImpl implements DocumentInterService {

	@Autowired
	private DocumentInterRepository dir;
	@Autowired
	private SearchRepository sr;
	@Override
	public List<DocumentInter> findAll() {
		return dir.findAll();
	}
	@Transactional
	@Override
	public List<DocumentInter> search(DocumentFiltre filtre) {
		return sr.search(filtre);
	}

}
