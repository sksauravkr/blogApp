package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	//update
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	//delete
	public void deleteCategory(Integer categoryId);
	//get by Id
	public CategoryDto getCategoryById(Integer categoryId);
	//get all
	public List<CategoryDto> getAllCategory();
		
	
}
