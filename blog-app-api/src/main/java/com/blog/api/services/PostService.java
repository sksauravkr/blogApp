package com.blog.api.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;


public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get All Posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);
	
	//get Post By Id
	PostDto getPostById(Integer postId);
	
	//get Post By Category
	List<PostDto> getPostByCategory(Integer categoryIdy, Integer pageNumber, Integer pageSize);
	
	//get Post By User
	List<PostDto> getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);	
	
	//search posts
	List<PostDto> searchPosts(String keyword);
}
