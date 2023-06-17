package com.blog.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.UserDto;
import com.blog.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	//POST - create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		//to return status code we use "new ResponseEntity<>()" otherwise will be using only ResponseEntity<>()
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
	}
	
	//PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		return ResponseEntity.ok(userService.updateUser(userDto, userId));
	}
	
	//DELETE - delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(Map.of("message","User deleted Successfully"),HttpStatus.OK);
	}
	
//	@DeleteMapping("/{userId}")
//	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
//		userService.deleteUser(userId);
//		return new ResponseEntity<>(new ApiResponse("User deleted seccessfully",true),HttpStatus.OK);
//	}
	
	//GET - get all user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = userService.getAllUser();
		//return new ResponseEntity<>(allUsers,HttpStatus.OK);
		
		return ResponseEntity.ok(allUsers);
	}
	
	//GET - get user by Id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
		
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK) ;
		
		//return ResponseEntity.ok(userService.getUserById(userId));
	}

}
