package com.Smart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.Smart.Service.UserService;
import com.Smart.entities.Contact;
import com.Smart.entities.User;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        userService.addCommonData(model, principal);
    }
	
	
	@RequestMapping("/DashBoard")
	public String DashBoard(Model model, Principal principal)
	{
		model.addAttribute("title","User - DashBoard");
		return "User/DashBoard";
	}
	//add contact handler
	@RequestMapping("/add-contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title","Add Contact");
		model.addAttribute("Contact", new Contact());
		
		return "User/add_contact_form";
	}
	
	//processing add contact form
	@PostMapping("/process-contact")
	public RedirectView processContact(@ModelAttribute Contact contact, Principal principal, @RequestParam(value = "profileImage", required = false) MultipartFile file, HttpSession session)
	{
		System.out.println(contact);
		User user =   userService.addContactToUser(contact,file,principal, session);
		System.out.println(user);
		return new RedirectView("/User/add-contact");
	}
}
