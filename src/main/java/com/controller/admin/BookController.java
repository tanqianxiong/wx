package com.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.common.util.JsonUtil;
import com.common.util.RegCheck;
import com.entity.Book;
import com.service.BookService;

@Controller
@RequestMapping("/admin/book")
public class BookController {
	@Autowired
	public BookService bookService;
	
	Logger logger  =  Logger.getLogger(BookController.class);
	
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/book/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(int pageIndex,int itemsPerPage,HttpServletRequest request,HttpServletResponse response){
		int count=0;
		Boolean success=false;
		Map<String,Object> map=new HashMap<String,Object>();
		String keyword=request.getParameter("keyword");
		String orderProp =request.getParameter("orderProp");
		if(RegCheck.CheckKeyword(keyword)&&RegCheck.CheckEnglish(orderProp)){			
			Map<String,String> orderMap = new HashMap<String,String>();
			List<Book> list=new ArrayList<Book>();			
			String bookType=request.getParameter("bookType");			
			if(orderProp!=null && !orderProp.isEmpty()){
				if(orderProp.equals("publishTime")||orderProp.equals("points")){
					orderMap.put(orderProp, "desc");
				}
				else
					orderMap.put(orderProp, "asc");
			}
			else {
				orderMap.put("bookInputTime", "desc");
			}		
			if(keyword!=null && !keyword.isEmpty()){
				Map<String,Object> like=new HashMap<String,Object>();
				like.put("bookName", "%"+keyword+"%");
				like.put("author", "%"+keyword+"%");
				like.put("publisher", "%"+keyword+"%");
				if(bookType!=null && !bookType.isEmpty()){
					Map<String,Object> and=new HashMap<String,Object>();
					and.put("type", bookType);
					list=this.bookService.getListByProperties(like, and,pageIndex*itemsPerPage,itemsPerPage,orderMap);
					count=this.bookService.getCountByProperties(like, and);
				}
				else{
					list=this.bookService.getListByProperties(like, null,pageIndex*itemsPerPage,itemsPerPage,orderMap);
					count=this.bookService.getCountByProperties(like, null);
				}
			}
			else{
				list=this.bookService.getListByProperties(pageIndex*itemsPerPage,itemsPerPage,orderMap);
				count=this.bookService.getCount();
			}
			success=true;
			map.put("list", list);
			map.put("count",count);
		}
		map.put("success", success);				
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,String id,String ISBN,String bookName,String author,String publisher,int publishTime,
					String type,int amount,String brief){
		Boolean success=false;
		if(RegCheck.CheckNum(ISBN)){
			Book book=new Book(id);
			book.setISBN(ISBN);
			book.setBookName(bookName);
			book.setAuthor(author);
			book.setPublisher(publisher);
			book.setPublishTime(publishTime);
			book.setType(type);
			book.setAmount(amount);
			book.setBrief(brief);
			book.setPoints((float) 0);
			book.setBorrowed(0);
			book.setCommentNum(0);
			book.setBookState("新书");
			book.setBookInputTime(new Date());
			this.bookService.add(book);
			success=true;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", success);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id){
		Boolean success=false;
		if(RegCheck.CheckLetterAndNum(id)){
				this.bookService.delete(id);
				success=true;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", success);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
	}
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		Boolean success=false;
		if(RegCheck.CheckLetterAndNum(id)){
			Book book=this.bookService.get(id);
			map.put("item", book);
			success=true;
		}			
		map.put("success", success);		
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,String id,String ISBN,String bookName,String author,String publisher,int publishTime,
			String type,int amount,String brief,float points,int borrowed,int commentNum,String bookState){
		Boolean success=false;
		if(success=RegCheck.CheckNum(ISBN)&&RegCheck.CheckLetterAndNum(id)){
			Book book=new Book(id);
			book.setISBN(ISBN);
			book.setBookName(bookName);
			book.setAuthor(author);
			book.setPublisher(publisher);
			book.setPublishTime(publishTime);
			book.setType(type);
			book.setAmount(amount);
			book.setBrief(brief);
			book.setPoints(points);
			book.setBorrowed(borrowed);
			book.setCommentNum(commentNum);
			book.setBookState(bookState);
			this.bookService.update(book);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", success);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
	}
}
