package com.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dao.WelfareDao;
import com.entity.Welfare;
import com.common.db.HibernateDaoImp;
@Repository("welfareDao")
public class WelfareDaoImp extends HibernateDaoImp<Welfare> implements WelfareDao{

	@Override
	public List<Welfare> getAll() {
		// TODO Auto-generated method stub
		return this.getAll(null);
	}
	@Override
	public void add(Welfare welfare){
		this.insert(welfare);
	}
	@Override
	public List<Welfare> getLikeProperty(String propertyName,Object propertyValue){
		
		return this.getListLikeProperty(propertyName,propertyValue,null);
	}
	@Override
	public void delete(String id){
		this.deleteById(id);
	}
	@Override
	public Welfare get(String id){
		return this.getById(id);
	}
	@Override
	public void alter(Welfare welfare){
		this.update(welfare);
	}
}
