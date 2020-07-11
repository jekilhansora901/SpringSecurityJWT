package com.killer;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5965465414434634234L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(Long userId) {
		super("User not found with userId: "+userId);
	}

	
}
