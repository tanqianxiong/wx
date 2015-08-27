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
import com.common.util.RegCheck;
import com.entity.Borrow;
import com.entity.Employee;
import com.service.BorrowService;
import com.service.EmployeeService;
@Controller
@RequestMapping("/admin/employee")
public class EmployeeController {
	@Autowired
	public EmployeeService employeeService;
	@Autowired
	public BorrowService borrowService;
	//全查
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView showListPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("admin/employee/list");
		return mv;
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getListData(int pageIndex,int itemsPerPage,HttpServletRequest request,HttpServletResponse response) {
		int count=0;
		Map<String,Object> map=new HashMap<String,Object>();
		List<Employee> list=new ArrayList<Employee>();
		Map<String,String> orderMap = new HashMap<String,String>();
		String keyword=request.getParameter("keyword");
		String orderProp =request.getParameter("orderProp");
		if(orderProp!=null && !orderProp.isEmpty()){
			if(orderProp.equals("point")){
				orderMap.put(orderProp, "desc");
			}
			else{
				orderMap.put(orderProp, "asc");
			}
		}
		else {
			orderMap.put("userNo", "asc");
		}
		if(keyword!=null && !keyword.isEmpty()){
			Map<String,Object> like=new HashMap<String,Object>();
			like.put("username", "%"+keyword+"%");
			like.put("position", "%"+keyword+"%");
			like.put("department", "%"+keyword+"%");
			like.put("userNo", "%"+keyword+"%");
			list=this.employeeService.getListByProperties(like,null,pageIndex*itemsPerPage,itemsPerPage,orderMap);
			count=this.employeeService.getCountByProperties(like);
			
		}
		else{
			//list=this.employeeService.getAll();
			list=this.employeeService.getListByProperties(pageIndex*itemsPerPage,itemsPerPage,orderMap);
			count=this.employeeService.getCount();
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
			e.printStackTrace();
		}
	}

	public ModelAndView list(HttpServletRequest request) {
		List<Employee> list=this.employeeService.getAll();
		return null;
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,Employee employee){
		employee.setPoint(0);
//		employee.setBorrowed(0);
		Boolean success=false;
		if (success=RegCheck.CheckChinese(employee.getUsername())){
			if(success=RegCheck.CheckNum(employee.getUserNo())){
				this.employeeService.add(employee);
			}
		}		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", success);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id) {
		//String id = request.getParameter("id");
		System.out.println(id);
		this.employeeService.delete(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) {
		System.out.println(id);
		Employee employee=this.employeeService.getById(id);
		System.out.println(employee.getUsername());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", employee);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,Employee employee){
		Boolean success=false;
		if (success=RegCheck.CheckChinese(employee.getUsername())){
			if(success=RegCheck.CheckNum(employee.getUserNo())){
				this.employeeService.update(employee);
			}
		}			
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", success);
		try {
			JsonUtil.setContentType(response);
			String response_json = JsonUtil.object2JsonStr(map);
			response.getWriter().write(response_json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//查询借书记录
		@RequestMapping(value = "/check", method = RequestMethod.POST)
		public void check(HttpServletResponse response,String userId) {
			Employee employee=this.employeeService.getById(userId);
			
			List<Borrow> list=this.borrowService.getListByEmployee(employee);//.getByUserID("employee",userId);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("success", true);
			map.put("employee", employee);
			map.put("list", list);
			try {
				JsonUtil.setContentType(response);
				String response_json = JsonUtil.object2JsonStr(map);
				response.getWriter().write(response_json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@RequestMapping(value = {"/check"}, method = RequestMethod.GET)
		public ModelAndView showIndividualPage(HttpServletRequest request) {
			ModelAndView mv = new ModelAndView("admin/employee/detail");
			return mv;
		}
		
	
}