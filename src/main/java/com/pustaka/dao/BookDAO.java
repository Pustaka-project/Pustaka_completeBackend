package com.pustaka.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pustaka.entity.BookEntity;
/**
 *Book 
 */
@Repository
public interface BookDAO { //It provides blueprint for all DAOImplementation
	
	/*
	 * we are sending all BookEntity class parameter and its return the particular Book id
	 */
	public long addBook(BookEntity book);
    
	/* Method for updating status of Book by Owner */
	public String removeBook(long bookId);
	
	/* Method for getting Book List of a particular category
	 * pagenumber represent current page number  */
	public List<BookEntity> getBookListByCatogoryId(long catogoryId, int pagenumber, int itemsperpage);

	/* it returns the book information using book id */
	public BookEntity getBookDetailsByBookId(long bookId);
    
    
}


