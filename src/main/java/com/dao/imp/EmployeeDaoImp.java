package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.EmployeeDao;
//import com.entity.Book;
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
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.doDeleteById(id);
	}

	@Override
	public Employee getById(String id){
		return this.doGetById(id);
	}

	@Override
	public void update(Employee employee) {
		// TODO Auto-generated method stub
		this.doUpdate(employee);
		
	}

	@Override
	public List<Employee> getListByProperties(Map<String, Object> like,
			int startIndex, int itemsPerpage, Map<String, String> orderMap) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(like, null, startIndex, itemsPerpage, orderMap);
	}

	@Override
	public int getCountByProperties(Map<String, Object> like) {
		// TODO Auto-generated method stub
		return this.doGetCountByProperties(like, null);
	}

	@Override
	public List<Employee> getListByProperties(int startIndex, int itemsPerpage,
			Map<String, String> orderMap) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(null, null, startIndex, itemsPerpage, orderMap);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.doGetCount();
	}

	@Override
	public List<Employee> getListByProperties(Map<String, Object> andProps) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(null,andProps);
	}

}
