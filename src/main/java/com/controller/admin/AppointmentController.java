package com.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.entity.Appointment;
import com.entity.Welfare;
import com.service.AppointmentService;
import com.service.WelfareService;

@Controller
@RequestMapping("/admin/appointment")
public class AppointmentController {
	@Autowired
	public AppointmentService appointmentService;
	@Autowired
	public WelfareService welfareService;
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/appointment/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(int pageIndex,int itemsPerPage,HttpServletRequest request,HttpServletResponse response) throws IOException {
	 
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> res=new HashMap<String,Object>();
		List<Welfare> welfareList=this.welfareService.getAll();//获取welfare表中的数据
		int count=0;
		for(int i=0;i<welfareList.size();i++){
			Map<String,Object> r=new HashMap<String,Object>();
			r.put("name", welfareList.get(i).getName());
			r.put("welfareId", welfareList.get(i).getId());
			int num=0;
			num=this.appointmentService.getNumByWelfare(welfareList.get(i));
			count+=num;
			r.put("number", num);
			list.add(r);
		}	
		
		res.put("success", true);
		res.put("list", list);
		res.put("count", count);
		JsonUtil.writeCommonJson(response, res);
	}

	public ModelAndView list(HttpServletRequest request) {
		List<Appointment> list=this.appointmentService.getAll();
		return null;
	}
	
	//详情
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(String welfareId,HttpServletRequest request) throws IOException{
		ModelAndView mv = new ModelAndView("admin/appointment/detail");
		request.setAttribute("welfareId", welfareId);
		//System.out.println("welfareID"+welfareId);
		return mv;
	}
	
	//详情
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public void getDetailInfo(int pageIndex,int itemsPerPage,String welfareId,HttpServletResponse response) throws IOException{
		int num=0;		
		Welfare wf=this.welfareService.get(welfareId);		
		List<Appointment> list=this.appointmentService.getEntityListByWelfareID("welfare", wf, pageIndex*itemsPerPage,itemsPerPage, null);
		num=this.appointmentService.getCountByWelfareID("welfare", wf);
		int count=0;//未处理请求数
		for(int i=0;i<num;i++){
			if(list.get(i).getState().equals("申请中"))
				count++;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("list",list);
		map.put("count", count);
		//加载福利名称到detail页面
		map.put("welfareName", wf.getName());
		JsonUtil.writeCommonJson(response, map);
	}
	
	//显示操作状态：通过或退回
	@RequestMapping(value = "/agree",method = RequestMethod.POST)
	public void agree(HttpServletResponse response,String id) throws IOException{
		Appointment appointment=this.appointmentService.get(id);
		appointment.setState("通过");
		//加上审批时间
		appointment.setCheckTime(new Date());
		this.appointmentService.update(appointment);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);		
	}

		
	//显示操作状态：通过或退回
	@RequestMapping(value = "/disAgree",method = RequestMethod.POST)
	public void disAgree(HttpServletResponse response,String id) throws IOException{
		Appointment appointment=this.appointmentService.get(id);
		appointment.setState("退回");
		//加上审批时间
		appointment.setCheckTime(new Date());
		this.appointmentService.update(appointment);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);		
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,Appointment appointment) throws IOException{
		this.appointmentService.add(appointment);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id) throws IOException{
		this.appointmentService.delete(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
		
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) throws IOException{
		Appointment appointment=this.appointmentService.get(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", appointment);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,Appointment appointment) throws IOException{
		this.appointmentService.update(appointment);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
}
