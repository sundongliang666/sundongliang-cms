package com.sundongliang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.sundongliang.entity.ShouCang;



public interface ShouCangMapper {

	@Insert("INSERT INTO shoucang VALUES(NULL,#{text},#{url},#{user_id},NOW())")
	int addShouCang(ShouCang shouCang);

	
	@Delete("DELETE FROM shoucang WHERE id=#{value}")
	int delShouCangById(Integer id);

	@Select("SELECT * FROM shoucang WHERE user_id=#{value} ORDER BY created DESC")
	List<ShouCang> findShouCang(Integer id);

}
