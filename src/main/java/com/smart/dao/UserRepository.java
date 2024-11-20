package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.smart.entities.user;

import java.util.List;

@Service	

public interface UserRepository extends JpaRepository<user, Integer> {
	
public user  findByEmail(String email);

	

}
