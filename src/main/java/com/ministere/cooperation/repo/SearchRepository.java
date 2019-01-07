package com.ministere.cooperation.repo;

import java.util.List;

import com.ministere.cooperation.model.International.DocumentFiltre;
import com.ministere.cooperation.model.International.DocumentInter;
import com.ministere.cooperation.model.National.DocumentNational;

public interface SearchRepository {

	public List<DocumentInter> search(DocumentFiltre filtre);
	public List<DocumentNational> search(DocumentNational filtre);

}
