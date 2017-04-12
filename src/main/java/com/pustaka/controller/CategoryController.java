package com.pustaka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pustaka.dto.CategoryDTO;
import com.pustaka.service.BookService;
import com.pustaka.service.CategoryService;

/*
 * This is the Controller class for managing all Book API's
 */
@RestController
@RequestMapping("/category") // Mapping URl
public class CategoryController {

	/*
	 * we are using "Autowired" annotation to inject the object dependency
	 * implicitly of this particular class
	 */

	@Autowired
	CategoryService categoryService;

	/*
	 * this returns all the Categories, which contain categoryId and
	 * categoryName
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	List<CategoryDTO> getCategoryList() {

		System.out.println("hello");
		List<CategoryDTO> result = categoryService.getCategoryList();

		return result;
	}

}
