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
	public int getCount(){
		return welfareDao.getCount();
	}
	@Override
	public void add(Welfare welfare){
		welfareDao.add(welfare);
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
	public void update(Welfare welfare){
		welfareDao.update(welfare);
	}
	@Override
	public List<Welfare> getListByProperties(Map<String, Object> like,
			int startIndex, int itemsPerPage) {
		// TODO Auto-generated method stub
		return this.welfareDao.getListByProperties(like,
				startIndex, itemsPerPage);
	}
	@Override
	public int getCountByLikeProperties(Map<String, Object> like) {
		// TODO Auto-generated method stub
		return this.welfareDao.getCountByLikeProperties(like);
	}
	@Override
	public List<Welfare> getPagination(int i, int itemsPerPage) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getCountByProperties(Map<String, Object> like) {
		// TODO Auto-generated method stub
		return this.getCountByLikeProperties(like);
	}
	@Override
	public List<Welfare> getListByProperties(Map<String, Object> andProps) {
		// TODO Auto-generated method stub
		return this.welfareDao.getListByProperties(andProps);
	}

}
