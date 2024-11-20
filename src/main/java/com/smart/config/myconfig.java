package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class myconfig  {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    public UserDetailsService getUserDetailsService() {
    	return new userdetailsimp();
    }
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider dao= new DaoAuthenticationProvider();
    	dao.setUserDetailsService(this.getUserDetailsService());
    	dao.setPasswordEncoder(passwordEncoder());
    	return dao;
    }

	
	@Bean
	public SecurityFilterChain Securityfilterchain(HttpSecurity http) throws Exception{
		
		
		http.csrf().disable().authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/**").permitAll().anyRequest().authenticated().and()
		.formLogin().loginPage("/signin").defaultSuccessUrl("/user/dashboard");
		
			return http.build();
		
		
	}
	
}
