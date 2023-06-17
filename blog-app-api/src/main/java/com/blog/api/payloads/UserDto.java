package com.blog.api.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min= 3, message="name must be of alteast 3 character")
	private String name;
	
	@Email(message="email address is not valid")
	private String email;
	
	//@Pattern
	@NotEmpty
	@Size(min = 5, max=12, message="password must be between 5 to 12 charachters")
	private String password;
	
	@NotEmpty
	private String about;

}
