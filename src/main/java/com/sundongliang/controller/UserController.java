package com.sundongliang.controller;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;






















import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;













import com.sundongliang.cms.utils.StringUtils;
import com.sundongliang.common.CmsMessage;
import com.sundongliang.entity.User;
import com.sundongliang.momme.Cms;
import com.sundongliang.service.UserService;
/**
 * 
 * @author ASUS
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService ser;
	
	
	
	
	
	
	
	/**
	 * index
	 */
	@RequestMapping("index")
	public String index(){
		
		return "redirect:/";
	}
	
	
	
	/**
	 * 去往注册页面
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String home(Model m){
		m.addAttribute("user", new User());
		return "/user/register";
	}
	
	/**
	 * 进行用户注册
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user,BindingResult result,Model m){
		System.out.println(1);
		User u=ser.getUserName(user.getUsername());
		if(u!=null){
			result.rejectValue("username", "", "用户名已存在");
		}
		if(StringUtils.isNumber(user.getPassword())){
			result.rejectValue("password", "", "密码太简单了");
		}
		if(result.hasErrors()){
			m.addAttribute("user", user);
			return "/user/register";
		}
		int i =ser.registerUser(user);
		if(i<1){
			result.rejectValue("id", "", "注册失败，请稍后再试");
			return "/user/register";
		}
		return "redirect:/user/login";
	}
	
	/**
	 * 进行姓名认证
	 * @param username
	 * @return
	 */
	@RequestMapping("/checkname")
	@ResponseBody
	public Object checkname(String username){
		User u=ser.getUserName(username);
		return u==null;
	}
	
	
	
	/**
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model m,HttpServletResponse response,HttpServletRequest request){
		
		m.addAttribute("user", new User());
		return "/user/login";
	}
	
	
	
	
	/**
	 * 
	 * @param user
	 * @param result
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@Valid @ModelAttribute("user") User user,
			BindingResult result,
			Model m,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(defaultValue="0")int ck){
		String pwd = user.getPassword();
		if(result.hasErrors()){
			m.addAttribute("user", user);
			return "/user/login";
		}
		User  u =ser.getUser(user);
		if(u==null){
			result.rejectValue("id", "", "登录失败，用户名或密码错误");
			return "/user/login";
		}
		if(ck==2){
		//保存用户的用户名和密码
		Cookie cookieUserName = new Cookie("username", user.getUsername());
		cookieUserName.setPath("/");
		cookieUserName.setMaxAge(10*24*3600);// 10天
		response.addCookie(cookieUserName);
		Cookie cookieUserPwd = new Cookie("userpwd", pwd);
		cookieUserPwd.setPath("/");
		cookieUserPwd.setMaxAge(10*24*3600);// 10天
		response.addCookie(cookieUserPwd);
		}
		
		
		session.setAttribute(Cms.USER, u);
		if(u.getLocked()==0)
		return "redirect:/user/home";
		
		
		return "redirect:/user/admin";
	}
	/**
	 * 
	 */
	@RequestMapping("/admin")
	public String admin(){
		
		return "/user/admin/locked";
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/home")
	public String home(){
		
		return "/user/home";
	}
	
	
	
	
	
	
	/**
	 * exit
	 */
	@RequestMapping("/exit")
	public String exit(HttpSession session,HttpServletResponse response){
		session.removeAttribute(Cms.USER);
		Cookie cookieUserName = new Cookie("username", "");
		cookieUserName.setPath("/");
		cookieUserName.setMaxAge(0);// 立即过期
		response.addCookie(cookieUserName);
		Cookie cookieUserPwd = new Cookie("userpwd", "");
		cookieUserPwd.setPath("/");
		cookieUserPwd.setMaxAge(0);// 立即过期
		response.addCookie(cookieUserPwd);
		return "redirect:/user/login";
	}
	/**
	 * tologin
	 */
	@RequestMapping("/tologin")
	@ResponseBody
	public CmsMessage tologin(String name,String pwd,HttpServletRequest request,HttpServletResponse response){
		User user = ser.getToUser(name,pwd);
		if(user==null){
			return  new CmsMessage(Cms.NOT_EXIST, "用户名或密码错误", "");
		}
		request.getSession().setAttribute(Cms.USER, user);
			//保存用户的用户名和密码
				Cookie cookieUserName = new Cookie("username", user.getUsername());
				cookieUserName.setPath("/");
				cookieUserName.setMaxAge(10*24*3600);// 10天
				response.addCookie(cookieUserName);
				Cookie cookieUserPwd = new Cookie("userpwd", pwd);
				cookieUserPwd.setPath("/");
				cookieUserPwd.setMaxAge(10*24*3600);// 10天
				response.addCookie(cookieUserPwd);
		 return  new CmsMessage(Cms.SUCCESS, "", "登录成功");
	}
}
