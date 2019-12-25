package com.sundongliang.controller;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sundongliang.entity.Car;
import com.sundongliang.entity.DriveType;
import com.sundongliang.entity.Driver;
import com.sundongliang.entity.DriverTwo;
import com.sundongliang.service.DriverService;


@Controller
public class DriverController {
	static List<Car> lists;
	@Autowired
	private DriverService service;
	
	//传发布车辆的303对象
	@RequestMapping("toFb.do")
	public String toFb(Model m){
		List<DriveType> list = service.list();
		m.addAttribute("c", new Car());
		m.addAttribute("list", list);
		return "fb";
	}
	//发布车辆
	@RequestMapping("fb.do")
	public String fb(@Valid @ModelAttribute("c")Car c,BindingResult result,Model m,@RequestParam("f")MultipartFile file,HttpServletRequest request){
		System.out.println(c);
		//判断是否验证成功
		if(result.hasErrors()){
			List<DriveType> list = service.list();
			m.addAttribute("list", list);
			return "fb";
		}
		try {
			//创建存放图片的路径
			String realPath = request.getSession().getServletContext().getRealPath("/img");
			
			File file2 = new File(realPath+"/"+file.getOriginalFilename());
			//判断是否用文件
			if(!file2.exists()){
				//没有则创建
				file2.mkdirs();
			}
			file.transferTo(file2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//获取文件图片的路径
		String path = "img"+"/"+file.getOriginalFilename();
		c.setPic_url(path);
		
		System.out.println(c);
		//执行车辆添加方法
		service.add(c);
		return "login";
	}
	
	//传注册方法的303验证对象
	@RequestMapping("toZc.do")
	public String toZc(Model m){
		List<DriveType> list = service.list();
		m.addAttribute("d", new Driver());
		m.addAttribute("list", list);
		return "zc";
	}
	//注册
	@RequestMapping("zc.do")
	public String zc(@Valid @ModelAttribute("d")Driver d,BindingResult result,Model m){
		//判断是否符合验证规则
		if(result.hasErrors()){
			List<DriveType> list = service.list();
			m.addAttribute("d", d);
			m.addAttribute("list", list);
			return "zc";
		}
		System.out.println(d);
		//执行添加司机方法
		/*service.zc(d);*/
		return "login";
		
	}
	//传登陆方法的303验证对象
	@RequestMapping("toLogin.do")
	public String toLogin(Model m){
		m.addAttribute("d", new DriverTwo());
		return "login";
	}
	/*//登陆
	@RequestMapping("login.do")
	public String login(@Valid @ModelAttribute("d")DriverTwo d,BindingResult result,Model m,HttpSession s){
		System.out.println(d);
		Driver d1 = service.getList(d);
		System.out.println(d1);
		if(result.hasErrors()){
			return "login";
		}
		//判断根据手机号和密码查询的数据是否存在
		if(d1!=null){
			System.out.println("哈哈哈");
			s.setAttribute("loginu", d1);
			m.addAttribute("d1", d1);
			String a =  service.driver(d1);
			System.out.println(a);
			String[] split = a.split(",");
			System.out.println(split[0]);
			System.out.println(split.length);
			
				
				 lists = service.cars(split[0]);
				 
			
			System.out.println(lists);
			m.addAttribute("lists", lists);
			return "list";
		}
		m.addAttribute("d", d);
		return "login";*/
	
	//修改方法回显
	@RequestMapping("toUpdate.do")
	public String toUpdate(Car c,Model m){
		System.out.println(c);
		c = service.toUpdate(c);
		List<DriveType> list = service.list();
		m.addAttribute("list", list);
		m.addAttribute("c", c);
		return "update";
	}
	
	//修改的方法
	@RequestMapping("update.do")
	public String update(@Valid @ModelAttribute("c")Car c,BindingResult result,Model m,@RequestParam("f")MultipartFile file,HttpServletRequest request){
		System.out.println(c);
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/img");
			File file2 = new File(realPath+"/"+file.getOriginalFilename());
			if(!file2.exists()){
				file2.mkdirs();
			}
			file.transferTo(file2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String path = "img"+"/"+file.getOriginalFilename();
		c.setPic_url(path);
		System.out.println(c);
		//执行修改的方法
		service.update(c);
		return "redirect:login.do";
	}
	
	
}
