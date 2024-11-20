package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
@Entity
public class user  {
	
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

      @Size(min=3,max=20, message="characters must be in between 3 to 20")
	private String name;
   @Email(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" , message="invalid email")
	private String email;
	private String password;
	private String role;
	private boolean enabled;
	private String image;
	private String about;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<contact> contact= new ArrayList<>();

	public user() {
		super();
		// TODO Auto-generated constructor stub
	}

	public user(int id,
 String name, String email,
			String password, String role, boolean enabled, String image, String about,
			List<com.smart.entities.contact> contact) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.image = image;
		this.about = about;
		this.contact = contact;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<contact> getContact() {
		return contact;
	}

	public void setContact(List<contact> contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enabled=" + enabled + ", image=" + image + ", about=" + about + ", contact=" + contact + "]";
	}


	
	
	
}
