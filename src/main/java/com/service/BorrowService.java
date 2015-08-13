package com.service;

import java.util.List;

import com.entity.Borrow;
import com.entity.Employee;

public interface BorrowService {
	public List<Borrow> getAll();
	public void add(Borrow borrow);
	public void delete(String id);
	public List<Borrow> getListByEmployee(Employee employee);
}
