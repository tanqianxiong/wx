package com.dao;

import java.util.List;
import java.util.Map;


import com.entity.Employee;
import com.common.db.BaseDao;

public interface EmployeeDao extends BaseDao<Employee>{

	public List<Employee> getAll();
	public void add(Employee employee);
	List<Employee> getListByLikeProperties(Map<String,Object> props);
	public void delete(String id);
	public void alter(Employee employee);
	public Employee getById(String id);
}
