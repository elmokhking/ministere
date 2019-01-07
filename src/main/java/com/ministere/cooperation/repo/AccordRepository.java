package com.ministere.cooperation.repo;

import java.util.List;

import javax.transaction.Transactional;

import com.ministere.cooperation.model.International.Accord;
import com.ministere.cooperation.model.International.TypeDocumentInter;

@Transactional
public interface AccordRepository extends DocumentInterBaseRepository<Accord>{
	public List<Accord> findByType(TypeDocumentInter type);
}
