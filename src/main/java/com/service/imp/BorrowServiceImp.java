package com.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BorrowDao;
import com.entity.Book;
import com.entity.Borrow;
import com.entity.Employee;
import com.service.BorrowService;

@Service("borrowService")
public class BorrowServiceImp implements BorrowService{
	
	@Autowired
	BorrowDao borrowDao;
	@Override
	public List<Borrow> getAll() {
		// TODO Auto-generated method stub
		return borrowDao.getAll();
	}
	@Override
	public void add(Borrow borrow) {
		// TODO Auto-generated method stub
		this.borrowDao.add(borrow);
	}
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.borrowDao.delete(id);
	}
	@Override
	public List<Borrow> getListByEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return this.borrowDao.getListByEmployee(employee);
	}
	@Override
	public Borrow get(Employee employee, Book book) {
		// TODO Auto-generated method stub
		return this.borrowDao.get(employee,book);
	}
	@Override
	public void update(Borrow br) {
		// TODO Auto-generated method stub
		this.borrowDao.update(br);
	}
}
