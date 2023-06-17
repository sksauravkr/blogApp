package com.blog.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
		
	Page<Post> findByUser(User user,Pageable p);
	Page<Post> findByCategory(Category category,Pageable p);
	List<Post> findByTitleContaining(String title);
	
	/*if we have to search by keyword without built in methods
	@Query("select p from post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
	in service layer where will be using this method we'll mention ("%"+keyword+"%");*/
}
