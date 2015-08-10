package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Employee;

public interface EmployeeService {
	public List<Employee> getAll();
	public Employee getByJobNumber(String jobNumber);
	public Employee getByProperties(Map<String,Object> props);
}
