package com.ministere.cooperation.model.International;

public enum TypeDocumentInter {
	ACCORD("Accord"),
	PROG("Programme d'application"),
	ENTENTE("Memorandum d'entente");
	
	 private String description;
	    
	 TypeDocumentInter(final String description) {
	        this.description = description;
	    }
	    
	    public String getDescription() {
	        return this.description;
	    }
}
