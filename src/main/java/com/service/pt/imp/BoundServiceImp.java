package com.service.pt.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BookDao;
import com.dao.BoundInfoDao;
import com.entity.Book;
import com.entity.BoundInfo;
import com.service.BookService;
import com.service.pt.BoundService;

@Service("boundService")
public class BoundServiceImp implements BoundService{
	
	@Autowired
	BoundInfoDao boundInfoDao;

	@Override
	public List<BoundInfo> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(BoundInfo boundInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(BoundInfo boundInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundInfo get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoundInfo getByProperties(Map<String, Object> props) {
		// TODO Auto-generated method stub
		return this.boundInfoDao.getByProperties(props);
	}

}
