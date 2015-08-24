package com.service;

import java.util.List;
import java.util.Map;














//import com.entity.Book;
import com.entity.Employee;

public interface EmployeeService {
	public List<Employee> getAll();
	public void add(Employee employee);
	public void delete(String id);
	public void update(Employee employee);
	public Employee getById(String id);
	public List<Employee> getListByProperties(Map<String, Object> like, int startIndex,
			int itemsPerPage, Map<String, String> orderMap);
	public int getCountByProperties(Map<String, Object> like);
	public List<Employee> getListByProperties(int startIndex, int itemsPerpage, Map<String, String> orderMap);
	public int getCount();
	public List<Employee> getListByProperties(Map<String, Object> andProps);
	
}
