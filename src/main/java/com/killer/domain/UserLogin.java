package com.killer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Setter
@ToString
public class UserLogin {
	
	private String userName;
	private String password;
	
}
