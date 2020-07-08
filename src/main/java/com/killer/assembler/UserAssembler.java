package com.killer.assembler;

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
}
