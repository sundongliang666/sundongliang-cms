package com.sundongliang;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sundongliang.entity.Article;
import com.sundongliang.mapper.ArticleRep;
import com.sundongliang.service.ArticleService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class EsTest {

	@Autowired
	ArticleService articleService;
	
	
	@Autowired
	ArticleRep articleRep;
	
	
	@Test
	public void esTest(){
		//获取所有审核通过的文章
		List<Article> list= articleService.getArticle();
		//便利文章
		for (Article article : list) {
			//将文章放入es中
			articleRep.save(article);
		}
		
	}


	
	
}
