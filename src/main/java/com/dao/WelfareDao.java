package com.dao;

import java.util.List;
import java.util.Map;



import com.entity.Welfare;
import com.common.db.BaseDao;

public interface WelfareDao extends BaseDao<Welfare>{

	List<Welfare> getAll();
	public void add(Welfare welfare);
	List<Welfare> getLikeProperties(Map<String,Object> map);
	public void delete(String id);
	public void alter(Welfare book);
	public Welfare get(String id);
	List<Welfare> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	List<Welfare> getListByProperty(Map<String, Object> prop);
	
}
