package com.killer.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.killer.UserNotFoundException;
import com.killer.assembler.UserAssembler;
import com.killer.data.UserData;
import com.killer.domain.User;
import com.killer.domain.UserLogin;
import com.killer.service.UserServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

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
	
	@ApiOperation(value = "Get User", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable("userId") Long userId) {
		UserData userData = userServiceImpl.getUser(userId);
		if(userData == null) {
			throw new UserNotFoundException(userId);
		}
		return this.userAssembler.writeUser(userData);
	}

	@ApiOperation(value = "Update User", authorizations = { @Authorization(value="jwtToken") })
	@PutMapping("/user/{userId}")
	public User updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
		UserData userReq = this.userAssembler.writeUserData(user);
		UserData userData = userServiceImpl.updateUser(userId, userReq);
		if(userData == null) {
			throw new UserNotFoundException(userId);
		}
		return this.userAssembler.writeUser(userData);
	}
	
	@ApiOperation(value = "Patch User", authorizations = { @Authorization(value="jwtToken") })
	@PatchMapping("/user/{userId}")
	public User patchUser(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> userReq) {
		UserData userData = userServiceImpl.patchUser(userId, userReq);
		if(userData == null) {
			throw new UserNotFoundException(userId);
		}
		return this.userAssembler.writeUser(userData);
	}
}
