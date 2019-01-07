package com.ministere.cooperation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ministere.cooperation.model.International.User;
import com.ministere.cooperation.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("inscription");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, @RequestParam("role") String rolee) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("inscription");
		} else {
			userService.saveUser(user,rolee);
			modelAndView.addObject("successMessage", "Utilisateur enregistré");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("inscription");
			
		}
		return modelAndView;
	}
	
	@RequestMapping("/access-denied")
	public ModelAndView accessDenied() {
		ModelAndView mv = new ModelAndView("ErrorPage");
		return mv;
	}
	
//	@RequestMapping("/error")
//	public String error() {
//		return "error";
//	}
	
	

}
