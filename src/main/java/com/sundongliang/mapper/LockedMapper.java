package com.sundongliang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sundongliang.entity.Article;
import com.sundongliang.entity.Link;

public interface LockedMapper {

	List<Article> getArticle(Integer status);

	Article getInfoById(Integer id);

	@Update("UPDATE cms_article set status=#{status} where id=#{id}")
	int setCheckStatus(@Param("id")Integer id,@Param("status") Integer status);

	@Update("UPDATE cms_article set hot=#{status} where id=#{id}")
	int setArticeHot(@Param("id")Integer id,@Param("status") Integer status);

	@Select("SELECT * FROM cms_link")
	List<Link> getLinkList();

	List<Article> getArticleqc();

}
