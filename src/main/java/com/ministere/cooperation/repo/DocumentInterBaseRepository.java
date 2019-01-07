package com.ministere.cooperation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ministere.cooperation.model.International.DocumentInter;

@NoRepositoryBean
public interface DocumentInterBaseRepository<T extends DocumentInter> extends JpaRepository<T, Integer>{
	

	
	
}
