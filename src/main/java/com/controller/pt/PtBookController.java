package com.controller.pt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Book;
import com.entity.Borrow;
import com.entity.BoundInfo;
import com.entity.Employee;
import com.entity.User;
import com.service.BookService;
import com.service.BorrowService;
import com.service.BoundInfoService;
import com.service.UserService;
import com.common.util.JsonUtil;

@Controller
@RequestMapping("/pt/book")
public class PtBookController {
	@Autowired
	public BookService bookService;

	@Autowired
	public BorrowService borrowService;
	@Autowired
	public BoundInfoService boundInfoService;
	/*
	 * 跳转到个人借阅记录页面
	 */
	@RequestMapping(value = {"/individual"}, method = RequestMethod.GET)
	public ModelAndView showIndividualPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/book/individual");
		return mv;
	}
	/*
	 * 跳转到图书馆的首页
	 */
	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public ModelAndView showIndexPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/book/index");
		return mv;
	}
	/*
	 * 借阅图书
	 */
	@RequestMapping(value = {"/borrow"}, method = RequestMethod.POST)
	public void borrow(String openId,String bookId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Boolean res=false;
		BoundInfo bi=this.boundInfoService.getByOpenId(openId);
		Employee el=bi.getEmployee();
		Book book=this.bookService.get(bookId);
		if(this.borrowService.get(el, book)==null){		
			Borrow br=new Borrow();
			br.setBook(book);
			br.setEmployee(el);
			br.setBorrowTime(new Date());
			//br.setReturnTime(new Date());
			this.borrowService.add(br);
			res=true;			
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", res);
		JsonUtil.writeCommonJson(response, map);
	}
	/*
	 * 图书搜索
	 */
	@RequestMapping(value = "/bookSearch", method = RequestMethod.POST)
	public void doSearch(HttpServletRequest request,HttpServletResponse response) {
		List<Book> list=new ArrayList<Book>();
		String keyword=request.getParameter("keyword");
		if(keyword!=null && !keyword.isEmpty()){
			Map<String,Object> like=new HashMap<String,Object>();
			like.put("bookName", "%"+keyword+"%");
			like.put("author", "%"+keyword+"%");
			like.put("publisher", "%"+keyword+"%");
			list=this.bookService.getLikeProperty(like);
		}
		else{
			list=this.bookService.getAll();
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("list", list);
		try {
			JsonUtil.writeCommonJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return mv;
	}
	/*
	 * 个人借阅记录
	 */
	@RequestMapping(value = "/individual", method = RequestMethod.POST)
	public void doGetBorrowRecord(String openId, HttpServletRequest request,HttpServletResponse response) {
		Employee employee=this.boundInfoService.getByOpenId(openId).getEmployee();
		List<Borrow> list =this.borrowService.getListByEmployee(employee);
		List<Book> borrowing=new ArrayList<Book>();
		List<Book> borrowed=new ArrayList<Book>();
		for(Borrow br:list){
			if(br.getReturnTime()!=null){
				borrowed.add(br.getBook());
			}
			else{
				borrowing.add(br.getBook());
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("score", list.get(0).getEmployee().getPoint());
		map.put("borrowing", borrowing);
		map.put("borrowed", borrowed);
		try {
			JsonUtil.writeCommonJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return mv;
	}
	/*
	 * 归还图书
	 */
	@RequestMapping(value = "/escheat", method = RequestMethod.POST)
	public void doReturnBook(String openId, String bookId, HttpServletRequest request,HttpServletResponse response) {
		Employee employee=this.boundInfoService.getByOpenId(openId).getEmployee();
		Book book=this.bookService.get(bookId);
		Borrow br=this.borrowService.get(employee,book);
		br.setReturnTime(new Date());
		this.borrowService.update(br);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		try {
			JsonUtil.writeCommonJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return mv;
	}
	
	/*
	 * 跳转到图书分类页面
	 */
	@RequestMapping(value = {"/category"}, method = RequestMethod.GET)
	public ModelAndView showCategoryPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/book/category");
		return mv;
	}
	/*
	 * 图书分类的数据响应
	 */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public void getCategoryList(String selectedCategory,HttpServletRequest request,HttpServletResponse response) {
		List<Book> list=new ArrayList<Book>();
		if(selectedCategory!=null && !selectedCategory.isEmpty()){
			Map<String,Object> prop=new HashMap<String,Object>();
			prop.put("type", selectedCategory);
			list=this.bookService.getListByProperty(prop);
		}
		else{
			list=this.bookService.getAll();
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("list", list);
		try {
			JsonUtil.writeCommonJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return mv;
	}
	/*
	 * 跳转到图书详情页
	 */
	@RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
	public ModelAndView showBookDetailPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/book/detail");
		return mv;
	}
}
