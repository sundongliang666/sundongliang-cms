package com.sundongliang.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundongliang.entity.Article;
import com.sundongliang.entity.Category;
import com.sundongliang.entity.Channel;
import com.sundongliang.entity.Comment;
import com.sundongliang.entity.Complain;
import com.sundongliang.entity.Slide;
import com.sundongliang.mapper.ArticleMapper;
import com.sundongliang.mapper.ArticleRep;
import com.sundongliang.mapper.SlideMapper;
import com.sundongliang.momme.Cms;
import com.sundongliang.service.ArticleService;

/**
 * 
 * @author ASUS
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper ma;
	
	@Autowired
	SlideMapper slideMapper;
	
	
	@Autowired
	RedisTemplate redisTemplate;
	
	
	@Autowired
	ArticleRep articleRep;
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	
	
	public void add_Article(List<Article> articles){
		articleRep.saveAll(articles);
	}
	
	
	public List<Article> list_Article(){
		List<Article> articles = ma.list_Article();
		
		return articles;
		
	}
	
	@Override
	public PageInfo<Article> listByUser(Integer id, int page) {
		PageHelper.startPage(page, Cms.PAGE_KEY);
		List<Article> list =ma.listByUser(id);
		
		return new PageInfo<Article>(list);
	}


	@Override
	public int deletearticle(Integer id) {
		int deletearticle = ma.deletearticle(id);
		if(deletearticle>0){
			kafkaTemplate.send("article", "del="+id);
		}
		return deletearticle;
	}


	@Override
	public List<Channel> channelList() {
		
		return ma.channelList();
	}


	@Override
	public List<Category> categoryList(Integer cid) {
		
		return ma.categoryList(cid);
	}


	@Override
	public int add(Article article) {
		System.err.println("====================");
		return ma.add(article);
	}


	@Override
	public Article getArticleId(Integer id) {
		
		
		return ma.getArticleId(id);
	}


	@Override
	public int updateArticle(Article article) {
		int updateArticle = ma.updateArticle(article);
		if(updateArticle>0){
			kafkaTemplate.send("article", "del="+article.getId());
			articleRep.deleteById(article.getId());
		}
		return updateArticle;
	}


	@Override
	public PageInfo<Article> getHot(int page) {
		//进行分页
		//PageHelper.startPage(page, Cms.PAGE_KEY);
		//从Redis中查找热门文章
		List<Article> range = redisTemplate.opsForList().range(Cms.REMENG,0, -1);
		//判断Redis中是否有热门文章
		
		if(range==null || range.size()==0){
			//System.err.println("从MySQL中获取");
			//没有热门文章从MySQL中查找热门文章
			List<Article> list = ma.getHot();
			//System.err.println(list.size());
			//将从MySQL中查找的热门文章放到Redis中
			redisTemplate.opsForList().leftPushAll(Cms.REMENG, list.toArray());
			//设置Redis的过期时间
			redisTemplate.expire(Cms.REMENG, 5, TimeUnit.MINUTES);
			
			List<Article> range1 = redisTemplate.opsForList().range(Cms.REMENG, (page-1)*Cms.PAGE_KEY, ((page-1)*Cms.PAGE_KEY)+Cms.PAGE_KEY-1);
			//将热门文章返回
			PageInfo<Article> pageInfo = new PageInfo<Article>(range1);
			pageInfo.setPageNum(page);
			pageInfo.setSize(Cms.PAGE_KEY);
			int pages =0;
			if(list.size()%Cms.PAGE_KEY==0){
				pages =list.size()%Cms.PAGE_KEY;
			}else{
				pages =list.size()%Cms.PAGE_KEY+1;
			}
			pageInfo.setPages(pages);
			pageInfo.setTotal(list.size());
			if(page-1>0){
				pageInfo.setPrePage(page-1);
			}else{
				pageInfo.setPrePage(0);
			}
			if(page+1<=pageInfo.getPages()){
				pageInfo.setLastPage(page+1);
			}else{
				pageInfo.setLastPage(0);
			}
			pageInfo.setLastPage(page+1);
			return pageInfo;
		}
		//System.err.println("从Redis中获取");
		//有热门文章直接返回
		List<Article> range1 = redisTemplate.opsForList().range(Cms.REMENG, (page-1)*Cms.PAGE_KEY, ((page-1)*Cms.PAGE_KEY)+Cms.PAGE_KEY-1);
		//将热门文章返回
		PageInfo<Article> pageInfo = new PageInfo<Article>(range1);
		pageInfo.setPageNum(page);
		pageInfo.setSize(Cms.PAGE_KEY);
		int pages =0;
		if(range.size()%Cms.PAGE_KEY==0){
			pages =range.size()%Cms.PAGE_KEY;
		}else{
			pages =range.size()%Cms.PAGE_KEY+1;
		}
		pageInfo.setPages(pages);
		pageInfo.setTotal(range.size());
		if(page-1>0){
			pageInfo.setPrePage(page-1);
		}else{
			pageInfo.setPrePage(0);
		}
		if(page+1<=pageInfo.getPages()){
			pageInfo.setLastPage(page+1);
		}else{
			pageInfo.setLastPage(0);
		}
		pageInfo.setLastPage(page+1);
		return pageInfo;
	}


	@Override
	public List<Article> newList() {
		//判断Redis中是否有最新文章
		List<Article> range = redisTemplate.opsForList().range(Cms.NEW_ARTICLE, 0, -1);
		//System.err.println(range.size());
		if(range==null || range.size()==0){
		//没有从MySQL中查询
			
		List<Article> newList = ma.newList(Cms.PAGE_KEY);
		System.err.println("走MySQL");
		System.err.println(newList.size());
		//存入Redis中
		for (Article article : newList) {
			redisTemplate.opsForList().leftPush(Cms.NEW_ARTICLE, article);
		}
		//设置过期时间
		redisTemplate.expire(Cms.NEW_ARTICLE, 5, TimeUnit.MINUTES);
		return newList;
		}
		//有直接返回
		return range;
	}


	@Override
	public List<Slide> getSlides() {
		// TODO Auto-generated method stub
		return slideMapper.list();
	}


	@Override
	public PageInfo<Comment> getComments(int id, int page) {
		PageHelper.startPage(page, Cms.PAGE_KEY);
		List<Comment> list = ma.getComments(id);
		return new PageInfo<Comment>(list);
	}


	@Override
	public int addComment(Comment comment) {
		
		ma.updateCommentCnt(comment.getArticleId());
		return ma.addComment(comment);
	}


	@Override
	public Article getArticlepage(int id, int articleid) {
		Article atricle=null;
		if(id>articleid){
			atricle = ma.getArticlepre(articleid);
		}else{
			atricle = ma.getArticlnext(articleid);
		}
		return atricle;
	}


	@Override
	public PageInfo<Article> getArticles(int channelId, int catId, int page) {
		PageHelper.startPage(page, Cms.PAGE_KEY);
		List<Article> list =ma.getArticles(channelId,catId);
		return new PageInfo<Article>(list);
	}


	@Override
	public List<Category> getCategoriesByChannelId(int channelId) {
		
		return ma.getCategoriesByChannelId(channelId);
	}


	@Override
	public void addComplian(@Valid Complain complain) {
		
		// TODO Auto-generated method stub
		
				//添加投诉到数据库
				int result = ma.addCoplain(complain);
				// 增加投诉的数量
				if(result>0)
					ma.increaseComplainCnt(complain.getArticleId());
				
				
	}


	@Override
	public PageInfo<Complain> getPageInfoComplain(int page) {
		PageHelper.startPage(page, Cms.PAGE_KEY);
		
		return new PageInfo<Complain>(ma.getComplain());
	}


	@Override
	public Complain getComplainId(int id) {
		// TODO Auto-generated method stub
		return ma.getComplainId(id);
	}


	@Override
	public List<Article> getArticle() {
		// TODO Auto-generated method stub
		return ma.getArticleList();
	}


	@Override
	public void updatehits(Integer id) {
		int hist= ma.gethits(id);
		ma.updatehits(id,(hist+1));
		
	}

}
