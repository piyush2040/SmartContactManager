package com.Smart.ControllerLogicInterface;

import java.security.Principal;

import org.springframework.ui.Model;

import com.Smart.entities.Contact;
import com.Smart.entities.User;

public interface UserServiceInterface {

	public void addCommonData(Model model, Principal principal);
	
	public User addContactToUser(Contact contact, Principal principal);
}
