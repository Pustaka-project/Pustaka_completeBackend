package com.pustaka.dao;

import org.springframework.stereotype.Repository;

import com.pustaka.dto.LoginDTO;
import com.pustaka.dto.UserDTO;
import com.pustaka.entity.BookEntity;
import com.pustaka.entity.UserEntity;

@Repository
public interface UserDao {

	/*
	 * returns true if user Email already exist 
	 */
	public boolean findUserByEmail(UserDTO user);
    
	
	/*
     * save the user details to user table  
     */
	public long addUser(UserEntity user);
	
	/*
	 * returns user details who is belongs to given loginDTO 
	 */
	public UserEntity authenticateUser(LoginDTO loginDTO);
	
	
	/*
	 * send the user details based on given userid
	 */
	public UserEntity getUserDetailsByUserId(long UserId);

}
