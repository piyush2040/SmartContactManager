package com.Smart.Service;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.Smart.ControllerLogicInterface.UserServiceInterface;
import com.Smart.dao.UserRepository;
import com.Smart.entities.Contact;
import com.Smart.entities.User;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        if (principal != null) {
            String userName = principal.getName();
            System.out.println("Username: " + userName);
            User user = userRepository.getUserByUserName(userName);
            if (user != null) {
                System.out.println("User: " + user);
                model.addAttribute("User", user);
            } else {
                System.out.println("User not found for username: " + userName);
            }
        } else {
            System.out.println("Principal is null, user is not authenticated.");
        }
    }

	@Override
	public User addContactToUser(Contact contact, Principal principal) {
		
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		contact.setUser(user);
		user.getContacts().add(contact);
		user = userRepository.save(user);
		return user;
	}
}
