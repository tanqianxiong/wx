package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Policy;
import com.common.db.BaseDao;

public interface PolicyDao extends BaseDao<Policy>{

	List<Policy> getAll();
	public void add(Policy policy);
	List<Policy> getLikeProperties(Map<String,Object> map);
	public void delete(String id);
	public void alter(Policy policy);
	public Policy get(String id);
	public List<Policy> getPagination(int start, int count);
	public int getCount();
	List<Policy> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i, int itemsPerPage);
	int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and);
	List<Policy> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	List<Policy> getListByProperty(Map<String, Object> prop);
}
