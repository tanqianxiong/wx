package com.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.WelfareDao;
import com.entity.Book;
import com.entity.Welfare;
import com.service.WelfareService;

@Service("welfareService")
public class WelfareServiceImp implements WelfareService{
	
	@Autowired
	WelfareDao welfareDao;
	@Override
	public List<Welfare> getAll() {
		// TODO Auto-generated method stub
		return welfareDao.getAll();
	}
	@Override
	public List<Welfare> getPagination(int start, int count) {
		// TODO Auto-generated method stub
		return welfareDao.getPagination(start,count);
	}
	@Override
	public int getCount(){
		return welfareDao.getCount();
	}
	@Override
	public void add(Welfare welfare){
		welfareDao.add(welfare);
	}
	
	@Override
	public List<Welfare> getLikeProperty(Map<String,Object> map){
		return welfareDao.getLikeProperties(map);
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
	@Override
	public List<Welfare> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
	    return this.welfareDao.getLikeProperty(map, map2);	
	}
	@Override
	public List<Welfare> getListByProperty(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		return this.welfareDao.getListByProperty(prop);
	}
	@Override
	public List<Welfare> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage) {
		// TODO Auto-generated method stub
		return this.welfareDao.getPaginationByLikeProperty(like, and, i, itemsPerPage);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.welfareDao.getCountByLikeProperty(like, and);
	}

}
