package com.sundongliang.service;

import java.util.List;













import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.sundongliang.entity.Article;
import com.sundongliang.entity.Category;
import com.sundongliang.entity.Channel;
import com.sundongliang.entity.Comment;
import com.sundongliang.entity.Complain;
import com.sundongliang.entity.Slide;

public interface ArticleService {

	/**
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	PageInfo<Article> listByUser(Integer id, int page);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int deletearticle(Integer id);

	/**
	 * 
	 * @return
	 */
	List<Channel> channelList();

	/**
	 * 
	 * @param cid
	 * @return
	 */
	List<Category> categoryList(Integer cid);

	/**
	 * 
	 * @param article
	 * @return
	 */
	int add(Article article);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Article getArticleId(Integer id);

	/**
	 * 
	 * @param article
	 * @return
	 */
	int updateArticle(Article article);

	PageInfo<Article> getHot(int page);

	List<Article> newList();

	List<Slide> getSlides();

	PageInfo<Comment> getComments(int id, int page);

	/**
	 * 
	 * @param comment
	 * @return
	 */
	int addComment(Comment comment);

	Article getArticlepage(int id, int articleid);

	/**
	 * 
	 * @param channelId
	 * @param catId
	 * @param page
	 * @return
	 */
	PageInfo<Article> getArticles(int channelId, int catId, int page);

	List<Category> getCategoriesByChannelId(int channelId);

	void addComplian(@Valid Complain complain);

	PageInfo<Complain> getPageInfoComplain(int page);

	

	Complain getComplainId(int id);

	List<Article> list_Article();

	void add_Article(List<Article> list_Article);

	List<Article> getArticle();

	void updatehits(Integer parseObject);

	

}
