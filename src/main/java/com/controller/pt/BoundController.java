package com.controller.pt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.service.EmployeeService;
import com.service.pt.BoundService;
import com.common.util.JsonUtil;
import com.entity.BoundInfo;
import com.entity.Employee;

@Controller
@RequestMapping("/pt")
public class BoundController {
	@Autowired
	public BoundService boundService;
	@Autowired
	public EmployeeService employeeService;
	
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
	public void doLogin(String oppenId,String name,String jobNumber,HttpServletRequest request,HttpServletResponse response) {
		//request.getSession().setAttribute("user", "pt");
		boolean res=false;
		Map<String,Object> eProps=new HashMap<String,Object>();
		eProps.put("username", name);
		eProps.put("userNo", jobNumber);
		Employee ep=this.employeeService.getByProperties(eProps);
		if(ep!=null){
			//添加绑定信息
			BoundInfo boundInfo=new BoundInfo(ep,oppenId);
			this.boundService.add(boundInfo);
			request.getSession().setAttribute("user", "pt");
			res=true;
		}
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		map.put("success", res);
		try {
			JsonUtil.writeCommonJson(response, map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
