package com.pustaka.dao;

import java.util.List;

import com.pustaka.entity.CategoryEntity;

public interface CategoryDAO {

	/* 
	 * it return entire category table 
	 */
	public List<CategoryEntity> getCategoryList();
	
}
