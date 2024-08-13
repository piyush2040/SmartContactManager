package com.Smart.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class UserController {
	@RequestMapping("/DashBoard")
	public String DashBoard()
	{
		return "User/DashBoard";
	}
}
