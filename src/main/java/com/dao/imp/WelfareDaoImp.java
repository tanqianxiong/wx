package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.WelfareDao;
import com.entity.Welfare;
import com.common.db.HibernateDaoImp;
@Repository("welfareDao")
public class WelfareDaoImp extends HibernateDaoImp<Welfare> implements WelfareDao{

	@Override
	public List<Welfare> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}
	@Override
	public void add(Welfare welfare){
		this.doInsert(welfare);
	}
	@Override
	public List<Welfare> getLikeProperties(Map<String,Object> map){
		
		return this.doGetListByLikeProperties(map);
	}
	@Override
	public void delete(String id){
		this.doDeleteById(id);
	}
	@Override
	public Welfare get(String id){
		return this.doGetById(id);
	}
	@Override
	public void alter(Welfare welfare){
		this.doUpdate(welfare);
	}
}
