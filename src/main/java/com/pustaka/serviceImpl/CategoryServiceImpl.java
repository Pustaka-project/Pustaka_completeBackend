package com.pustaka.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pustaka.dao.BookDAO;
import com.pustaka.dao.CategoryDAO;
import com.pustaka.dto.CategoryDTO;
import com.pustaka.entity.CategoryEntity;
import com.pustaka.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	private CategoryDAO categoryDAO;

	/*
	 *returns category id and name
	 */
	@Transactional
	@Override
	public List<CategoryDTO> getCategoryList() {
		List<CategoryEntity> catogoryEntity=null;
		catogoryEntity= this.categoryDAO.getCategoryList();
		
		List<CategoryDTO> result=new ArrayList<CategoryDTO>();
		for(CategoryEntity k:catogoryEntity){
			CategoryDTO  categoryDTO=new CategoryDTO();
			categoryDTO.setCategoryId(k.getCategoryId());
			categoryDTO.setCategoryName(k.getCategoryName());
			result.add(categoryDTO);
		}
		
		return result;
	}
	
	
}
