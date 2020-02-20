package com.sundongliang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.sundongliang.entity.Article;
import com.sundongliang.service.RedisArticle;



@Service
public class RedisArticleService implements RedisArticle{

	@Autowired
	RedisTemplate<String, Article> redisTemplate;
	
	public void save(Article article){
		redisTemplate.opsForList().leftPush("redis_article", article);
	}
}
