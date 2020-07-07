package com.killer.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.killer.data.UserData;
import com.killer.service.UserServiceImpl;

@RestController
@RequestMapping("v1")
public class UserController {

	private UserServiceImpl userServiceImpl;
	
	@Autowired
	public UserController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;
	}



	@PostMapping("/user/registration")
	public UserData register(@RequestBody UserData userReq) {
		UserData resp = userServiceImpl.registerUser(userReq);
		return resp;
	}
	
	@PostMapping("/user/login")
	public JSONObject login(@RequestBody UserData userReq) {
		JSONObject resp = userServiceImpl.login(userReq);
		return resp;
	}
	
	@GetMapping("/user/{userId}")
	public UserData getUser(@PathVariable("userId") Long userId) {
		UserData resp = userServiceImpl.getUser(userId);
		
		return resp;
	}
}
