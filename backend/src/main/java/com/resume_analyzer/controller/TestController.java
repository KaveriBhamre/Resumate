package com.resume_analyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resume_analyzer.entity.User;
import com.resume_analyzer.repository.UserRepository;

@RestController
@RequestMapping("api/test")
public class TestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/hello")
	public String hello() {
		return "Backend is alive";
	}
	
	@PostMapping("/create-user")
	public User createUser() {
		User user = new User();
		user.setEmail("test@example.com");
		user.setName("Dummy");
		user.setPassword("test123");
		return userRepository.save(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

}
