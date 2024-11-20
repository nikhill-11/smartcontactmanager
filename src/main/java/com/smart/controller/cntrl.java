package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.dao.contactrepository;
import com.smart.entities.contact;
import com.smart.entities.user;
import com.smart.helper.message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class cntrl {
	@Autowired
	private UserRepository repo;
	 @Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	 @Autowired
	 private contactrepository contactrepo;
	 
	@RequestMapping("/home")
	public String base(Model m) {
	m.addAttribute("title","Home" );
		return "home";
	}
	@RequestMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("title", "Register");
		m.addAttribute("user",new user());
		return "signup";
	}
@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user")user user,BindingResult result,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model m,HttpSession session
	  ) {

	
	try {
		
		
		if(!agreement) {
			System.out.println("you have not checked terms and condition");
			throw new Exception("you have not checked terms and condition");
		}
		if(result.hasErrors()) {
			System.out.println("errors"+result.toString());
			m.addAttribute("user",user);
			return "signup";
		}
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.repo.save(user);
		m.addAttribute("user",user);
		System.out.println( "agreement" +agreement );
			System.out.println("user"+user);
			session.setAttribute("message", new message("you are ready to go !!" , "alert-success"));
			
		return "signup";
		
	} catch (Exception e) {
		e.printStackTrace();
		m.addAttribute("user",user);
		session.setAttribute("message", new message("something went wrong !! "+e.getMessage(), "alert-danger"));
		
		
		return "signup";
	}
	}
@RequestMapping("/user/dashboard")
public String dash( Model m , Principal p)

{
	String useranme = p.getName();
	user user = repo.findByEmail(useranme);
	System.out.println(user);
	m.addAttribute("user",user);
	return "dash";
}
@GetMapping("/signin")
public String login(Model m) {
	m.addAttribute("title","login");
	return "login";
}
@GetMapping("/user/contact")
 public String addcontact(Model m , Principal p) {
	 String useranme = p.getName();
		user user = repo.findByEmail(useranme);

		m.addAttribute("user",user);
		m.addAttribute("title","add contact");
		m.addAttribute("contact",new contact());
	 return "contact";
 }

@PostMapping("/user/process")
public String process(@ModelAttribute contact contact ,
		Model m
		 ,@RequestParam("Profileimage") MultipartFile file,
		Principal principal, HttpSession session) {
	
	try {
		
	String name = principal.getName();
	user user = this.repo.findByEmail(name);
	m.addAttribute("user",user);
	m.addAttribute("title","add contact");
	m.addAttribute("contact",new contact());
	
	
	if(file.isEmpty()) {
		contact.setImage("save.png");
		
	}else {
		contact.setImage(file.getOriginalFilename());
		File file2 = new ClassPathResource("static/img").getFile();
		Path path = Paths.get(file2.getAbsolutePath()+File.separator+file.getOriginalFilename());
		Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
		System.out.println("image is uploaded");
	}
	contact.setUser(user);
	
	user.getContact().add(contact);
	this.repo.save(user);
	
	System.out.println(contact);
	 
	session.setAttribute("message", new message("your contact is sucessfully added", "success"));
	}
	catch(Exception e) {
		System.out.println("error"+e.getMessage());
		e.printStackTrace();
		session.setAttribute("message", new message("something went wrong !! try again", "danger"));
	}
	return "contact";
}
@GetMapping("/user/showcontacts/{page}")
public String showcontact(@PathVariable("page")Integer page ,Model m, Principal p) {
	
	m.addAttribute("title","add contact");
	 String useranme = p.getName();
		user user = repo.findByEmail(useranme);
		m.addAttribute("user",user);
		
		Pageable pageable = PageRequest.of(page, 5);
		
Page<contact> allcontacts = this.contactrepo.findcontactsbyuser(user.getId(),pageable);
System.out.println(allcontacts);
	m.addAttribute("contacts",allcontacts );

	m.addAttribute("currentpage",page);
	m.addAttribute("totalpages",allcontacts.getTotalPages());
	
	return "show_contact";
}


@RequestMapping("/user/contact/{cid}")
public String showprofile(@PathVariable("cid")Integer cid , Model m , Principal p) {
	String useranme = p.getName();
	user user = repo.findByEmail(useranme);
	m.addAttribute("user",user);
	m.addAttribute("title","profile");
	Optional<contact> optional = this.contactrepo.findById(cid);
	contact contact = optional.get();
	
	if(user.getId()==contact.getUser().getId()) {
	m.addAttribute("contact",contact);}else {}
	System.out.println(cid);
	return "profilepage";
}

@GetMapping("/user/delete/{cid}")
public String delete(@PathVariable("cid")Integer cid,Model m,Principal p, HttpSession s) {
	String useranme = p.getName();
	user user = repo.findByEmail(useranme);
	m.addAttribute("user",user);
	
	Optional<contact> optional = this.contactrepo.findById(cid);
	contact contact = optional.get();
	this.contactrepo.delete(contact);
	s.setAttribute("message" , new message("contact deleted succesfully", "success"));
	return "redirect:/user/showcontacts/0"; 
}




@PostMapping("/user/update-contact/{cid}")
public String UpdateForm(@PathVariable("cid") Integer cid,Model model,Principal p)
{
	String useranme = p.getName();
	user user = repo.findByEmail(useranme);
	model.addAttribute("user",user);
	
	model.addAttribute("title", "Update Contact");
	

	contact contact = this.contactrepo.findById(cid).get();
model.addAttribute("contact",contact);
	
	return"update_from";
}

@PostMapping("/user/process-update")
public String updatehnadler(@ModelAttribute contact contact , Model m , Principal p,HttpSession session) {
	// try {
	user user=this.repo.findByEmail(p.getName());
	
//	contact oldcontactDetails=this.contactrepo.findById(contact.getCid()).get();
//	
//	@RequestParam("image") MultipartFile file
//	if(!file.isEmpty())
//	{
//		//delete file
//		File deleteFile=new ClassPathResource("static/img").getFile();
//		File file1=new File(deleteFile,oldcontactDetails.getImage());
//		file1.delete();
//		
//		//update image
//		File saveFile=new ClassPathResource("static/img").getFile();
//		
//		Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//		
//		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//		contact.setImage(file.getOriginalFilename());
//	}
//	else
//	{
//		contact.setImage(oldcontactDetails.getImage());
//	}
	
	
//	System.out.println(contact.getName());
//	System.out.println(contact.getCid());
	// }catch(Exception e) {
	//	 e.printStackTrace();
	 //}
	 contact.setUser(user);
		
		this.contactrepo.save(contact);
		m.addAttribute("user",user);
		m.addAttribute("title", "Update Contact");
		session.setAttribute("message", new message("contact updated succesfully","success"));
		System.out.println(contact.getCid());
		
	return "redirect:/user/contact/"+contact.getCid();
}

@GetMapping("/user/profile")
public String personalprofile( Model m , Principal p) {

	String useranme = p.getName();
	user user = repo.findByEmail(useranme);
	m.addAttribute("title", "profile");
	m.addAttribute("user",user);
	
	return "personal_profile";
}

@GetMapping("/user/setting")
public String opensettings( Model m, Principal p) {
	
	String useranme = p.getName();
	user user = repo.findByEmail(useranme);
	m.addAttribute("title", "settings");
	m.addAttribute("user",user);
	
	return "setting";
}
@PostMapping("/user/change-password")
public String changepasswrod( @RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword,Model m,Principal p,HttpSession session) {
	
	String useranme = p.getName();
	user user = repo.findByEmail(useranme);

	m.addAttribute("user",user);
	String database_password = user.getPassword();
	
	 if(this.passwordEncoder.matches(oldpassword, database_password)) {
		 user.setPassword(this.passwordEncoder.encode(newpassword));
		 this.repo.save(user);
		 session.setAttribute("message", new message("your password changed  sucessfully ", "success")); 
	 }else {
		 session.setAttribute("message", new message("wrong password ", "warning")); 
	 }
	System.out.println("old pass word "+ oldpassword);
	System.out.println("new pass word "+ newpassword);
	
	return "redirect:/user/dashboard";
}




}




