package com.pustaka.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pustaka.dto.BookDTO;
import com.pustaka.dto.BuyBookDTO;
import com.pustaka.service.BookService;
/**
 * This is the Controller class for managing all Book API's
 */
@RestController
@RequestMapping("/book")
public class BookController {

	/*
	 * we are using "Autowired" annotation to inject the object dependency
	 * implicitly of this particular class
	 */
	@Autowired
	BookService bookService;

	/*
	 *This API is used to add books into Database of "Pustaka_Book" table.
	 *We use Book DT0 class as a communicator between Fronted and Backed.
	 *url pattern is given in 'value' as /addBook/{userID}.
	 *userId represents current user. 
	 */
	@RequestMapping(value = "/addbook/{userId}", method = RequestMethod.POST)
	public BookDTO addBook(@RequestBody BookDTO book, HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") long userId) {
		BookDTO bookDTO = null;
		try {
			bookDTO = bookService.addBook(book, userId); // Calling addBook() of Bookservice. 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookDTO;
	}

	/*
	 *This API is used to Update Books status of a book in Pustaka_Book.
	 *url pattern is given in 'value' as "updateBookStatus/{bookId}, 
	 *where bookId is used as reference for a particular table where owner will change this 
	 *status. 
	 */
	@RequestMapping(value = "/updateBookStatus/{bookId}", method = RequestMethod.GET)
	public String removeBook(@PathVariable("bookId") long bookId, HttpServletRequest request,
			HttpServletResponse response) {
		String result = bookService.removeBook(bookId);
		return result;
	}
	
	/*
	 *This API is used to get booklist in Database of "Pustaka_Book" table.
	 *url pattern is given in 'value'. "/getbooklist/{userId}/{categoryId}/{pagenumber}
	 *where "userId" is current user's Id
	 *"categoryId" is the category in which user is searching for book
	 *"pagenumber" represents current page-number, it's further used to pagination.
	 */
	@RequestMapping(value = "/getbooklist/{categoryId}/{pagenumber}/{userId}", method = RequestMethod.GET)
	public List<BookDTO> getBookList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") long userId, @PathVariable("categoryId") long categoryId,
			@PathVariable("pagenumber") int pagenumber) {

		List<BookDTO> result = null;

		try {
			result = bookService.getBookByUserId(categoryId, userId, pagenumber);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return result;
	}

	/*
	 *This API is to get Owner and Buyer details from Database of "Pustaka_Book_User" table.
	 *we use this API to send message to both parties.
	 */
	@RequestMapping(value = "/buybook/{bookId}/{userId}", method = RequestMethod.GET)
	public String buyBook(HttpServletRequest request, HttpServletResponse response, @PathVariable("userId") long userId,
			@PathVariable("bookId") long bookId) {
		String result = null;
		try {
			result = bookService.buyBook(bookId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/buyWithEmail", method = RequestMethod.POST)
	public String buy(HttpServletRequest request, HttpServletResponse response, @RequestBody BuyBookDTO book) {
		String result = null;
		try {
			result = bookService.buyBookWithEmail(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}