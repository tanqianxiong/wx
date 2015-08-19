package com.controller.pt;

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

import com.service.AppointmentService;
import com.service.BoundInfoService;
import com.service.EmployeeService;
import com.service.WelfareService;
import com.common.util.JsonUtil;
import com.entity.Appointment;
import com.entity.Book;
import com.entity.Borrow;
import com.entity.BoundInfo;
import com.entity.Employee;
import com.entity.Welfare;

@Controller
@RequestMapping("/pt/service")
public class ServiceController {
	@Autowired
	public BoundInfoService boundInfoService;
	
	@Autowired
	public AppointmentService appointmentService;
	
	@Autowired
	public EmployeeService employeeService;

	@Autowired
	public WelfareService welfareService;
	
	@RequestMapping(value = {"/welfare"}, method = RequestMethod.GET)
	public ModelAndView showBoundPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pt/service/welfare");
		return mv;
	}

	@RequestMapping(value = {"/welfare"}, method = RequestMethod.POST)
	public void getWelfareData(HttpServletResponse response) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Welfare> list=new ArrayList<Welfare>();
		Map<String,Object> prop=new HashMap<String,Object>();
		prop.put("state", "启用");
		list=this.welfareService.getListByProperty(prop);
		map.put("success", true);
		map.put("list", list);
		JsonUtil.writeCommonJson(response, map);
	}

	@RequestMapping(value = {"/handle"}, method = RequestMethod.POST)
	public void doWelfareHandle(String openId,String welfareIds,String jobNumber,String username,HttpServletResponse response) throws IOException {
		Boolean res = false;
		
		String[] welIds =  welfareIds.split(",");
		
		BoundInfo bi=this.boundInfoService.getByOpenId(openId);
		Employee em=bi.getEmployee();
		if(em.getUsername().equals(username) && em.getUserNo().equals(jobNumber) ){
			for(int i=0;i<welIds.length;i++){
				System.out.println(welIds[i]);
				Appointment ap = new Appointment();
				
				Welfare wel = this.welfareService.get(welIds[i]);
				ap.setWelfare(wel);
				ap.setEmployee(em);
				ap.setApplyTime(new Date());
				ap.setState("0");
				this.appointmentService.add(ap);
			}
			res = true;
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", res);
		JsonUtil.writeCommonJson(response, map);		
	}
	
}
