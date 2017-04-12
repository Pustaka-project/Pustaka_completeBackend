package com.pustaka.serviceImpl;


import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pustaka.dao.UserDao;
import com.pustaka.dto.LoginDTO;
import com.pustaka.dto.UserDTO;
import com.pustaka.entity.UserEntity;
import com.pustaka.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	/*
	 * for registration 
	 */
	@Override
	@Transactional
	public String addUser(UserDTO user) {

		boolean isUserExists = userDao.findUserByEmail(user);

		String result=null;
		if (!isUserExists) {
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setCity(user.getCity());
			userEntity.setState(user.getState());
			userEntity.setCountry(user.getCountry());
			userEntity.setEmail(user.getEmail());
			userEntity.setPinCode(user.getPinCode());
			userEntity.setMobile(user.getMobile());
			userEntity.setUserId(user.getUserId());
			userEntity.setPassword(user.getPassword());
			long userId = userDao.addUser(userEntity);
			if(userId>0)
				result="User Registered Successfully!";
			else
				result="Registration Failed!";
			
		}else{
			
			result="User Already Exist";
			
		}

		return result;  
	}
	
	/*
	 * authenticate user for login 
	 */
	@Override
	@Transactional
	public UserDTO authenticateUser(LoginDTO loginDTO) {
		UserEntity userEntity = userDao.authenticateUser(loginDTO);
		UserDTO userDTO = null;
		if (userEntity != null) {
			userDTO = new UserDTO();
			userDTO.setFirstName(userEntity.getFirstName());
			userDTO.setLastName(userEntity.getLastName());
			userDTO.setCity(userEntity.getCity());
			userDTO.setState(userEntity.getState());
			userDTO.setEmail(userEntity.getEmail());
			userDTO.setMobile(userEntity.getMobile());
			userDTO.setUserId(userEntity.getUserId());

		}

		return userDTO;
	}

}
