package com.farm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.farm.model.User;
import com.farm.service.SecurityService;
import com.farm.service.UserService;

@Controller
public class DefaultController {
	
	@Autowired
	UserService userService;
	
   @Autowired
    private SecurityService securityService;

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    
	@RequestMapping("/registration")
	public String locate(Model model) {
		model.addAttribute("user", new User());
		return "farm_user_reg";
	}
	
	@RequestMapping("/addUser")
	public String populateState(ModelMap model,@ModelAttribute User user) {
		System.out.println("user   "+user.getuAddress());
		System.out.println("user   "+user.getuLat());
		userService.create(user);
		//System.out.println("user   "+userService.create(user));
		//model.put("msg",  "Added");
		//securityService.autologin(user.getuAliasName(), user.getuPass());
		return "farm_user_reg";
	}
	
}
