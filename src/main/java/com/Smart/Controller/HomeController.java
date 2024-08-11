package com.Smart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import com.Smart.dao.UserRepository;
import com.Smart.entities.User;



@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	@ResponseBody
	public String test()
	{
		User user = new User();
		user.setName("Piyush");
		user.setEmail("piyush@gmail.com");
		userRepository.save(user);
				
		return "working";
	}
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home - Smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About - Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signUp")
	public String signUp(Model model)
	{
		model.addAttribute("title","SignUp - Smart Contact Manager");
		return "signUp";
	}
}
