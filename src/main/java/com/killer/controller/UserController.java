package com.killer.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.killer.assembler.UserAssembler;
import com.killer.data.UserData;
import com.killer.domain.User;
import com.killer.domain.UserLogin;
import com.killer.service.UserServiceImpl;

@RestController
@RequestMapping("v1")
public class UserController {

	private UserServiceImpl userServiceImpl;
	private UserAssembler userAssembler;
	
	@Autowired
	public UserController(UserServiceImpl userServiceImpl, UserAssembler userAssembler) {
		super();
		this.userServiceImpl = userServiceImpl;
		this.userAssembler = userAssembler;
	}



	@PostMapping("/user/registration")
	public User register(@RequestBody UserData userReq) {
		UserData userData = userServiceImpl.registerUser(userReq);
		return this.userAssembler.writeUser(userData);
	}
	
	@PostMapping("/user/login")
	public JSONObject login(@RequestBody UserLogin userReq) {
		UserData userData = this.userAssembler.writeUserData(userReq);
		JSONObject resp = userServiceImpl.login(userData);
		return resp;
	}
	
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable("userId") Long userId) {
		UserData userData = userServiceImpl.getUser(userId);
		return this.userAssembler.writeUser(userData);
	}
}
