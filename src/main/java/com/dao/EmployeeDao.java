package com.dao;

import java.util.List;
import java.util.Map;











//import com.entity.Book;
import com.entity.Employee;
import com.common.db.BaseDao;

public interface EmployeeDao extends BaseDao<Employee>{

	public List<Employee> getAll();
	public void add(Employee employee);
	public void delete(String id);
	public Employee getById(String id);
	public void update(Employee employee);
	public List<Employee> getListByProperties(Map<String, Object> like,Map<String, Object> and,
			int startIndex, int itemsPerPage, Map<String, String> orderMap);
	public int getCountByProperties(Map<String, Object> like);
	public List<Employee> getListByProperties(int startIndex, int itemsPerpage,
			Map<String, String> orderMap);
	public int getCount();
	public List<Employee> getListByProperties(Map<String, Object> andProps);
	
}
