package com.controller.pt;

import java.io.IOException;
import java.util.ArrayList;
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
import com.entity.User;
import com.service.BookService;
import com.service.UserService;
import com.common.util.JsonUtil;

@Controller
@RequestMapping("/pt/book")
public class PtBookController {
	@Autowired
	public BookService bookService;

	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public ModelAndView showIndexPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/book/index");
		return mv;
	}
	
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
}
