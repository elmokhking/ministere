package com.ministere.cooperation;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ministere.cooperation.model.International.DocumentInter;
import com.ministere.cooperation.model.International.Etat;
import com.ministere.cooperation.model.National.DocumentNational;
import com.ministere.cooperation.repo.DocumentInterRepository;
import com.ministere.cooperation.repo.NationalRepo;


@Component
public class Sched {

	  @Autowired
	  private DocumentInterRepository dir;
	  @Autowired
	  private NationalRepo nr;
	 
	    @Scheduled(fixedRate = 500000000)
	    public void reportCurrentTime() {
	        List<DocumentInter> documents=dir.listerDocuments();
	        documents.forEach((document)->{
	        	if(ChronoUnit.DAYS.between(document.getDateSignature(), document.getDateExpiration())<=0) {
	        		document.setEtat(Etat.Expire);

	        	}
	        	else if(ChronoUnit.MONTHS.between(document.getDateSignature(), document.getDateExpiration())<6) {
	        		document.setEtat(Etat.PreExpiration);
	        		
	        	}
	        
	        	dir.save(document);
	        });
	        
	        List<DocumentNational> documentsn=nr.listerDocuments();
	        documentsn.forEach((document)->{
	        	if(ChronoUnit.DAYS.between(document.getDateSignature(), document.getDateExpiration())<=0) {
	        		document.setEtat(Etat.Expire);

	        	}
	        	else if(ChronoUnit.MONTHS.between(document.getDateSignature(), document.getDateExpiration())<6) {
	        		document.setEtat(Etat.PreExpiration);
	        		
	        	}
	        
	        	nr.save(document);
	        });
	    }
	
}