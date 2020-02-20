package com.sundongliang.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.sundongliang.entity.Article;
import com.sundongliang.mapper.ArticleRep;
import com.sundongliang.service.ArticleService;
import com.sundongliang.service.RedisArticle;

public class ArticleListener implements MessageListener<String, String>{

	@Autowired
	ArticleService articleService;
	
	@Autowired
	ArticleRep articleRep;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	RedisArticle article;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		String value = data.value();
		System.err.println("收到信息=============");
		String key = data.key();
		System.out.println(key);
		if("addArticle".endsWith(key)){
			Article parseObject = JSON.parseObject(value, Article.class);
			articleService.add(parseObject);
		}else if("dianji".endsWith(key)){
			Integer parseObject = JSON.parseObject(value, Integer.class);
			articleService.updatehits(parseObject);
		}else if("ArticleAdd".equals(key)){
			
			Article parseObject = JSON.parseObject(value, Article.class);
			
			article.save(parseObject);
			
		}else if("addMySQLArticle".equals(key)){
			List<Article> range = redisTemplate.opsForList().range("redis_article", 0, -1);
			
			for (Article article : range) {
				articleService.add(article);
			}
		}
		
		if(value.startsWith("add")){
			
			String[] split = value.split("=");
			
			Article parseObject = JSON.parseObject(split[1], Article.class);
			
			System.err.println("es"+"添加方法");
			
			articleRep.save(parseObject);
			
		}else if(value.startsWith("del")){
			
			String[] split = value.split("=");
			
			System.err.println("es"+"删除方法");
			
			articleRep.deleteById(Integer.parseInt(split[1]));
		}
		//Article parseObject = JSON.parseObject(value, Article.class);
		//articleService.add(parseObject);
	}

}
