package com.controller.pt;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.entity.User;
import com.service.UserService;
import com.common.util.JsonUtil;

@Controller
@RequestMapping("/pt")
public class BoundController {
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = {"/bound","/index"}, method = RequestMethod.GET)
	public ModelAndView showBoundPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/bound");
		return mv;
	}

	@RequestMapping(value = "/bound", method = RequestMethod.POST)
	public ModelAndView doLogin(String workID,String name,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		request.getSession().setAttribute("user", "pt");
		mv.setViewName("/pt/success");
		return mv;
	}
}
