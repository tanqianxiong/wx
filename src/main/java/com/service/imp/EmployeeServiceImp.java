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
	public List<Employee> getByProperties(Map<String,Object> props) {
		// TODO Auto-generated method stub
		return this.employeeDao.getByProperties(props);
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
	public void alter(Employee employee){
		employeeDao.alter(employee);
		
		
	}
	@Override
	public Employee getById(String id){
		return employeeDao.getById(id);
	}

	@Override
	public List<Employee> getByLikeProperties(Map<String, Object> like) {
		// TODO Auto-generated method stub
		return this.employeeDao.getListByLikeProperties(like);
	}
	
	@Override
	public List<Employee> getPagination(int start, int count) {
		// TODO Auto-generated method stub
		return employeeDao.getPagination(start,count);
	}
	@Override
	public int getCount(){
		return employeeDao.getCount();
	}
	@Override
	public List<Employee> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage) {
		// TODO Auto-generated method stub
		return this.employeeDao.getPaginationByLikeProperty(like, and, i, itemsPerPage);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.employeeDao.getCountByLikeProperty(like, and);
	}

}





