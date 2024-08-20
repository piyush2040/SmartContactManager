package com.Smart.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.ControllerLogicInterface.UserServiceInterface;
import com.Smart.Helper.Message;
import com.Smart.dao.UserRepository;
import com.Smart.entities.Contact;
import com.Smart.entities.User;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	public User addContactToUser(Contact contact, MultipartFile file, Principal principal, HttpSession session) {
		
		String name = principal.getName();
		User user = null;
		try {
		//processing and uploading file.
		if(file.isEmpty())
		{
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
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		session.setAttribute("message", new Message("Something went Wrong!! Try again.." , "danger"));
	}
		return user;
	}

	@Override
	public void removeMessageFromSession() {
		HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		session.removeAttribute("message");
		
	}
}
