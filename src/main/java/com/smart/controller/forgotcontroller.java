package com.smart.controller;

import java.util.Random;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.service;
import com.smart.dao.UserRepository;
import com.smart.entities.user;

import jakarta.servlet.http.HttpSession;

@Controller
public class forgotcontroller {
	 Random random=new Random(1000);
	  
	 @Autowired
	 private service service;
	 @Autowired
	private UserRepository repo;
	 @Autowired
		private BCryptPasswordEncoder passwordEncoder;
	 
	@RequestMapping("/forgot")
	public String openforgotemail() {
		
		
		return "forgot_password";
	}
	
	@PostMapping("/send-otp")
	public String sendotp( @RequestParam("email") String email, HttpSession session) {
	
		 int otp = random.nextInt(9999);
		System.out.println(otp);
		  String to = email;
	        String subject = "OTP ";
	        String body ="your otp is"+otp ;
		
	   boolean success= this.service.sendEmail(email, subject, body);
	   if (success) {
           System.out.println("Email sent successfully!");
           
           session.setAttribute("otp", otp);
           session.setAttribute("email", email);
           return "send_otp";
       } else {
           System.out.println("Failed to send email.");
           return "forgot_password"; 
       }
		    
		    
		 
		
	}
	
	@PostMapping("/verify-otp")
	public String verifyotp(@RequestParam("otp") int otp,HttpSession session){
		
		int myotp=(int)session.getAttribute("otp");
	String email=(String)session.getAttribute("email");
	
	
	if(myotp==otp) {
		
		user user = this.repo.findByEmail(email);
		if(user==null) {
			session.setAttribute("message", "user doesn't exist with this email");
			return "forgot_password";
		}else {
		
		return "password_change_from";}
	}else {
		
		session.setAttribute("message", "you have entere wrong otp");
		return  "send_otp";	
		
	}
		
	}
	
	@PostMapping("/changepassword")
	public String changepassword(@RequestParam("newpassword") String newpassword,HttpSession session) {
		String email=(String)session.getAttribute("email");
		user user= this.repo.findByEmail(email);
		user.setPassword(this.passwordEncoder.encode(newpassword));
		this.repo.save(user);
		session.setAttribute("message", "password change");
		
		return "login";
	}
}
