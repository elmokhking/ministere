package com.ministere.cooperation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ministere.cooperation.model.National.DocumentNational;

public interface NationalRepo extends JpaRepository<DocumentNational,Integer>{

	@Query("From DocumentNational d where d.etat NOT LIKE com.ministere.cooperation.model.International.Etat.Expire")
	public List<DocumentNational> listerDocuments();
	
	@Query("select count(*) From DocumentNational d  where d.etat LIKE com.ministere.cooperation.model.International.Etat.PreExpiration")
	public long count();


}
