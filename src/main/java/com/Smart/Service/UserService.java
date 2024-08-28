package com.Smart.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.ControllerLogicInterface.UserServiceInterface;
import com.Smart.Helper.Message;
import com.Smart.dao.ContactRepository;
import com.Smart.dao.UserRepository;
import com.Smart.entities.Contact;
import com.Smart.entities.User;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ContactRepository contactRepository;
    
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
	public User addContactToUser(Contact contact, MultipartFile file, Principal principal, HttpSession session) {
		
		String name = principal.getName();
		User user = null;
		try {
		//processing and uploading file.
		if(file.isEmpty())
		{
			contact.setImage("contact.png");
			System.out.println("Image is empty");
		}
		else {
			contact.setImage(file.getOriginalFilename());
			
				File staticFile = new ClassPathResource("static/img").getFile();
				Path path =   Paths.get(staticFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				
				
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				
			
			
			
		}
		
		user = userRepository.getUserByUserName(name);
		contact.setUser(user);
		
		user.getContacts().add(contact);
		user = userRepository.save(user);
		session.setAttribute("message", new Message("Your Contact is added!! Add more.." , "success"));
		}
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Getting error");
		session.setAttribute("message", new Message("Something went Wrong!! Try again.." , "danger"));
	}
		return user;
	}

	
	public Page<Contact> GetContactsByUserId(Principal principal) {
	    return GetContactsByUserId(principal, 0); // Default to page 0
	}
	
	@Override
	public Page<Contact> GetContactsByUserId(Principal principal,Integer page) {
		
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		Pageable pageable =  PageRequest.of(page, 5);
		
		Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(),pageable);
		System.out.println(pageable);
		//System.out.println(user);
		//System.out.println(contacts);
		return contacts;
	}

	@Override
	public Contact getDetialContactFromContactId(Integer cId) {
		
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		
		return contact;
	}

	@Override
	public void deleteContact(Integer cId) {
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		contact.setUser(null);
		this.contactRepository.delete(contact);
	}
	
	}
