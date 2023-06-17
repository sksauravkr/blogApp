package com.blog.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		Post post = dtoToPost(postDto);
		
		User user = userRepo.findById(userId).orElseThrow(()-> 
			new ResourceNotFoundException("User","User id",userId));
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> 
			new ResourceNotFoundException("Category", "Category id", categoryId));
		
		post.setImageName("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		return PostToDto(postRepo.save(post));
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> 
			new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		return PostToDto(postRepo.save(post));
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> 
			new ResourceNotFoundException("Post", "Post Id", postId));
		postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post> pagePost = postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
				
		List<PostDto> postDto = allPosts.stream().map((post) -> PostToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		return PostToDto(postRepo.findById(postId).orElseThrow(() -> 
				new ResourceNotFoundException("Post", "Post id", postId)));
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize); 
		Category category= categoryRepo.findById(categoryId).orElseThrow(() -> 
			new ResourceNotFoundException("Category", "Category Id", categoryId));
		Page<Post> posts = postRepo.findByCategory(category,p);
		List<PostDto> collect = posts.stream().map((post) -> PostToDto(post)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize); 
		User user = userRepo.findById(userId).orElseThrow(() -> 
				new ResourceNotFoundException("User", "User Id", userId));
		Page<Post> posts = postRepo.findByUser(user,p);
		List<PostDto> collect = posts.stream().map((post) -> PostToDto(post)).collect(Collectors.toList());
		return collect;
	}
	
	public Post dtoToPost(PostDto postDto) {
		
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}
	
	public PostDto PostToDto(Post post) {
		
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> list = postRepo.findByTitleContaining(keyword);
		return list.stream().map(item -> PostToDto(item)).collect(Collectors.toList());
	}

}
