package com.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Book;
import com.service.BookService;

@Controller
@RequestMapping("/admin/book")
public class BookController {
	@Autowired
	public BookService bookService;
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/book/list");
		
		List<Book> list=this.bookService.getAll();
		System.out.println(list);
		mv.addObject("list", list);
		return mv;
	}
	//按字段查询
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView getByProperty(HttpServletRequest request) throws IOException{
		ModelAndView mv = new ModelAndView("admin/book/result");
		String propertyValue = request.getParameter("propertyValue");
		propertyValue="老";
		System.out.println(propertyValue);		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", "%"+propertyValue+"%");
		map.put("author", "%"+propertyValue+"%");
		map.put("publisher", "%"+propertyValue+"%");
		List<Book> list=this.bookService.getLikeProperty(map);
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("type", "文学类");
		List<Book> list2=this.bookService.getLikeProperty(map,map2);
		mv.addObject("list", list);
		mv.addObject("propertyValue", propertyValue);
		return mv;
	}
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request,Book book){
		book.setPoints((float) 0);
		book.setOutNum(0);
		this.bookService.add(book);
		return this.list(request);
		
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteById(HttpServletRequest request,String id){
		//String id = request.getParameter("id");
		System.out.println(id);
		this.bookService.delete(id);
		return this.list(request);
	}
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public ModelAndView modify(String id){
		System.out.println(id);
		ModelAndView mv = new ModelAndView("admin/book/update");
		Book book=this.bookService.get(id);
		System.out.println(book.getName());
		
		mv.addObject("item",book);
		return mv;
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request,Book book){
		
		this.bookService.alter(book);
		
		return this.list(request);
	}
}
