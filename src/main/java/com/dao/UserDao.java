package com.dao;

import java.util.List;

import com.entity.User;
import com.common.db.BaseDao;

public interface UserDao extends BaseDao<User>{

	List<User> getAll();
	
}
