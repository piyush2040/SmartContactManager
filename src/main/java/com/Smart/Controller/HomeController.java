package com.Smart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
}
