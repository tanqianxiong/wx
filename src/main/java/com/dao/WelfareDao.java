package com.dao;

import java.util.List;

import com.entity.Welfare;
import com.common.db.BaseDao;

public interface WelfareDao extends BaseDao<Welfare>{

	List<Welfare> getAll();
	public void add(Welfare welfare);
	List<Welfare> getLikeProperty(String propertyName,Object value);
	public void delete(String id);
	public void alter(Welfare book);
	public Welfare get(String id);
}
