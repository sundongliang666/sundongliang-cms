package com.sundongliang.controller;





import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;








import com.github.pagehelper.PageInfo;
import com.sundongliang.common.CmsMessage;
import com.sundongliang.entity.Article;
import com.sundongliang.entity.Link;
import com.sundongliang.momme.Cms;
import com.sundongliang.service.LockedService;

/**
 * 
 * @author ASUS
 *
 */
@RequestMapping("/locked")
@Controller
public class LockedController {

	@Autowired
	LockedService  lockedService;
	
	@RequestMapping("/article")
	public String article(@RequestParam(defaultValue="0")Integer status,
			@RequestParam(defaultValue="1")Integer page,
			Model m){
		
		PageInfo<Article> list = lockedService.getArticle(status,page);
		m.addAttribute(Cms.ARTICLE_KEY, list);
		m.addAttribute("status", status);
		return "/user/admin/list";
	}
	
	
	@RequestMapping("setArticeHot")
	@ResponseBody
	public CmsMessage setArticeHot(Integer id,Integer status){
		/**
		 * 数据合法性校验 
		 */
		if(status !=1 && status!=0) {
			return new CmsMessage(Cms.NOT_VALIDATED_ARGURMENT,"status参数值不合法",null);
		}
		
		if(id<0) {
			return new CmsMessage(Cms.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		
		Article article = lockedService.getInfoById(id);
		if(article==null) {
			return new CmsMessage(Cms.NOT_EXIST,"数据不存在",null);
		}
		
		/**
		 * 
		 */
		if(article.getHot()==status) {
			return new CmsMessage(Cms.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		/**
		 *  修改数据
		 */
		int result = lockedService.setArticeHot(id,status);
		if(result<1)
			return new CmsMessage(Cms.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(Cms.SUCCESS,"成功",null);
	}
	
	
	@RequestMapping("setArticeStatus")
	@ResponseBody
	public CmsMessage setArticeStatus(Integer id,Integer status){
		/**
		 * 数据合法性校验 
		 */
		if(status !=1 && status!=2) {
			return new CmsMessage(Cms.NOT_VALIDATED_ARGURMENT,"status参数值不合法",null);
		}
		
		if(id<0) {
			return new CmsMessage(Cms.NOT_VALIDATED_ARGURMENT,"id参数值不合法",null);
		}
		
		Article article = lockedService.getInfoById(id);
		if(article==null) {
			return new CmsMessage(Cms.NOT_EXIST,"数据不存在",null);
		}
		
		/**
		 * 
		 */
		if(article.getStatus()==status) {
			return new CmsMessage(Cms.NEEDNT_UPDATE,"数据无需更改",null);
		}
		
		/**
		 *  修改数据
		 */
		int result = lockedService.setCheckStatus(id,status);
		if(result<1)
			return new CmsMessage(Cms.FAILED_UPDATE_DB,"设置失败，请稍后再试",null);
		
		
		return new CmsMessage(Cms.SUCCESS,"成功",null);
	}
	
	
	@RequestMapping("link")
	public String link(@RequestParam(defaultValue="1")int page,HttpServletRequest request){
		PageInfo<Link>  pageInfolink= lockedService.getLinkList(page); 
		request.setAttribute("pageInfolink", pageInfolink);
		return "/user/admin/link";
		
	}
	
}
