package com.ministere.cooperation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ministere.cooperation.model.International.DocumentFiltre;
import com.ministere.cooperation.model.International.DocumentInter;
import com.ministere.cooperation.model.National.DocumentNational;
@Service
public interface DocumentInterService {

	
	
	public List<DocumentInter> findAll();
	public List<DocumentInter> search(DocumentFiltre filtre);

	
	
}