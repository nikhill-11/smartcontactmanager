package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.smart.dao.UserRepository;
import com.smart.entities.user;
@Component
public class userdetailsimp implements UserDetailsService{
  @Autowired
	private UserRepository repo;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    
		user user = repo.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("user not exist");
		}else {
		
			return new customuserdetails(user);
			
			
		}
	}

}
