package com.controller.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.common.util.JsonUtil;
import com.entity.Policy;
import com.service.PolicyService;

@Controller
@RequestMapping("/admin/policy")
public class PolicyController {
	@Autowired
	public PolicyService policyService;
	Logger logger  =  Logger.getLogger(PolicyController.class);
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/policy/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(int pageIndex,int itemsPerPage,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String,Object>();
		int count=0;
		List<Policy> list=new ArrayList<Policy>();
		String keyword=request.getParameter("keyword");
	
		if(keyword!=null && !keyword.isEmpty()){
			Map<String,Object> like=new HashMap<String,Object>();
			like.put("name", "%"+keyword+"%");
			like.put("content", "%"+keyword+"%");
		
			
				list=this.policyService.getListByProperties(like, null,pageIndex*itemsPerPage,itemsPerPage);
				count=this.policyService.getCountByProperties(like, null);
			
		}
		else{
			list=this.policyService.getListByProperties(pageIndex*itemsPerPage,itemsPerPage);
			count=this.policyService.getCount();
		}
		map.put("success", true);
		map.put("list", list);
		map.put("count",count);
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

	public ModelAndView list(HttpServletRequest request) {
		//List<Policy> list=this.policyService.getAll();
		return null;
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request,HttpServletResponse response) {
		Policy policy=new Policy();
		String name=request.getParameter("name");
		String content=request.getParameter("content");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date createTime=new Date();
		policy.setName(name);
		policy.setContent(content);
		policy.setCreateTime(createTime);
		this.policyService.add(policy);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
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
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id){
		//String id = request.getParameter("id");
		System.out.println(id);
		this.policyService.delete(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
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
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) {
		System.out.println(id);
		Policy policy=this.policyService.get(id);
		//System.out.println(book.getBookName());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", policy);
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
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,String id,String name,String content) {
		Policy policy = new Policy(id);
		policy.setName(name);
		policy.setContent(content);
		Date createTime=new Date();
		policy.setCreateTime(createTime);
		this.policyService.alter(policy);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
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
