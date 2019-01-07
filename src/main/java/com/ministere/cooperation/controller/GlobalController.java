package com.ministere.cooperation.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalController {

//	 @InitBinder
//	  public void initBinder(WebDataBinder binder) {
//	    binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
//	      @Override
//	      public void setAsText(String text) throws IllegalArgumentException {
//	    	  if(text!=null && !text.trim().isEmpty())
//	        LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
//	      }
//	    });
//	  }
}
