package com.smart.dao;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.contact;
import com.smart.entities.user;

public interface contactrepository extends JpaRepository<contact,Integer>{
	
	@Query("from contact as c where c.user.id=:id")
	public org.springframework.data.domain.Page<contact> findcontactsbyuser(@Param("id") int id,Pageable pageable);

	
	public List<contact> findByNameContainingAndUser(String name, user user);
	
}
