package com.Smart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Smart.dao.UserRepository;
import com.Smart.entities.User;

@Controller
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/DashBoard")
	public String DashBoard(Model model, Principal principal)
	{
		String userNameString = principal.getName();
		System.out.println("Username" + userNameString);
		User user = userRepository.getUserByUserName(userNameString);
		model.addAttribute("User",user);
		return "User/DashBoard";
	}
}
