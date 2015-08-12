package com.service;

import java.util.List;
import java.util.Map;



import com.entity.Employee;

public interface EmployeeService {
	public List<Employee> getAll();
	public void add(Employee employee);
	public void delete(String id);
	public void alter(Employee employee);
	public List<Employee> getByProperties(Map<String,Object> props);
	public Employee getById(String id);
}
