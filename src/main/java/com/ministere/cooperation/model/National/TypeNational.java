package com.ministere.cooperation.model.National;

public enum TypeNational {
	DepartementGourvern("Departement gouvernemental"),
	AgencesDevel("Agences de developpement"),
	ONG("O.N.G"),
	INDH("I.N.D.H"),
	Association("Association"),
	Prive("Privé"),
	SemiPublique("Semi publique"),
	AUTRE("Autres");
	
	
	
	 private String description;
	    
	 TypeNational(final String description) {
	        this.description = description;
	    }
	    
	    public String getDescription() {
	        return this.description;
	    }
}
