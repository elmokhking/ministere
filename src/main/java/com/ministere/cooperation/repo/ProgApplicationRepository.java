package com.ministere.cooperation.repo;

import java.util.List;

import javax.transaction.Transactional;

import com.ministere.cooperation.model.International.ProgApplication;
import com.ministere.cooperation.model.International.TypeDocumentInter;

@Transactional
public interface ProgApplicationRepository extends DocumentInterBaseRepository<ProgApplication>{
	public List<ProgApplication> findByType(TypeDocumentInter type);
}
