package com.service;

import java.util.List;

import com.entity.Borrow;

public interface BorrowService {
	public List<Borrow> getAll();
	public void add(Borrow borrow);
	public void delete(String id);
}
