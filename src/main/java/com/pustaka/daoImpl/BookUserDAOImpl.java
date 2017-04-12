package com.pustaka.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pustaka.dao.BookUserDAO;
import com.pustaka.entity.BookUserEntity;

@Repository
public class BookUserDAOImpl implements BookUserDAO {

	@Autowired
	SessionFactory sessionFactory;

	
	/*
	 * save the book user details to book_user table
	 */
	@Override
	public long addMappingforBookUser(BookUserEntity bookUser) {
		Session session = sessionFactory.getCurrentSession();
		return (long) session.save(bookUser);
	}

	/*
	 * returns list of book id which are uploaded by user
	 * status 1 represent owner
	 * status 2 represent buyer
	 */
	@Override
	public List<Long> getUserBookMappingByUserId(long userId) {
		Session session = sessionFactory.getCurrentSession();
		Criterion userCriteria = Restrictions.eq("userId", userId);
		Criterion statusCriteria = Restrictions.eq("status", 1);
		
		Criteria cr = session.createCriteria(BookUserEntity.class).add(userCriteria).add(statusCriteria);
		
		cr.setProjection(Projections.property("bookId"));
      
		return cr.list();
	}

	/*
	 * returns userid who is the owner for particular book 
	 */
	@Override
	public long getBookOwner(long bookId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criterion bookCriteria = Restrictions.eq("bookId", bookId);
		Criterion statusCriteria = Restrictions.eq("status", 1);
		
		Criteria cr = session.createCriteria(BookUserEntity.class).add(bookCriteria).add(statusCriteria);
		
		cr.setProjection(Projections.property("userId"));
      
		return (long) cr.uniqueResult();
		
	}

}
