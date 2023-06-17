/**
 * 
 */
package com.learn.com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sauravkumar69
 *
 */
@RestController
public class LoginController {
	
	@GetMapping("/login/working")
	public String first() {
		return "working";
	}

}
