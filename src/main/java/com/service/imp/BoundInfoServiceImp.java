package com.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BoundInfoDao;
import com.entity.BoundInfo;
import com.service.BoundInfoService;

@Service("boundInfoService")
public class BoundInfoServiceImp implements BoundInfoService{
	
	@Autowired
	BoundInfoDao boundInfoDao;
	@Override
	public List<BoundInfo> getAll() {
		// TODO Auto-generated method stub
		return boundInfoDao.getAll();
	}
	@Override
	public BoundInfo getByOpenId(String openId) {
		// TODO Auto-generated method stub
		Map<String,Object> prop=new HashMap<String,Object>();
		prop.put("openId", openId);
		return this.boundInfoDao.getByProperties(prop);
	}
	@Override
	public void add(BoundInfo boundInfo) {
		// TODO Auto-generated method stub
		this.boundInfoDao.add(boundInfo);
	}
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.boundInfoDao.delete(id);
	}
	@Override
	public void update(BoundInfo boundInfo) {
		// TODO Auto-generated method stub
		this.boundInfoDao.update(boundInfo);
	}
	@Override
	public BoundInfo get(String id) {
		// TODO Auto-generated method stub
		return this.boundInfoDao.get(id);
	}
	@Override
	public BoundInfo getByProperties(Map<String, Object> props) {
		// TODO Auto-generated method stub
		return this.boundInfoDao.getByProperties(props);
	}

}
