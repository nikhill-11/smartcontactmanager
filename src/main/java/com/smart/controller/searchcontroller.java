package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.UserRepository;
import com.smart.dao.contactrepository;
import com.smart.entities.contact;
import com.smart.entities.user;

@RestController
public class searchcontroller {
     @Autowired
	private UserRepository repo;
	@Autowired
     private contactrepository contactrepo;
     
	@GetMapping("/search/{query}")
	public ResponseEntity< ?>search(@PathVariable("query")String query , Principal p)
	{
		String username =p.getName();
		System.out.println(username);
		user user = this.repo.findByEmail(username);
		
		System.out.println(query);
		List<contact> contactsbysearch = this.contactrepo.findByNameContainingAndUser(query, user);
	return	ResponseEntity.ok(contactsbysearch);
	}
	
	
	

}
