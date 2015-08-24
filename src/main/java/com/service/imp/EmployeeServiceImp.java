package com.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeDao;
//import com.entity.Book;
import com.entity.Employee;
import com.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImp implements EmployeeService{
	
	@Autowired
	EmployeeDao employeeDao;
	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeDao.getAll();
	}
	
	@Override
	public void add(Employee employee){
		employeeDao.add(employee);
		
	}
	@Override
	public void delete(String id){
		employeeDao.delete(id);
	}
	@Override
	public Employee getById(String id){
		return employeeDao.getById(id);
	}

	@Override
	public void update(Employee employee) {
		// TODO Auto-generated method stub
		this.employeeDao.update(employee);
	}

	@Override
	public List<Employee> getListByProperties(Map<String, Object> like,
			int startIndex, int itemsPerPage, Map<String, String> orderMap) {
		// TODO Auto-generated method stub
		return this.employeeDao.getListByProperties(like,
				startIndex, itemsPerPage, orderMap);
	}

	@Override
	public int getCountByProperties(Map<String, Object> like) {
		// TODO Auto-generated method stub
		return this.employeeDao.getCountByProperties(like);
	}

	@Override
	public List<Employee> getListByProperties(int startIndex, int itemsPerpage,Map<String, String> orderMap) {
		// TODO Auto-generated method stub
		return this.employeeDao.getListByProperties(startIndex, itemsPerpage, orderMap);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.employeeDao.getCount();
	}


}





