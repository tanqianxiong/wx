package com.service;

import java.util.List;
import java.util.Map;






//import com.entity.Book;
import com.entity.Employee;

public interface EmployeeService {
	public List<Employee> getAll();
	public void add(Employee employee);
	public void delete(String id);
	public void alter(Employee employee);
	public List<Employee> getByProperties(Map<String,Object> props);
	public Employee getById(String id);
	public List<Employee> getByLikeProperties(Map<String, Object> like);
	
	public List<Employee> getPagination(int start, int count);
	public int getCount();
	List<Employee> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i, int itemsPerPage);
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and);
}
