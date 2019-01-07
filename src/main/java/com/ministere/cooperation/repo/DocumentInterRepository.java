package com.ministere.cooperation.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;

import com.ministere.cooperation.model.International.DocumentInter;

@Transactional
public interface DocumentInterRepository extends DocumentInterBaseRepository<DocumentInter>{
	@Query("From DocumentInter d where d.etat NOT LIKE com.ministere.cooperation.model.International.Etat.Expire")
	public List<DocumentInter> listerDocuments();
	
	@Query("select count(*) From DocumentInter d  where d.etat LIKE com.ministere.cooperation.model.International.Etat.PreExpiration")
	public long count();

}
