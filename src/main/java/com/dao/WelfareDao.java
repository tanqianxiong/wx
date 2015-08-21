package com.dao;

import java.util.List;
import java.util.Map;





import com.entity.Book;
import com.entity.Welfare;
import com.common.db.BaseDao;

public interface WelfareDao extends BaseDao<Welfare>{

	List<Welfare> getAll();
	public List<Welfare> getPagination(int start, int count);
	public int getCount();
	public void add(Welfare welfare);
	List<Welfare> getLikeProperties(Map<String,Object> map);
	public void delete(String id);
	public void alter(Welfare book);
	public Welfare get(String id);
	List<Welfare> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	List<Welfare> getListByProperty(Map<String, Object> prop);
	List<Welfare> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i, int itemsPerPage);
	int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and);
	
}
