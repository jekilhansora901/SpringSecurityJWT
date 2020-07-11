package com.killer.assembler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.killer.data.UserData;
import com.killer.domain.User;
import com.killer.domain.UserLogin;

@Component
public class UserAssembler {

	public User writeUser(UserData userData) {
		return  User.builder()
					.id(userData.getId())
					.firstName(userData.getFirstName())
					.lastName(userData.getLastName())
					.city(userData.getCity())
					.email(userData.getEmail())
					.mobile(userData.getMobile())
					.userName(userData.getUsername())
					.build();
	}
	
	public UserData writeUserData(UserLogin user) {
		return UserData.builder()
						.userName(user.getUserName())
						.password(user.getPassword())
						.build();
	}
	
	public UserData writeUserData(User user) {
		return UserData.builder()
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.city(user.getCity())
						.email(user.getEmail())
						.mobile(user.getMobile())
						.userName(user.getUserName())
						.build();

	}

	public UserData writeUserData(Map<String, Object> userReq, UserData user) {
		return UserData.builder()
				.firstName(userReq.containsKey("firstName") ? userReq.get("firstName").toString() : user.getFirstName())
				.lastName(userReq.containsKey("lastName") ? userReq.get("lastName").toString() : user.getLastName())
				.city(userReq.containsKey("city") ? userReq.get("city").toString() : user.getCity())
				.email(userReq.containsKey("email") ? userReq.get("email").toString() : user.getEmail())
				.mobile(userReq.containsKey("mobile") ? userReq.get("mobile").toString() : user.getMobile())
				.userName(userReq.containsKey("username") ? userReq.get("username").toString() : user.getUsername())
				.password(user.getPassword())
				.build();
		
	}
}
