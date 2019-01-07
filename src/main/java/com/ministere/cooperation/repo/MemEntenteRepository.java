package com.ministere.cooperation.repo;

import java.util.List;

import javax.transaction.Transactional;

import com.ministere.cooperation.model.International.MemEntente;
import com.ministere.cooperation.model.International.TypeDocumentInter;

@Transactional
public interface MemEntenteRepository extends DocumentInterBaseRepository<MemEntente>{
	public List<MemEntente> findByType(TypeDocumentInter type);
}
