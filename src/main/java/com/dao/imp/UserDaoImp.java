package com.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dao.UserDao;
import com.entity.User;
import com.common.db.HibernateDaoImp;
@Repository("userDao")
public class UserDaoImp extends HibernateDaoImp<User> implements UserDao{

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}

	
}
