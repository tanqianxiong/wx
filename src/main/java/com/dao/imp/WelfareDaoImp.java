package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.WelfareDao;
import com.entity.Book;
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
	public List<Welfare> getPagination(int start, int count) {
		// TODO Auto-generated method stub
		return this.doGetListByPage(null,start,count,null);
	}
	@Override
	public int getCount(){
		return this.doGetCount();
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
	@Override
	public List<Welfare> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(map, map2);
	}
	@Override
	public List<Welfare> getListByProperty(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(prop, null);
	}
	@Override
	public List<Welfare> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage) {
		return this.doGetListByLikeProperties(like, and, i, itemsPerPage);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.doGetCountByLikeProperty(like, and);
	}
}
