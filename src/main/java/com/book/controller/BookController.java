package com.book.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.book.entity.User;
import com.book.service.UserService;

@Controller
@RequestMapping("/book")
public class BookController {
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		List<User> list=this.userService.getAll();
		mv.addObject("list", list);
		return mv;
	}
}
