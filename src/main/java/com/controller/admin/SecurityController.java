package com.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.entity.User;
import com.service.UserService;

@Controller
@RequestMapping("/admin")
public class SecurityController {
	

	@Autowired
	public UserService userService;
	
	
	/**
	 * 管理员用户登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/login", "/index" }, method = RequestMethod.GET)
	public ModelAndView showLoginPage() {
		ModelAndView mv = new ModelAndView();
	//	mv.addObject(new LoginForm());
		mv.setViewName("/admin/login");
		return mv;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(String username,String password,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
	//	mv.addObject(new LoginForm());
		request.getSession().setAttribute("user", "admin");
		List<User> list=this.userService.getAll();
		mv.addObject("list", list);
		mv.setViewName("/admin/user/list");
		return mv;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView doLogout(String username,String password,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
	//	mv.addObject(new LoginForm());
		request.getSession().removeAttribute("user");
		mv.setViewName("/admin/login");
		return mv;
	}
}
