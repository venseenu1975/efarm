package com.farm;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.farm.model.User;
import com.farm.service.SecurityService;
import com.farm.service.UserService;
import com.farm.util.FarmUtil;

@Controller
public class DefaultController {
	private static Logger log = Logger.getLogger(DefaultController.class);
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

	@GetMapping("/error")
	public String error() {
		return "/error";
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

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String locate(Model model) {
		model.addAttribute("user", new User());
		return "farm_user_reg";
	}

	@Value("${registration_success_message}")
	private String regSuccessMsg;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String populateState(@Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "farm_user_reg";
		}

		log.info("user   " + user.getuAddress());
		log.info("user   " + user.getuLat());
		userService.create(user);
		FarmUtil.sendSMS(regSuccessMsg, user.getuPhoneNo());
		securityService.autologin(user.getuAliasName(), user.getuPass());
		return "redirect:/";
	}

}
