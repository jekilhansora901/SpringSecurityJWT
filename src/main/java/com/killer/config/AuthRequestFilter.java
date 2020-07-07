package com.killer.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.killer.data.UserData;
import com.killer.repository.UserRepository;
import com.killer.service.UserServiceImpl;

public class AuthRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserServiceImpl userService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthRequestFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = jwtUtil.parseJwt(request);
			if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
				String username = jwtUtil.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
					SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				if(jwt == null)
					request.setAttribute("noJWT", true);
				else 
					request.setAttribute("invalidJWT", true);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);

	}

}
