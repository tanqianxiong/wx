package com.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dao.BorrowDao;
import com.dao.UserDao;
import com.entity.Borrow;
import com.entity.Employee;
import com.entity.User;
import com.common.db.HibernateDaoImp;
@Repository("borrowDao")
public class BorrowDaoImp extends HibernateDaoImp<Borrow> implements BorrowDao{

	@Override
	public List<Borrow> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}

	@Override
	public void add(Borrow borrow) {
		// TODO Auto-generated method stub
		this.doInsert(borrow);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.doDeleteById(id);
	}

	@Override
	public List<Borrow> getListByEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return this.doGetListByProperty("employee", employee, null);
	}


	
}
