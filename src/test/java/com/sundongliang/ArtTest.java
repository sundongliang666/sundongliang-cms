package com.sundongliang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.aspectj.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.sundongliang.cms.utils.FileUtilIO;
import com.sundongliang.entity.Article;
import com.sundongliang.service.ArticleService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ArtTest {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	ArticleService articleService;
	
	@Test
	public void artTest(){
		File f =new File("E:\\1708E");
		String[] list = f.list();
		Article article = new Article();
		for (String string : list) {
			int lastIndexOf = string.lastIndexOf(".");
			String substring = string.substring(0, lastIndexOf);
			article.setTitle(substring);
			File ff =new File("E:\\1708E"+"\\"+string);
			try {
				FileReader fr=new FileReader(ff);
				BufferedReader br =new BufferedReader(fr);
				String nn="";
				String nr="";
				while ((nn=br.readLine())!=null) {
					nr+=nn+"<br/>";
				}
				
				article.setContent(nr);
				article.setChannelId(1);
				article.setCategoryId(1);
				article.setUserId(73);
				article.setArticleType(0);
				article.setPicture("");
				//articleService.add(article);
				String jsonString = JSON.toJSONString(article);
				kafkaTemplate.send("sundongliang", "ArticleAdd", jsonString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void addMySQLArticle(){
		kafkaTemplate.send("sundongliang","addMySQLArticle","");
	}
	
}
