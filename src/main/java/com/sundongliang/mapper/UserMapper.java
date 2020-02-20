package com.sundongliang.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sundongliang.entity.User;

public interface UserMapper {

	/**
	 * 
	 * @param username
	 * @return
	 */
	@Select("SELECT id,username,`password` FROM cms_user WHERE username=#{username} LIMIT 1")
	User getUserName(String username);

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Insert("INSERT INTO cms_user(username,password,locked,create_time,score,role)"
			+ " VALUES(#{username},#{password},0,now(),0,0)")
	int registerUser(User user);

	@Select("SELECT * FROM cms_user WHERE username=#{username} and password=#{password} LIMIT 1")
	User setPassword(User user);

	@Select("SELECT * FROM cms_user WHERE username=#{name} and password=#{pwwd} LIMIT 1")
	User getToUser(@Param("name")String name,@Param("pwwd") String pwwd);

}
