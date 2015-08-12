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
	public void getListData(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Employee> list=new ArrayList<Employee>();
		String keyword=request.getParameter("keyword");
//		String bookType=request.getParameter("bookType");
		if(keyword!=null && !keyword.isEmpty()){
			Map<String,Object> like=new HashMap<String,Object>();
			like.put("userName", "%"+keyword+"%");
			like.put("department", "%"+keyword+"%");
			like.put("userNO", "%"+keyword+"%");
//			if(bookType!=null && !bookType.isEmpty()){
//				Map<String,Object> and=new HashMap<String,Object>();
//				and.put("type", bookType);
//				list=this.bookService.getLikeProperty(like, and);
//			}
//			else{
				list=this.employeeService.getByProperties(like);
//			}
		}
		else{
			list=this.employeeService.getAll();
		}
		map.put("success", true);
		map.put("list", list);
		JsonUtil.writeCommonJson(response, map);
	}

	public ModelAndView list(HttpServletRequest request) {
		List<Employee> list=this.employeeService.getAll();
		return null;
	}
	
	//增加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletResponse response,Employee employee) throws IOException{
		employee.setPoint(0);
//		employee.setBorrowed(0);
		this.employeeService.add(employee);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//按ID删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteById(HttpServletResponse response,String id) throws IOException{
		//String id = request.getParameter("id");
		System.out.println(id);
		this.employeeService.delete(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
	
	
	//详细显示要修改的记录
	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public void get(HttpServletResponse response,String id) throws IOException{
		System.out.println(id);
		Employee employee=this.employeeService.getById(id);
		System.out.println(employee.getUsername());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("item", employee);
		JsonUtil.writeCommonJson(response, map);
		
	}
	//更新记录
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletResponse response,Employee employee) throws IOException{
		this.employeeService.alter(employee);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		JsonUtil.writeCommonJson(response, map);
	}
	//查询借书记录
		@RequestMapping(value = "/check", method = RequestMethod.POST)
		public void check(HttpServletResponse response,String id) throws IOException{
			List<Borrow> list=this.borrowService.getAll();
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}