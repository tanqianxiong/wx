package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.EmployeeDao;
import com.dao.UserDao;
import com.entity.Employee;
import com.entity.User;
import com.common.db.HibernateDaoImp;
@Repository("employeeDao")
public class EmployeeDaoImp extends HibernateDaoImp<Employee> implements EmployeeDao{

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}

	@Override
	public Employee getByJobNumber(String jobNumber) {
		// TODO Auto-generated method stub
		return this.doGetByProperty("userNo", jobNumber);
	}

	@Override
	public Employee getByProperties(Map<String, Object> props) {
		// TODO Auto-generated method stub
		return this.doGetByProperties(props, null);
	}

	
}
