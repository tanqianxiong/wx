package com.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.BorrowDao;
import com.dao.UserDao;
import com.entity.Book;
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
		return this.doGetListByProperty("employee", employee);
	}


	@Override
	public Borrow get(Employee employee, Book book) {
		// TODO Auto-generated method stub
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("employee", employee);
		param.put("book", book);
		Map<String,String> orderProps=new HashMap<String,String>();
		orderProps.put("returnTime", "asc");
		List<Borrow> list=this.doGetListByProperties(null, param, 0, 0, orderProps);
		if(list==null || list.isEmpty()){
			return null;
		}
		else{
			return list.get(0);
		}
	}

	@Override
	public void update(Borrow br) {
		// TODO Auto-generated method stub
		this.doUpdate(br);
	}


	
}
