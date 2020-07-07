package com.killer.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String msg = "";
        try {
	        Boolean f = (Boolean) request.getAttribute("invalidJWT");
	        Boolean ff = (Boolean) request.getAttribute("noJWT");
	        if(f!= null && f == true) {
	        	msg = "invalid jwt token";
	        }
	        else if(ff!= null && ff == true) {
	        	msg = "no jwt token found";
	        }
        } catch(Exception e) {
        	msg = "invalid jwt token";
        }
        response.getOutputStream().println("{\"status\":\"failed\",\"message\":\""+msg+"\"}");
	}


}
