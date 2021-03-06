package com.sundongliang.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;






import com.sundongliang.entity.User;
import com.sundongliang.momme.Cms;
import com.sundongliang.service.UserService;



public class CmsInterceptor implements HandlerInterceptor{

	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// TODO Auto-generated method stub
		User loginUser = (User)request.getSession().getAttribute(Cms.USER);
		if(loginUser != null)
		{
			return true;
		}
		
		
		
		
		// 从cookie 当中获取用户信息
				User user = new User();
				Cookie[] cookies = request.getCookies();
				for (int i = 0; i < cookies.length; i++) {
					if("username".equals(cookies[i].getName())){
						user.setUsername(cookies[i].getValue());
					}
					if("userpwd".equals(cookies[i].getName())){
						user.setPassword(cookies[i].getValue());
					}
				}
				
				// 说明cookie中存放的用户信息不完整
				if(null==user.getUsername() || null== user.getPassword()) {
					//request.s
					response.sendRedirect("/user/login");
					//request.getRequestDispatcher("/user/login").forward(request, response);
					return false;
				}
				
				// 利用cookie中用户信息进行登录操作
				loginUser = userService.getUser(user);
				if(loginUser!=null) {
					request.getSession().setAttribute(Cms.USER, loginUser);
					return true;
				}
				
				//request.s
				response.sendRedirect("/user/login");
		
		//放行 
		return true;
		
		
	}
	
}
