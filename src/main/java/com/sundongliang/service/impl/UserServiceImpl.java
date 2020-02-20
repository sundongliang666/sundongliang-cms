package com.sundongliang.service.impl;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sundongliang.common.CmsUtils;
import com.sundongliang.entity.User;
import com.sundongliang.mapper.UserMapper;
import com.sundongliang.service.UserService;


/**
 * 
 * @author ASUS
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper ma;
	
	/**
	 * 
	 */
	@Override
	public User getUserName(String username) {
		// TODO Auto-generated method stub
		return ma.getUserName(username);
	}

	/**
	 * 
	 */
	@Override
	public int registerUser(User user) {
		user.setPassword(CmsUtils.encry(user.getPassword(), user.getUsername()));
		return ma.registerUser(user);
	}

	@Override
	public User getUser(User user) {
		user.setPassword(CmsUtils.encry(user.getPassword(), user.getUsername()));
		return ma.setPassword(user);
	}

	@Override
	public User getToUser(String name, String pwd) {
		String pwwd = CmsUtils.encry(pwd, name);
		
		return ma.getToUser(name,pwwd);
	}

}
