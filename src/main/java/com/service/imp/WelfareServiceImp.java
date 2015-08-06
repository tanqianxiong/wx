package com.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.WelfareDao;
import com.entity.Welfare;
import com.service.WelfareService;

@Service("WelfareService")
public class WelfareServiceImp implements WelfareService{
	
	@Autowired
	WelfareDao welfareDao;
	@Override
	public List<Welfare> getAll() {
		// TODO Auto-generated method stub
		return welfareDao.getAll();
	}
	@Override
	public void add(Welfare welfare){
		welfareDao.add(welfare);
	}
	
	@Override
	public List<Welfare> getLikeProperty(String propertyName,Object propertyValue){
		return welfareDao.getLikeProperty(propertyName,propertyValue);
	}
	@Override
	public void delete(String id){
		welfareDao.delete(id);
	}
	@Override
	public Welfare get(String id){
		return welfareDao.get(id);
	}
	@Override
	public void alter(Welfare welfare){
		welfareDao.alter(welfare);
	}

}
