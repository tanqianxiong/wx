package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.BoundInfoDao;
import com.entity.BoundInfo;
import com.common.db.HibernateDaoImp;
@Repository("boundInfoDao")
public class BoundInfoDaoImp extends HibernateDaoImp<BoundInfo> implements BoundInfoDao{

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
		return this.doGetByProperties(props, null);
	}

}
