package com.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeDao;
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
	public Employee getByJobNumber(String jobNumber) {
		// TODO Auto-generated method stub
		return this.employeeDao.getByJobNumber(jobNumber);
	}
	@Override
	public Employee getByProperties(Map<String, Object> props) {
		// TODO Auto-generated method stub
		return this.employeeDao.getByProperties(props);
	}

}
