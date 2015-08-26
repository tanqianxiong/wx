package com.controller.admin;

import java.io.IOException;
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
	public void doLogin(String username,String password,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		if(username.equals("admin") && password.equals("123")){
			request.getSession().setAttribute("user", "admin");
			map.put("success", true);
		}
		else{
			map.put("success", false);
		}
		String response_json = JsonUtil.object2JsonStr(response, map);
		response.getWriter().write(response_json);
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
