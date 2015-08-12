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
import com.service.UserService;
import com.common.util.JsonUtil;

@Controller
@RequestMapping("/pt/book")
public class PtBookController {
	@Autowired
	public UserService userService;

	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public ModelAndView showIndexPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/book/index");
		return mv;
	}
	
	@RequestMapping(value = "/bookSearch", method = RequestMethod.POST)
	public void doSearch(String bookName,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		List<Book> list=new ArrayList<Book>();
		Book book=new Book();
		book.setBookName("西游记");
		book.setAuthor("吴承恩");
		list.add(book);
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
