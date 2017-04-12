 package com.pustaka.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pustaka.dto.LoginDTO;
import com.pustaka.dto.UserDTO;
import com.pustaka.service.UserService;

/*
 * This is the Controller class for managing all User
 */
@RestController
@RequestMapping("/user")
public class UserController {
	/*
	 * we are using "Autowired" annotation to inject the object dependency
	 * implicitly of this particular class
	 */
	@Autowired
	UserService userService;

	/*
	 *
	 * userDTO-A DTO class consists required properties for registering the the
	 * user! request,@param response,
	 * 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String userRegister(@RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			result = userService.addUser(userDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * login DTO contains email id and password
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UserDTO userLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletRequest responce) {
		UserDTO userDTO = null;
		try {
			userDTO = userService.authenticateUser(loginDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDTO;
	}

}
