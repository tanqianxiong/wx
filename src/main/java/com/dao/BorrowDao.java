package com.dao;

import java.util.List;
import com.entity.Borrow;
import com.common.db.BaseDao;

public interface BorrowDao extends BaseDao<Borrow>{

	List<Borrow> getAll();
	public void add(Borrow borrow);
	public void delete(String id);
}
