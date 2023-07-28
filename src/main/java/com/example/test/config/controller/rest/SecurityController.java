package com.example.test.config.controller.rest;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class SecurityController {
	
	@GetMapping("/")
	public String index() {
		
		return "home";
	}// index
	
	@GetMapping("/user")
	public String userGet() {
		return "user";
	}// index
	
	@PostMapping("/user")
	public String userPost() {
		return "user";
	}// index
	
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}// index
	
	@GetMapping("/admin")
	public String admin() {
		log.info("### admin");
		
		return "admin";
	}// index
	
	@GetMapping("/denied")
	public String denied() {
		log.info("### denied");
		
		return "denied";
	}// index
}// SecurityController
