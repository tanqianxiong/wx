package com.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import com.entity.Welfare;
import com.service.WelfareService;

@Controller
@RequestMapping("/admin/welfare")
public class WelfareController {
	@Autowired
	public WelfareService welfareService;
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/welfare/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Welfare> list=new ArrayList<Welfare>();
		String keyword=request.getParameter("keyword");
		String welfareType=request.getParameter("welfareType");
		if(keyword!=null && !keyword.isEmpty()){
			Map<String,Object> like=new HashMap<String,Object>();
			like.put("name", "%"+keyword+"%");
			if(welfareType!=null && !welfareType.isEmpty()){
				Map<String,Object> and=new HashMap<String,Object>();
				and.put("type", welfareType);
				list=this.welfareService.getLikeProperty(like, and);
			}
			else{
				list=this.welfareService.getLikeProperty(like);
			}
		}
		else{
			list=this.welfareService.getAll();
		}
		map.put("success", true);
		map.put("list", list);
		JsonUtil.writeCommonJson(response, map);
	}

	public ModelAndView list(HttpServletRequest request) {
		List<Welfare> list=this.welfareService.getAll();
		return null;
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,Welfare welfare) throws IOException{
		welfare.setState("停用");
		this.welfareService.add(welfare);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id) throws IOException{
		//String id = request.getParameter("id");
		System.out.println(id);
		this.welfareService.delete(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) throws IOException{
		System.out.println(id);
		Welfare welfare=this.welfareService.get(id);
		System.out.println(welfare.getName());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", welfare);
		JsonUtil.writeCommonJson(response, map);
		
	}
	
	//显示操作状态：启用或者停用
	@RequestMapping(value = "/use",method = RequestMethod.POST)
	public void use(HttpServletResponse response,String id) throws IOException{
		System.out.println(id);
		Welfare welfare=this.welfareService.get(id);
		if(welfare.getState().equals("停用")){
			welfare.setState("启用");
		}
		else{
			welfare.setState("停用");
		}
		this.welfareService.alter(welfare);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,Welfare welfare) throws IOException{
		this.welfareService.alter(welfare);
		welfare.setState("停用");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
}
