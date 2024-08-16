package com.Smart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.Smart.Helper.Message;
import com.Smart.dao.UserRepository;
import com.Smart.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
		model.addAttribute("user",new User());	
		return "signUp";
	}
	
	//handler for registering user
	@RequestMapping(value = "/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model , HttpSession session)
	{
		try {
			Object message = session.getAttribute("message");
	        if (message != null) {
	            model.addAttribute("message", message);
	            session.removeAttribute("message");
	        }
			if(!agreement)
			{
				System.out.println("you have not agreed the T&Cs");
				throw new Exception("you have not agreed the T&Cs");
			}
			
			if(result.hasErrors())
			{
				model.addAttribute("user", user);
				return "signUp";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User Result = userRepository.save(user);
//			System.out.println("Agreement" + agreement);
//			System.out.println("user" + user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered","alert-success"));
		}
		catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong !!"+ex.getMessage(),"alert-danger"));
			
		}
		return "signUp";
	}
}
