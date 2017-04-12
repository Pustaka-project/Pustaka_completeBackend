package com.pustaka.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pustaka.entity.BookUserEntity;

/**A Repository represents all objects of a certain type as a conceptual set. 
 * It acts like a collection, except with more elaborate querying capability.*/
@Repository
public interface BookUserDAO 
{
	/* returns list of userid based on userid   */                                          
	public long addMappingforBookUser(BookUserEntity bookUser);
	
	/* returns book details added by that particular user*/
	public List<Long> getUserBookMappingByUserId(long userId);
	
	/* To get the Book owner who added that book based on that bookId */
	public long getBookOwner(long bookId);
	
	

}
