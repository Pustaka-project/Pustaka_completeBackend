package com.pustaka.service;
import java.util.List;

import com.pustaka.dto.BookDTO;
import com.pustaka.dto.BuyBookDTO;


public interface BookService 
{
	public BookDTO addBook(BookDTO book, long userId);
	public String removeBook(long bookId);
    public List<BookDTO> getBookByUserId(long categoryId,long userId,int pagenumber);
    public String buyBook(long bookId,long userId);
	public String buyBookWithEmail(BuyBookDTO book);
}
