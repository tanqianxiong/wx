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
import com.common.util.RegCheck;
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
		Map<String,Object> map = new HashMap<String,Object>();
		List<Welfare> list=new ArrayList<Welfare>();
		Map<String,Object> andProps=new HashMap<String,Object>();
		andProps.put("state", "启用");
		list=this.welfareService.getListByProperties(andProps);
		map.put("success", true);
		map.put("list", list);
		JsonUtil.writeCommonJson(response, map);
	}
	//点击预约办理
	@RequestMapping(value = {"/handle"}, method = RequestMethod.POST)
	public void doWelfareHandle(String openId,String welfareIds,String jobNumber,String username,HttpServletResponse response) throws IOException {
		Boolean res = false;		
		if(RegCheck.CheckLetterAndNum(openId)){
			String[] welIds =  welfareIds.split(",");		
			BoundInfo bi=this.boundInfoService.getByOpenId(openId);
			Employee em=bi.getEmployee();
			//String user_id = em.getId();
			if(em.getUsername().equals(username) && em.getUserNo().equals(jobNumber )){
				for(int i=0;i<welIds.length;i++){
					Appointment ap = new Appointment();			
					Welfare wel = this.welfareService.get(welIds[i]);
					List<Appointment> li = this.appointmentService.getListByProperty("employee",em);
					if(li!=null && !li.isEmpty()){
						int j=0;
						for(int k=0;k<li.size();k++){
							Appointment app = li.get(k);
							String id=app.getWelfare().getId();
							if(id.equals(welIds[i]) && !app.getWelfare().getState().equals("通过")){
								j=1;
								break;
							}
						}
						if(j==0){
							ap.setWelfare(wel);
							ap.setEmployee(em);
							ap.setApplyTime(new Date());
							ap.setState("申请中");
							this.appointmentService.add(ap);	
						}
					}else{
						ap.setWelfare(wel);
						ap.setEmployee(em);
						ap.setApplyTime(new Date());
						ap.setState("申请中");
						this.appointmentService.add(ap);					
					}
				}
				res = true;
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", res);
		JsonUtil.writeCommonJson(response, map);		
	}
	
	//获取用户所办理的福利列表
	@RequestMapping(value = {"/hasHandled"}, method = RequestMethod.POST)
	public void doHasHandled(String openId,HttpServletResponse response) throws IOException {
		Boolean res = false;
		Map<String,Object> map=new HashMap<String,Object>();		
		if(openId!=null && RegCheck.CheckLetterAndNum(openId)){
			Employee employee=this.boundInfoService.getByOpenId(openId).getEmployee();
			List<Appointment> alist=this.appointmentService.getListByProperty("employee", employee);
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			for(Appointment am:alist){
				Map<String,Object> elem=new HashMap<String,Object>();
				elem.put("welfare", am.getWelfare());
				elem.put("state", am.getState());
				list.add(elem);
			}
			map.put("list", list);
			res=true;
		}
		map.put("success", res);
		JsonUtil.writeCommonJson(response, map);
	}
	
}
