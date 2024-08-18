package com.Smart.ControllerLogicInterface;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.entities.Contact;
import com.Smart.entities.User;

public interface UserServiceInterface {

	public void addCommonData(Model model, Principal principal);
	
	public User addContactToUser(Contact contact, MultipartFile file, Principal principal);
}
