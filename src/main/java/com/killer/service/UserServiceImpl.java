package com.killer.service;

import java.util.ArrayList;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.killer.config.JwtUtil;
import com.killer.data.UserData;
import com.killer.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	PasswordEncoder encoder;
	
	public UserData registerUser(UserData userReq) {
		userReq = userReq.toBuilder().password(encoder.encode(userReq.getPassword())).build();
		UserData user = userRepository.save(userReq);
		
		return user;
	}
	
	public UserData getUser(Long userId) {
		
		Optional<UserData> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
		
		return null;
	}
	
	public JSONObject login(UserData applicationUser) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtil.generateJwtToken(authentication);

			JSONObject res = new JSONObject();
			res.put("status", "success");
			res.put("token", jwt);
			res.put("id", applicationUser.getUsername());
			return res;
		} catch(Exception e) {
			JSONObject res = new JSONObject();
			res.put("status", "failed");
			res.put("message", "invalid credentials");
			return res;
		}
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserData> user = userRepository.findByUserName(username);
		if (user.isPresent()) {
			return new User(user.get().getUsername(), user.get().getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
}
