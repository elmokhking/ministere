package com.ministere.cooperation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ministere.cooperation.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository ur;
	
	@RequestMapping("/liste")
	public ModelAndView userList() {
		ModelAndView mv = new ModelAndView("utilisateurs");
		mv.addObject("users",ur.findAll());
		return mv;
	}
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		ur.deleteById(id);
		return "redirect:/user/liste?deleted";
	}
	
}
