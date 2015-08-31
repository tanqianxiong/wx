package com.controller.admin;

import java.io.IOException;
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
	Logger logger  =  Logger.getLogger(AppointmentController.class);
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/appointment/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(int pageIndex,int itemsPerPage,HttpServletRequest request,HttpServletResponse response)  {
	 
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> res=new HashMap<String,Object>();
		List<Welfare> welfareList=this.welfareService.getAll();//获取welfare表中的数据
		int count=0;
		for(int i=0;i<welfareList.size();i++){
			Map<String,Object> r=new HashMap<String,Object>();
			r.put("name", welfareList.get(i).getName());
			r.put("welfareId", welfareList.get(i).getId());
			int num=0;
			num=this.appointmentService.getCountByProperty("welfare",welfareList.get(i));
			count+=num;
			r.put("number", num);
			list.add(r);
		}	
		
		res.put("success", true);
		res.put("list", list);
		res.put("count", count);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(res);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
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
	public void getDetailInfo(int pageIndex,int itemsPerPage,String welfareId,HttpServletResponse response,HttpServletRequest request){
		int num=0;		
		Welfare wf=this.welfareService.get(welfareId);
		Map<String,Object> andProps=new HashMap<String,Object>();
		Map<String,String> orderMap = new HashMap<String,String>();
		andProps.put("welfare", wf);
		String orderProp =request.getParameter("orderProp");
		if(orderProp!=null && !orderProp.isEmpty()){
			if(orderProp.equals("applyTime")){
				orderMap.put(orderProp, "desc");
			}
			else{
				orderMap.put(orderProp, "asc");
			}
		}
		else {
			orderMap.put("applyTime", "desc");
		}
		List<Appointment> list=this.appointmentService.getListByProperties(andProps, pageIndex*itemsPerPage,itemsPerPage,orderMap);//分页计算
		List<Appointment> list2=this.appointmentService.getListByProperty("welfare", wf);//不分页，按照福利ID查找所得数据
		int count2=this.appointmentService.getCountByProperty("welfare",wf);//分页计算
		int count=0;//未处理请求数
		if(list2!=null&&!list2.isEmpty()){
			for(int i=0;i<list2.size();i++){
				if(list2.get(i).getState().equals("申请中"))
					count++;
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("list",list);
		map.put("count", count);
		map.put("count2", count2);
		//加载福利名称到detail页面
		map.put("welfareName", wf.getName());
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
	
	//显示操作状态：通过或退回
	@RequestMapping(value = "/agree",method = RequestMethod.POST)
	public void agree(HttpServletResponse response,String id) {
		Appointment appointment=this.appointmentService.get(id);
		appointment.setState("通过");
		//加上审批时间
		appointment.setCheckTime(new Date());
		this.appointmentService.update(appointment);
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

		
	//显示操作状态：通过或退回
	@RequestMapping(value = "/disAgree",method = RequestMethod.POST)
	public void disAgree(HttpServletResponse response,String id){
		Appointment appointment=this.appointmentService.get(id);
		appointment.setState("退回");
		//加上审批时间
		appointment.setCheckTime(new Date());
		this.appointmentService.update(appointment);
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
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,Appointment appointment){
		this.appointmentService.add(appointment);
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
	public void deleteById(HttpServletResponse response,String id) {
		this.appointmentService.delete(id);
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
	public void get(HttpServletResponse response,String id){
		Appointment appointment=this.appointmentService.get(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", appointment);
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
	public void update(HttpServletResponse response,Appointment appointment){
		this.appointmentService.update(appointment);
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
