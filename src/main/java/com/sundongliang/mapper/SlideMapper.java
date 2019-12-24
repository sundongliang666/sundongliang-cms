package com.sundongliang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sundongliang.entity.Slide;


/**
 * 轮播图管理
 * @author 
 *
 */
public interface SlideMapper {

	@Select("SELECT id,title,picture,url FROM cms_slide ORDER BY id ")
	List<Slide> list();

}
