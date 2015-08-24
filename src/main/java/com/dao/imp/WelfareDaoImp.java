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
	public void delete(String id){
		this.doDeleteById(id);
	}
	@Override
	public Welfare get(String id){
		return this.doGetById(id);
	}
	@Override
	public void update(Welfare welfare) {
		// TODO Auto-generated method stub
		this.doUpdate(welfare);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.doGetCount();
	}

	@Override
	public void add(Welfare welfare) {
		// TODO Auto-generated method stub
		this.doInsert(welfare);
	}
	@Override
	public List<Welfare> getListByProperties(Map<String, Object> like,
			int startIndex, int itemsPerpage) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(like, null, startIndex, itemsPerpage);
	}
	@Override
	public int getCountByLikeProperties(Map<String, Object> like) {
		// TODO Auto-generated method stub
		return this.doGetCountByProperties(like, null);
	}
	
	
}
