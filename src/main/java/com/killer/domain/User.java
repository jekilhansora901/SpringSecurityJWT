package com.killer.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
@Builder
@Setter
@ToString
@JsonInclude(value = Include.NON_NULL)
public class User {

	public Long id;
	public String userName;
	public String firstName;
	public String lastName;
	public String mobile;
	public String password;
	public String city;
	public String email;
}
