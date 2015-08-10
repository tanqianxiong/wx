package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Employee;
import com.common.db.BaseDao;

public interface EmployeeDao extends BaseDao<Employee>{

	public List<Employee> getAll();
	public Employee getByJobNumber(String jobNumber);
	public Employee getByProperties(Map<String,Object> props);
}
