package com.pustaka.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pustaka.dao.BookDAO;
import com.pustaka.dao.BookUserDAO;
import com.pustaka.dao.UserDao;
import com.pustaka.dto.BookDTO;
import com.pustaka.dto.BuyBookDTO;
import com.pustaka.entity.BookEntity;
import com.pustaka.entity.BookUserEntity;
import com.pustaka.entity.CategoryEntity;
import com.pustaka.entity.UserEntity;
import com.pustaka.service.BookService;
import com.pustaka.util.Mail;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDAO bookDao;
	
	@Autowired
	private BookUserDAO bookUserDao;
	@Autowired
	private UserDao userDao;
	
	/*
	 * update book table as well as book_use table
	 */
	@Transactional
	public BookDTO addBook(BookDTO book, long userId) {
		BookEntity bookEntity = new BookEntity();
		bookEntity.setTitle(book.getTitle());
		bookEntity.setDescription(book.getDesc());
		bookEntity.setPrice(book.getPrice());
		bookEntity.setEdition(book.getEdition());
		bookEntity.setStatus(1);

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setCategoryId(book.getCategoryId());
		categoryEntity.setCategoryName(book.getCategoryName());

		bookEntity.setCategoryId(categoryEntity);

		long bookId = this.bookDao.addBook(bookEntity);
		long bookUserId = 0;
		if (bookId > 0) {
			BookUserEntity bookUserEntity = new BookUserEntity();
			bookUserEntity.setBookId(bookId);
			bookUserEntity.setStatus(1);
			bookUserEntity.setUserId(userId);

			bookUserId = bookUserDao.addMappingforBookUser(bookUserEntity);//book entity for book_user table

		}
		if (bookUserId > 0 && bookId > 0) {
			book.setMessage("Book has added sucessfully");
			book.setBookId(bookId);
		} else {
			book.setMessage("unsucessfull.. pls check");
		}
		return book;

	}
	/*
	 * updating status(remove book from sell list)
	 */

	@Transactional
	public String removeBook(long bookId) {
		String result=this.bookDao.removeBook(bookId);
		
		return result;
	}

	
	
	/*
	 * get all the books which are belongs to particular category id
	 * 
	 */
	@Override
	@Transactional
	public List<BookDTO> getBookByUserId(long categoryId,long userId,int pagenumber) {
		
		List<BookEntity> bookEntityList=bookDao.getBookListByCatogoryId(categoryId,pagenumber, 5);
		List<Long> bookIdList = bookUserDao.getUserBookMappingByUserId(userId);
		List<BookDTO> bookDTOList=new ArrayList<BookDTO>();
		for(BookEntity bookEntity:bookEntityList){
			BookDTO bookDTO=new BookDTO();
			bookDTO.setBookId(bookEntity.getBookId());
			bookDTO.setDesc(bookEntity.getDescription());
			bookDTO.setEdition(bookEntity.getEdition());
			bookDTO.setPrice(bookEntity.getPrice());
			bookDTO.setTitle(bookEntity.getTitle());
			bookDTO.setCategoryName(bookEntity.getCategoryId().getCategoryName());
			bookDTO.setCategoryId(bookEntity.getCategoryId().getCategoryId());
			
			if(bookIdList.contains(bookEntity.getBookId()))
				bookDTO.setBookOwner(1);
			else
				bookDTO.setBookOwner(0);
			
			bookDTOList.add(bookDTO);
		}
		return bookDTOList;
	}

	/*
	 * send mail to owner and user
	 */
	@Override
	@Transactional
	public String buyBook(long bookId, long userId) {
		// TODO Auto-generated method stub
		String result = "";
		String result1 = "success";
		try{
			BookEntity bookDetails=bookDao.getBookDetailsByBookId(bookId);
			UserEntity userDetails=userDao.getUserDetailsByUserId(userId);
			long ownerId=bookUserDao.getBookOwner(bookId);
			BookUserEntity bookUserEntity=new BookUserEntity();
			bookUserEntity.setBookId(bookId);
			bookUserEntity.setUserId(userId);
			bookUserEntity.setStatus(2);
			bookUserDao.addMappingforBookUser(bookUserEntity);
			UserEntity ownerDetails=userDao.getUserDetailsByUserId(ownerId);
			String userMailBody="Welcome to Pustaka App \n we came to know that ur intersted in buying the BOOk  \t"+bookDetails.getTitle()+"  \t we are sharing the owner information Seller details : "+ownerDetails.getFirstName()+"\n"+ownerDetails.getLastName()+" \n"+ownerDetails.getEmail()+"";
			Mail.sendMessage(userDetails.getEmail(), userMailBody, "owner details");
			UserEntity user=userDao.getUserDetailsByUserId(userId);
			result = "success";
			if(result1.equals(result))
			{
			
			String ownerMailBody="user "+userDetails.getFirstName()+" is intersted in Buying your book...\n hennce we are sharing user information with you.\n"+"Buyer Details:"+user.getFirstName()+"\n "+user.getLastName()+"\n "+user.getEmail()+"\n"+user.getMobile();
			Mail.sendMessage(ownerDetails.getEmail(), ownerMailBody, "user details");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			result = "failure";
		}
		return result;
	}

	@Override
	@Transactional
	public String buyBookWithEmail(BuyBookDTO book) {
		
		String result = "";
		String result1 = "success";
		try{
			BookEntity bookDetails=bookDao.getBookDetailsByBookId(book.getBooknumber());
			
			long ownerId=bookUserDao.getBookOwner(book.getBooknumber());
			UserEntity ownerDetails=userDao.getUserDetailsByUserId(ownerId);
			String userMailBody="Welcome to Pustaka App \n we came to know that ur intersted in buying the BOOk  \t"+bookDetails.getTitle()+"  \t we are sharing the owner information Seller details : "+ownerDetails.getFirstName()+"\n"+ownerDetails.getLastName()+" \n"+ownerDetails.getEmail()+"";
			Mail.sendMessage(book.getEmail(), userMailBody, "owner details");
			
			result = "success";
			if(result1.equals(result))
			{
			
			String ownerMailBody="user "+book.getEmail()+" is intersted in Buying your book...\n hennce we are sharing user information with you.\n"+"Buyer Details:";
			
			Mail.sendMessage(ownerDetails.getEmail(), ownerMailBody, "user details");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			result = "failure";
		}
		return result;
	}
	
}