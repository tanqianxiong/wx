package com.book.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.book.dao.UserDao;
import com.book.entity.User;
import com.book.service.UserService;

@Repository("userService")
public class UserServiceImp implements UserService{

	
	@Autowired
	public UserDao userDao;
	
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userDao.getAll();
	}

}
