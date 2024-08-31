package com.Smart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.Smart.Helper.Message;
import com.Smart.Service.UserService;
import com.Smart.dao.ContactRepository;
import com.Smart.entities.Contact;
import com.Smart.entities.User;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactRepository contactRepository;
	
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
	
	
	@GetMapping("/show-contact")
	public String GetContacts(Model model, Principal principal) {
	    return GetContacts(0, model, principal); // Calls the method with page = 1
	}
	
	@GetMapping("/show-contact/{page}")
	public String GetContacts(@PathVariable("page") Integer page,  Model model,Principal principal)
	{
		model.addAttribute("title","Show Contact");
		Page<Contact> contacts = userService.GetContactsByUserId(principal,page);
		model.addAttribute("contacts",contacts);
		model.addAttribute("currentPage",page);
		System.out.println(contacts.getContent());
		model.addAttribute("totalPages",contacts.getTotalPages());
		System.out.println(contacts.getContent());
		return "User/show-contacts";
	}
	@RequestMapping("/contact/{cId}")
	public String showContactDetail(@PathVariable("cId") Integer cId,Model model)
	{
		
		Contact contact = userService.getDetialContactFromContactId(cId);
		model.addAttribute("contactInfo",contact);
		
		return "User/contactDetail";
	}
	
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId,Model model)
	{
		
		userService.deleteContact(cId);
		model.addAttribute("message",new Message("Contact deleted successfully...", "success"));
		
		return "redirect:/User/show-contact";
	}
	
	@RequestMapping("update-contact/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId, Model model)
	{
		Contact contact = this.contactRepository.findById(cId).get();
		model.addAttribute("contact",contact);
		return "User/update-contact";
	}
	
	@PostMapping("/process-update-contact")
	public RedirectView processUpdateContact(@ModelAttribute Contact contact,@RequestParam(value = "profileImage", required = false) MultipartFile file, HttpSession session,Model model)
	{
		System.out.println(contact);
		Contact Updatedcontact =   userService.updateContact(contact,session);
		System.out.println(contact);
		model.addAttribute("contact",Updatedcontact);
		return new RedirectView("/User/update-contact");
	}
	
}
