package com.pustaka.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pustaka.dao.CategoryDAO;
import com.pustaka.entity.CategoryEntity;

@Repository
public class CategoryDAOImpl implements CategoryDAO{
	
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public List<CategoryEntity> getCategoryList() {
		Session session=sessionfactory.getCurrentSession();
		
		Criteria cr=session.createCriteria(CategoryEntity.class);
		if(cr!=null){
			return cr.list();
		}
		return null;
	}

}
