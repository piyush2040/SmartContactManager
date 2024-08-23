package com.Smart.ControllerLogicInterface;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.entities.Contact;
import com.Smart.entities.User;

import jakarta.servlet.http.HttpSession;

public interface UserServiceInterface {

	public void addCommonData(Model model, Principal principal);
	
	public User addContactToUser(Contact contact, MultipartFile file, Principal principal, HttpSession session);
	
	public Page<Contact> GetContactsByUserId(Principal principal,Integer page);
}
