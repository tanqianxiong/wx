package com.controller.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Welfare;
import com.service.WelfareService;

@Controller
@RequestMapping("/admin/welfare")
public class WelfareController {
	@Autowired
	public WelfareService welfareService;
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/welfare/list");
		
		List<Welfare> list=this.welfareService.getAll();
		System.out.println(list);
		mv.addObject("list", list);
		return mv;
	}
	//按字段查询
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView getByProperty(HttpServletRequest request) throws IOException{
		ModelAndView mv = new ModelAndView("admin/welfare/result");
		String propertyValue = request.getParameter("propertyValue");
		System.out.println(propertyValue);		
		List<Welfare> r1 = this.welfareService.getLikeProperty("name",propertyValue);
		List<Welfare> r2 = this.welfareService.getLikeProperty("id", propertyValue);
		
		mv.addObject("list", r1);
		mv.addObject("list2", r2);
		mv.addObject("propertyValue", propertyValue);
		return mv;
	}
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request,Welfare welfare){
		this.welfareService.add(welfare);
		return this.list(request);
		
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteById(HttpServletRequest request,String id){
		this.welfareService.delete(id);
		return this.list(request);
	}
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public ModelAndView get(String id){
		System.out.println(id);
		ModelAndView mv = new ModelAndView("admin/welfare/update");
		Welfare welfare=this.welfareService.get(id);
		mv.addObject("item",welfare);
		return mv;
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request,Welfare welfare){
		this.welfareService.alter(welfare);
		
		return this.list(request);
	}
}
