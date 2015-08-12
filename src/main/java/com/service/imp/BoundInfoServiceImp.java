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

}
