package com.sundongliang.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundongliang.cms.utils.FileUtils;
import com.sundongliang.cms.utils.HtmlUtils;
import com.sundongliang.cms.utils.StringUtils;
import com.sundongliang.common.CmsError;
import com.sundongliang.common.CmsMessage;
import com.sundongliang.common.FileResult;
import com.sundongliang.entity.Article;
import com.sundongliang.entity.Category;
import com.sundongliang.entity.Channel;
import com.sundongliang.entity.Comment;
import com.sundongliang.entity.Complain;
import com.sundongliang.entity.ShouCang;
import com.sundongliang.entity.User;
import com.sundongliang.momme.Cms;
import com.sundongliang.service.ArticleService;
import com.sundongliang.service.ShouCangService;



/*
 * 
 */
@RequestMapping("/article")
@Controller
public class ArticleController {

	@Value("${upload.path}")
	String picRootPath;
	
	@Value("${pic.path}")
	String picUrl;
	
	
	private static Logger log = Logger.getLogger(ArticleController.class);
	
	@Autowired
	ShouCangService shouCangService;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	
	@Autowired
	RedisTemplate<String, Article> redisTemplate;
	
	
	@Autowired
	RedisTemplate<String, ShouCang> redisShouCang;
	@Autowired
	ThreadPoolTaskExecutor executor;
	
	
	static StringBuffer hit= new StringBuffer();
	
	@RequestMapping("addshoucang")
	public String addshoucang(){
		
		return "/user/articles/addshoucang";
	}	
		/**
		 * 
		 * @param cang
		 * @param request
		 * @return
		 */
		@RequestMapping("/addsc")
		@ResponseBody
		public Boolean addsc(ShouCang cang,HttpServletRequest request){
			System.out.println("-----");
			//获取user
			User attribute = (User) request.getSession().getAttribute(Cms.USER);
			System.out.println("++++");
			//将user  id  放入ShouCang对象中
			cang.setUser_id(attribute.getId());
			System.out.println(cang);
			//添加收藏夹
			int i = shouCangService.addShouCang(cang);
			if(i>0){
				return true;
			}
			return false;
			
		}
		
		/**
		 * 
		 * @param request
		 * @param page
		 * @param pagesize
		 * @return
		 */
		@RequestMapping("/shoucangjia")
		public String shoucangjia(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="3")int pagesize){
			//获取user
			User attribute = (User) request.getSession().getAttribute(Cms.USER);
			//传入当前页，每页条数
			PageHelper.startPage(page, pagesize);
			//获取收藏夹数据
			List<ShouCang> findShouCang = shouCangService.findShouCang(attribute.getId());
			//放到pageinfo里
			PageInfo<ShouCang> pageindo =new PageInfo<ShouCang>(findShouCang);
			request.setAttribute("pageInfo", pageindo);
			return "/user/articles/shoucangjia";
		}

		//
		/**
		 * 
		 * @param request
		 * @param id	
		 * @param userid
		 * @return
		 */
		@RequestMapping("delshoucangjia")
		@ResponseBody
		public Boolean delshoucangjia(HttpServletRequest request,int id,int userid){
			//获取user
			User attribute = (User) request.getSession().getAttribute(Cms.USER);
			//判断是否是登录用户的收藏夹
			if(attribute.getId()!=userid){
				return false;
			}
			//删除收藏夹
			int i =shouCangService.delShouCangById(id);
			if(i>0){
				return true;
			}
			return false;
			
		}
	/**
	 * 
	 * @param request
	 * @param id
	 * @param page
	 * @return
	 */
	@RequestMapping("comments")
	public String comments(HttpServletRequest request,int id,int page) {
		PageInfo<Comment> commentPage =  articleService.getComments(id,page);
		request.setAttribute("commentPage", commentPage);
		return "comments";
	}
	
	/**
	 * pagearticle
	 */
	@RequestMapping("pagearticle")
	@ResponseBody
	public CmsMessage pagearticle(HttpServletRequest request,int id,int articleid){
		//System.out.println(id+"++++++++++++"+articleid);
		
		Article article =articleService.getArticlepage(id,articleid);
		if(article==null){
			if (id>articleid) {
				//System.out.println(1);
				return new CmsMessage(CmsError.NOT_EXIST, "已经是最后一篇了", "");
			}else{
				//System.out.println(2);
				return new CmsMessage(CmsError.NOT_EXIST, "已经是第一篇了", "");
			}
		}
		//System.out.println(3);
		/*if(article.getId())*/
		CmsMessage cmsMessage = new CmsMessage(CmsError.SUCCESS, "", article.getId());
		return cmsMessage;
		
	}
	
	/**
	 * 
	 * @param request
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="tocomplain",method=RequestMethod.GET)
	public String tocomplain(@RequestParam(defaultValue="1")int page,HttpServletRequest request){
		PageInfo<Complain> pageinfocomplain=articleService.getPageInfoComplain(page);
		request.getSession().setAttribute("pageinfocomplain", pageinfocomplain);
		return "/user/admin/complain";
	}
	
	
	
	@RequestMapping(value="complain",method=RequestMethod.GET)
	public String complain(HttpServletRequest request,int articleId){
		Article article= articleService.getArticleId(articleId);
		request.setAttribute("article", article);
		request.setAttribute("complain", new Complain());
		return "/article/complain";
	}
	
	@RequestMapping("getComplain")
	@ResponseBody
	public CmsMessage getComplain(int id){
		Complain com = articleService.getComplainId(id);
		System.out.println(com);
		if(com==null){
			return new CmsMessage(Cms.NEEDNT_UPDATE, "信息错误", null);
		}
		System.out.println(1);
		return new CmsMessage(Cms.SUCCESS, "", com);
		
	}
	
	/**
	 * 接受投诉页面提交的数据
	 * @param request
	 * @param articleId
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="complain",method=RequestMethod.POST)
	public String complain(HttpServletRequest request,
			@ModelAttribute("complain") @Valid Complain complain,
			MultipartFile file,
			BindingResult result) throws IllegalStateException, IOException {
		System.out.println(1);
		if(!StringUtils.isHttpUrl(complain.getSrcUrl())) {
			result.rejectValue("srcUrl", "", "不是合法的url地址");
		}
		System.out.println(2);
		if(result.hasErrors()) {
			Article article= articleService.getArticleId(complain.getId());
			request.setAttribute("article", article);
			return "article/complain";
		}
		System.out.println(3);
		User loginUser  =  (User)request.getSession().getAttribute(Cms.USER);
		System.out.println(4);
		String picUrl = this.processFile(file);
		complain.setPicture(picUrl);
		
		System.out.println(5);
		//加上投诉人
		if(loginUser!=null)
			complain.setUserId(loginUser.getId());
		else
			complain.setUserId(0);
		System.out.println(6);
		articleService.addComplian(complain);
		System.out.println(7);
		return "redirect:/article/detail?id="+complain.getArticleId();
				
	}
	
	
	/**
	 * 
	 * @param request
	 * @param articleId
	 * @param content
	 * @return
	 */
	@RequestMapping("postcomment")
	@ResponseBody
	public CmsMessage postcomment(HttpServletRequest request,int articleId,String content) {
		System.out.println(content);
		User loginUser  = (User)request.getSession().getAttribute(Cms.USER);
		
		if(loginUser==null) {
			return new CmsMessage(Cms.NOT_LOGIN, "您尚未登录！", null);
		}
		
		Comment comment = new Comment();
		comment.setUserId(loginUser.getId());
		comment.setContent(content);
		comment.setArticleId(articleId);
		int result = articleService.addComment(comment);
		if(result > 0)
			return new CmsMessage(Cms.SUCCESS, "成功", null);
		
		return new CmsMessage(Cms.FAILED_UPDATE_DB, "异常原因失败，请与管理员联系", null);
		
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,int id){
		
		//获取用户ip
		String remoteAddr = request.getRemoteAddr();
		
		//使用String线程池节省空间
		StringBuilder string =new StringBuilder("Hits_"+id+"_"+remoteAddr);
		
		
		//从Redis中取出文章
		Article article2 = redisTemplate.opsForValue().get(string.toString());
		
		//判断是否有文章
		if(article2==null){
			executor.createThread(new Runnable() {
				
				@Override
				public void run() {
					//使用kafka异步修改点击数
					String jsonString = JSON.toJSONString(id);
					kafkaTemplate.send("sundongliang", "dianji", jsonString);
					
					//没有文章添加文章
					//从Mysql中查出文章
					System.err.println("从MySQL中获取文章");
					Article article = articleService.getArticleId(id);
					request.setAttribute("article", article);
					//放入Redis中
					redisTemplate.opsForValue().set(string.toString(), article);
					//设置过期时间
					redisTemplate.expire(string.toString(),5 , TimeUnit.MINUTES);
				}
			}).start();
			/*Article article = redisTemplate.opsForValue().get(string.toString());
			request.setAttribute("article", article);*/
			
		}else if(article2!=null){
			System.err.println("从Redis中获取文章");
			request.setAttribute("article", article2);
		}
		try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "detail";
		
	}
	
	/**
	 * 
	 */
	@RequestMapping("getDetail")
	@ResponseBody
	public CmsMessage getDetail(int id) {
		if(id<=0) {
			
		}
		// 获取文章详情
		Article article = articleService.getArticleId(id);
		System.out.println(article);
		// 不存在
		if(article==null)
			return new CmsMessage(CmsError.NOT_EXIST, "文章不存在",null);
		//return new CmsMessage(2, "文章不存在",null);
		
		// 返回数据
		//return new CmsMessage(1,"",article); 
		return new CmsMessage(CmsError.SUCCESS,"",article); 
		
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/articles")
	public String articles(HttpServletRequest request,@RequestParam(defaultValue="1") int page) {
		
		User loginUser = (User)request.getSession().getAttribute(Cms.USER);
		
		PageInfo<Article> articlePage = articleService.listByUser(loginUser.getId(),page);
		
		request.setAttribute("articlePage", articlePage);
		
		return "/user/articles/list";
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/comment")
	public String comments() {
		
		
		return "/user/comments/list";
	}
	
	
	
	/**
	 * 个人设置
	 * @return
	 */
	@RequestMapping(value="postArticle",method=RequestMethod.GET)
	public String personal(Model m) {
		//m.addAttribute(Cms.ARTICLE, new Article());
		List<Channel> list = articleService.channelList();
		m.addAttribute(Cms.CHANNEL, list);
		return "/user/articles/add";
	}
	
	@RequestMapping(value="postArticle",method=RequestMethod.POST)
	@ResponseBody
	public boolean postArticle(HttpServletRequest request, Article article, 
			MultipartFile file
			) throws IOException {
		
		
		String picUrl;
		try {
			// 处理上传文件
			picUrl = processFile(file);
			article.setPicture(picUrl);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//当前用户是文章的作者
		User loginUser = (User)request.getSession().getAttribute(Cms.USER);
		article.setUserId(loginUser.getId());
		
		
		return articleService.add(article)>0;
		
		
		
	}
	
	
	private String processFile(MultipartFile file) {
		// 判断目标目录时间否存在
				//picRootPath + ""
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String subPath = sdf.format(new Date());
				//图片存放的路径
				File path= new File(picRootPath+"/" + subPath);
				//路径不存在则创建
				if(!path.exists())
					path.mkdirs();
				
				//计算新的文件名称
				String suffixName = FileUtils.getSuffixName(file.getOriginalFilename());
				
				//随机生成文件名
				String fileName = UUID.randomUUID().toString() + suffixName;
				//文件另存
				try {
					file.transferTo(new File(picRootPath+"/" + subPath + "/" + fileName));
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return  subPath + "/" + fileName;
	}
	@RequestMapping("/deletearticle")
	@ResponseBody
	public Object deletearticle(Integer id){
		int i =articleService.deletearticle(id);
		if(i<0){
			return false;
		}
		return true;
	}
	
	@RequestMapping("/getCategoris")
	@ResponseBody
	public Object getCategoris(Integer cid){
		List<Category> list = articleService.categoryList(cid);
		return list;
		
	}
	
	
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(Integer id,HttpServletRequest request){
		System.out.println(1111);
		Article article=articleService.getArticleId(id);
		User user = (User)request.getSession().getAttribute(Cms.USER);
		System.out.println(article.getUserId());
		System.out.println(user.getId());
		if(article.getUserId()!=user.getId()){
			request.getSession().removeAttribute(Cms.USER);
			request.setAttribute(Cms.USER,new User());
			request.setAttribute("eror", "未知错误");
			return "/user/login";
		}
		
		List<Channel> list = articleService.channelList();
		request.setAttribute(Cms.CHANNEL, list);
		request.setAttribute("article", article);
		request.setAttribute("content1",  HtmlUtils.htmlspecialchars(article.getContent()));
		return "/user/articles/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public boolean update(HttpServletRequest request, Article article, 
			MultipartFile file
			) throws IOException {
		Article articl=articleService.getArticleId(article.getId());
		User user = (User)request.getSession().getAttribute(Cms.USER);
		System.out.println(articl.getUserId());
		System.out.println(user.getId());
		if(articl.getUserId()!=user.getId()){
			
			return false;
		}
		if(file.getOriginalFilename()!=null&&file.getOriginalFilename()!=""){
		String picUrl;
		try {
			// 处理上传文件
			picUrl = processFile(file);
			article.setPicture(picUrl);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		//当前用户是文章的作者
		User loginUser = (User)request.getSession().getAttribute(Cms.USER);
		article.setUserId(loginUser.getId());
		
		
		return articleService.updateArticle(article)>0;
		
		
		
	}
	/**
	 * 批量上传文件
	 * @param request
	 * @param imgFiles
	 * @return
	 * @throws FileUploadException
	 */
	@RequestMapping("uploads.do")
	@ResponseBody
	public String uploads(HttpServletRequest request ,@RequestParam(value = "imgFile") MultipartFile  imgFiles[]) throws FileUploadException {
	
		log.info("开始上传文件啊");
	//文件保存目录路径  todo
	//String savePath = pageContext.getServletContext().getRealPath("/") + "pic/";
	
		StringBuilder sb = new StringBuilder();
	//文件保存目录URL
	String saveUrl  = request.getContextPath() + "/pic/";
	//定义允许上传的文件扩展名
	HashMap<String, String> extMap = new HashMap<String, String>();
	extMap.put("image", "gif,jpg,jpeg,png,bmp");
	extMap.put("flash", "swf,flv");
	extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
	extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

	//最大文件大小
	long maxSize = 1000000;

	//response.setContentType("text/html; charset=UTF-8");

	if(!ServletFileUpload.isMultipartContent(request)){
		log.info(getError("请选择文件。"));
		return sb.toString();
	}
	//检查目录
	File uploadDir = new File(picRootPath);

	if(!uploadDir.isDirectory()){
		log.info(getError("上传目录不存在。"));
		return sb.toString();
	}
	//检查目录写权限
	if(!uploadDir.canWrite()){
		log.info(getError("上传目录没有写权限。"));
		return sb.toString();
	}

	String dirName = request.getParameter("dir");
	if (dirName == null) {
		dirName = "image";
	}
	if(!extMap.containsKey(dirName)){
		log.info(getError("目录名不正确。"));
		return sb.toString();
	}
	//创建文件夹
	String savePath =picRootPath + "/" +  dirName + "/";
	saveUrl += dirName + "/";
	File saveDirFile = new File(savePath);
	if (!saveDirFile.exists()) {
		saveDirFile.mkdirs();
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String ymd = sdf.format(new Date());
	savePath += ymd + "/";
	saveUrl += ymd + "/";
	File dirFile = new File(savePath);
	log.info("1");
	if (!dirFile.exists()) {
		dirFile.mkdirs();
	}

	FileItemFactory factory = new DiskFileItemFactory();
	//ServletFileUpload upload = new ServletFileUpload(factory);
	//upload.setHeaderEncoding("UTF-8");
	
	//List items = upload.parseRequest(request);
	
	List<FileResult> fileList =  new ArrayList();
	
	//Iterator itr = imgFiles.iterator();
	log.info("2");
	
		
		log.info("循环");
		//FileItem item = (FileItem) itr.next();
		for (int i = 0; i < imgFiles.length; i++) {
			MultipartFile imgFile = imgFiles[i]; 
		
		String fileName = imgFile.getOriginalFilename();
		long fileSize = imgFile.getSize();
		
			//检查文件大小
			if(imgFile.getSize() > maxSize){
				log.info(getError("上传文件大小超过限制。"));
				return sb.toString();
			}
			//检查扩展名
			log.info("fileName is " + fileName);
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
				log.info(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
				return sb.toString();			}

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			try{
				log.info("savePath, newFileName :" + savePath + " -- "+ newFileName);
				File uploadedFile = new File(savePath, newFileName);
				//item.write(uploadedFile);
				imgFile.transferTo(uploadedFile);
			}catch(Exception e){
				log.info(getError("上传文件失败。"));
				return sb.toString();
			}

			//return new FileResult(0,saveUrl + newFileName);
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", saveUrl + newFileName);
			sb.append(obj.toJSONString());
		}
		return sb.toString();
	
}
	
	private String getError(String message) {
		log.info("error " + message	);
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
	
	
}







class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
		}
	}
}
 
 
 

 class SizeComparator implements Comparator {
	public SizeComparator() {
		// TODO Auto-generated constructor stub
	}

	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
				return 1;
			} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

 class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
		}
	}
}

	

