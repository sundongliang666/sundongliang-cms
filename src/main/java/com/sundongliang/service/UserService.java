package com.sundongliang.service;

import javax.validation.Valid;

import com.sundongliang.entity.User;


public interface UserService {

	User getUserByUsername(String username);

	int register(@Valid User user);

	/**
	 * 登录操作
	 * @param user
	 * @return
	 */
	User login(User user);

}
