package com.sundongliang.service;

import javax.validation.Valid;

import com.sundongliang.entity.User;

public interface UserService {

	/**
	 * 
	 * @param username
	 * @return
	 */
	User getUserName(String username);

	/**
	 * 
	 * @param user
	 * @return
	 */
	int registerUser(User user);

	User getUser(@Valid User user);

	/**
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	User getToUser(String name, String pwd);

}
