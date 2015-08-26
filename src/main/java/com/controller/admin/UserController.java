package com.controller.admin;

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
@RequestMapping("/admin/user")
public class UserController {
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/user/list");
		List<User> list=this.userService.getAll();
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void add(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<User> list=this.userService.getAll();
		String response_json = JsonUtil.object2JsonStr(response, list);
		response.getWriter().write(response_json);
	}
}
