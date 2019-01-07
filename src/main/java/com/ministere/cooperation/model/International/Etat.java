package com.ministere.cooperation.model.International;

public enum Etat {
	
	EnCours("En cours"),
	PreExpiration("Expire prochainement"),
	Expire("Expiré"),
	Renouvle("Renouvlé");
	
	
	 private String description;
	    
	 Etat(final String description) {
	        this.description = description;
	    }
	    
	    public String getDescription() {
	        return this.description;
	    }
}
