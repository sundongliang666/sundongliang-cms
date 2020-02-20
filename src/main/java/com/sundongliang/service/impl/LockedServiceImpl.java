package com.sundongliang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundongliang.entity.Article;
import com.sundongliang.entity.Link;
import com.sundongliang.mapper.ArticleMapper;
import com.sundongliang.mapper.ArticleRep;
import com.sundongliang.mapper.LockedMapper;
import com.sundongliang.momme.Cms;
import com.sundongliang.service.LockedService;


@Service
public class LockedServiceImpl implements LockedService {

	@Autowired
	LockedMapper lockedMapper;

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	ArticleRep articleRep;
	
	
	@Override
	public PageInfo<Article> getArticle(Integer status, Integer page) {
		PageHelper.startPage(page, Cms.PAGE_KEY);
		List<Article> list=null;
		if(status==-1){
		 list = lockedMapper.getArticleqc();
		}else{
		list = lockedMapper.getArticle(status);
		}
		PageInfo<Article> pageInfo = new PageInfo<Article>(list);
		return pageInfo;
	}

	@Override
	public Article getInfoById(Integer id) {
		// TODO Auto-generated method stub
		return lockedMapper.getInfoById(id);
	}

	@Override
	public int setCheckStatus(Integer id, Integer status) {
		// TODO Auto-generated method stub
		int setCheckStatus = lockedMapper.setCheckStatus(id,status);
		Article articleId2 = articleMapper.getArticleId(id);
		articleRep.save(articleId2);
		if(status==1&&setCheckStatus==1){
		Article articleId = articleMapper.getArticleId(setCheckStatus);
		String jsonString = JSON.toJSONString(articleId);
		kafkaTemplate.send("article","add="+jsonString);
		}
		return setCheckStatus;
	}

	@Override
	public int setArticeHot(Integer id, Integer status) {
		// TODO Auto-generated method stub
		return lockedMapper.setArticeHot(id,status);
	}

	@Override
	public PageInfo<Link> getLinkList(int page) {
		PageHelper.startPage(page, 40);
		List<Link> list = lockedMapper.getLinkList();
		
		return new PageInfo<Link>(list);
	}

	
	
	
	
}
