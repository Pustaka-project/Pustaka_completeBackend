package com.pustaka.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pustaka.dao.BookDAO;
import com.pustaka.dto.BookDTO;
import com.pustaka.entity.BookEntity;
import com.pustaka.entity.CategoryEntity;
import com.pustaka.entity.UserEntity;

@Repository
public class BookDAOImpl implements BookDAO {

	private static final Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public long addBook(BookEntity book) {
		Session session = this.sessionfactory.getCurrentSession();
		return (long) session.save(book);
	}

	/*
	 *update status in book table from 1 to 2  
	 */
	@Override
	public String removeBook(long bookId) {
		Session session = this.sessionfactory.getCurrentSession();
		//BookDTO book=null;
		String result=null;
		BookEntity book = (BookEntity) session.load(BookEntity.class, new Long(bookId));
		
		book.setStatus(2);
		
		try{
		if (null != book) {
			session.update(book);
			result= "Status Updated";
		}}catch (Exception e) {
			result= "Exception while Updating ";
		}
		//logger.info("book deleted successfully, book details= " + book);
		return result;
		
	}
	
	/*
	 * Returns all books which are belongs to particular category and available   
	 */

	@Override
	public List<BookEntity> getBookListByCatogoryId(long categoryId, int pagenumber, int itemsperpage) {
		Session session = sessionfactory.getCurrentSession();
		Criterion categoryCriteria = Restrictions.eq("categoryId.categoryid", categoryId);
		Criterion statusCriteria = Restrictions.eq("status", 1);

		Criteria cr = session.createCriteria(BookEntity.class).add(categoryCriteria).add(statusCriteria);
		cr.setFirstResult((pagenumber - 1) * itemsperpage);
		cr.setMaxResults(itemsperpage);
		return cr.list();

	}

	@Override
	public BookEntity getBookDetailsByBookId(long bookId) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		Criterion bookCriteria = Restrictions.eq("bookId", bookId);
		Criteria cr = session.createCriteria(BookEntity.class).add(bookCriteria);
		return (BookEntity) cr.uniqueResult();
		
	}

}
