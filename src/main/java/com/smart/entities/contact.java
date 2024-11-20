package com.smart.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity

public class contact  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	 @NotBlank(message="name can't be blank")
     @Size(min=3,max=20, message="characters must be in between 3 to 20")
	private String name;
     @Size(min=0,max=20, message="characters must be in between 0 to 20")
	private String secondname;
	private String work;
	private String email;
	 @NotBlank(message="phone can't be blank")
	private String phone;
	private String image;
	@Column(length = 1000)
	private String descrption;
	
	@ManyToOne
	@JsonIgnore
	private user user;

	public contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public contact(int cid,
			 String name,
 String secondname, String work,
			String email,  String phone, String image, String descrption
			) {
		super();
		this.cid = cid;
		this.name = name;
		this.secondname = secondname;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.descrption = descrption;
	
	}


	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public user getUser() {
		return user;
	}

	public void setUser(user user) {
		this.user = user;
	}

//	@Override
//	public String toString() {
//		return "contact [cid=" + cid + ", name=" + name + ", secondname=" + secondname + ", work=" + work + ", email="
//				+ email + ", phone=" + phone + ", image=" + image + ", descrption=" + descrption + ", user=" + user
//				+ "]";
//	}
//	
	

}
