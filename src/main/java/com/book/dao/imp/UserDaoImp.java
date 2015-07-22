package com.book.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.book.dao.UserDao;
import com.book.entity.User;
import com.common.db.HibernateDaoImp;

@Repository("userDao")
public class UserDaoImp extends HibernateDaoImp<User> implements UserDao{

	public List<User> getAll() {		
		return this.getAll(null);
	}


}
