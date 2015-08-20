package com.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.common.util.JsonUtil;
import com.entity.Book;
import com.service.BookService;

@Controller
@RequestMapping("/admin/book")
public class BookController {
	@Autowired
	public BookService bookService;
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/book/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(int pageIndex,int itemsPerPage,HttpServletRequest request,HttpServletResponse response) throws IOException {
		int count=0;
		Map<String,Object> map=new HashMap<String,Object>();
		List<Book> list=new ArrayList<Book>();
		String keyword=request.getParameter("keyword");
		String bookType=request.getParameter("bookType");
		if(keyword!=null && !keyword.isEmpty()){
			Map<String,Object> like=new HashMap<String,Object>();
			like.put("bookName", "%"+keyword+"%");
			like.put("author", "%"+keyword+"%");
			like.put("publisher", "%"+keyword+"%");
			if(bookType!=null && !bookType.isEmpty()){
				Map<String,Object> and=new HashMap<String,Object>();
				and.put("type", bookType);
				list=this.bookService.getLikeProperty(like, and);
			}
			else{
				list=this.bookService.getLikeProperty(like);
			}
		}
		else{
			list=this.bookService.getPagination(pageIndex*itemsPerPage,itemsPerPage);
			count=this.bookService.getCount();
		}
		map.put("success", true);
		map.put("list", list);
		map.put("count",count);
		JsonUtil.writeCommonJson(response, map);
	}

	public ModelAndView list(HttpServletRequest request) {
		List<Book> list=this.bookService.getAll();
		return null;
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,Book book) throws IOException{
		book.setPoints((float) 0);
		book.setBorrowed(0);
		this.bookService.add(book);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id) throws IOException{
		//String id = request.getParameter("id");
		System.out.println(id);
		this.bookService.delete(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) throws IOException{
		System.out.println(id);
		Book book=this.bookService.get(id);
		System.out.println(book.getBookName());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", book);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,Book book) throws IOException{
		this.bookService.alter(book);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
}
