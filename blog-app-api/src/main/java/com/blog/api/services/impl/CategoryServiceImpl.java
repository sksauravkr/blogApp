 package com.blog.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		return categoryToDto(categoryRepo.save(dtoToCategory(categoryDto)));
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category","Id",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		return categoryToDto(categoryRepo.save(category));
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category","Id",categoryId));
		
		categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		
		return categoryToDto(categoryRepo.findById(categoryId).orElseThrow(()-> 
		new ResourceNotFoundException("Category","Id",categoryId)));
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> category = categoryRepo.findAll();
		return category.stream().map(user -> categoryToDto(user)).collect(Collectors.toList());
	}
	
	public Category dtoToCategory(CategoryDto categoryDto) {
		
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	public CategoryDto categoryToDto(Category category) {
		
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
