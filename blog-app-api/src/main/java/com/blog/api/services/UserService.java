package com.blog.api.services;

import java.util.List;

import com.blog.api.exceptions.MyException;
import com.blog.api.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	void deleteUser(Integer userId);

}
