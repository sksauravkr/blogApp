package com.blog.api.exceptions;

public class MyException extends Exception{
	
	public MyException(){
		super("user not found");
	}

}
