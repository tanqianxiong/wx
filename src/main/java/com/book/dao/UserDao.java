package com.book.dao;

import java.util.List;

import com.book.entity.User;
import com.common.db.BaseDao;

public interface UserDao extends BaseDao<User>{

	List<User> getAll();
	
}
