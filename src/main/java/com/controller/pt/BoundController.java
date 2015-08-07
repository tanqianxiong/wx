package com.controller.pt;

import java.io.IOException;
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

import com.entity.User;
import com.service.UserService;
import com.common.util.JsonUtil;

@Controller
@RequestMapping("/pt")
public class BoundController {
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public ModelAndView showIndexPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/index");
		return mv;
	}

	@RequestMapping(value = {"/binding"}, method = RequestMethod.GET)
	public ModelAndView showBoundPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/binding");
		return mv;
	}

	@RequestMapping(value = {"/tobind"}, method = RequestMethod.GET)
	public ModelAndView showTobindPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/tobind");
		return mv;
	}
	
	@RequestMapping(value = "/binding", method = RequestMethod.POST)
	public void doLogin(String username,String jobnumber,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		request.getSession().setAttribute("user", "pt");
		mv.setViewName("/pt/success");
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		map.put("success", true);
		try {
			JsonUtil.writeCommonJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return mv;
	}
}
