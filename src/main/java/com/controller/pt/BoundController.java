package com.controller.pt;

import java.io.IOException;
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

import com.service.BoundInfoService;
import com.service.EmployeeService;
import com.common.util.JsonUtil;
import com.common.util.RegCheck;
import com.controller.admin.WelfareController;
import com.entity.BoundInfo;
import com.entity.Employee;

@Controller
@RequestMapping("/pt")
public class BoundController {
	@Autowired
	public BoundInfoService boundInfoService;
	@Autowired
	public EmployeeService employeeService;
	Logger logger  =  Logger.getLogger(BoundController.class);

	/*@RequestMapping(value = {"/checkWeChat"}, method = RequestMethod.GET)
	public void checkWeChat(HttpServletRequest request,HttpServletResponse response) throws IOException {
		WeChatUtil.check(request, response);
	}*/
	
	/*@RequestMapping(value = {"/checkWeChat"}, method = RequestMethod.GET)
	public void checkWeChat(String code, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String openId=WeChatUtil.httpGetOpenId(code);
		String basePath = request.getContextPath();
		response.sendRedirect(basePath + "/pt/book/list.do?openId="+openId);
	}*/
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
	public void doLogin(String openId,String name,String jobNumber,HttpServletRequest request,HttpServletResponse response) {
		boolean res=false;
		Map<String,Object> eProps=new HashMap<String,Object>();
		eProps.put("username", name);
		eProps.put("userNo", jobNumber);
		List<Employee> epList=this.employeeService.getListByProperties(eProps);
		if(epList!=null && epList.size()>0 && openId!=null && RegCheck.CheckLetterAndNum(openId)){
			//添加绑定信息
			BoundInfo boundInfo=new BoundInfo(epList.get(0),openId);
			this.boundInfoService.add(boundInfo);
			//request.getSession().setAttribute("user", "pt");
			res=true;
		}
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		map.put("success", res);
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

	@RequestMapping(value = "/cancelBind", method = RequestMethod.GET)
	public void doCancelBind(String openId,HttpServletRequest request,HttpServletResponse response) {
		boolean res=false;
		if(openId!=null && RegCheck.CheckLetterAndNum(openId)){
			//删除绑定信息
			this.boundInfoService.delete(this.boundInfoService.getByOpenId(openId).getId());
			res=true;
		}
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		map.put("success", res);
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
