package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.EmployeeDao;
import com.entity.Employee;
import com.common.db.HibernateDaoImp;
@Repository("employeeDao")
public class EmployeeDaoImp extends HibernateDaoImp<Employee> implements EmployeeDao{

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}

	@Override
	public void add(Employee employee) {
		// TODO Auto-generated method stub
		this.doInsert(employee);
	}

	

	@Override
	public List<Employee> getListByLikeProperties(Map<String,Object> props){
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(props);
	}

	
	

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.doDeleteById(id);
	}

	@Override
	public void alter(Employee employee) {
		// TODO Auto-generated method stub
		this.doUpdate(employee);
	}
	@Override
	public Employee getById(String id){
		return this.doGetById(id);
	}

}





