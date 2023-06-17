package com.blog.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.User;
import com.blog.api.payloads.UserDto;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.UserService;
import com.blog.api.exceptions.MyException;
import com.blog.api.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		return userToDto(userRepo.save(dtoToUser(userDto)));
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = (User)userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","Id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		return userToDto(userRepo.save(user));
	}

	@Override
	public UserDto getUserById(Integer userId) {
		return userToDto(userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","Id",userId)));
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users= userRepo.findAll();
		return users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId){
		User user = userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","Id",userId));
		
		userRepo.delete(user);
	}
	
	public User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

}
