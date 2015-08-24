package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Policy;
import com.common.db.BaseDao;

public interface PolicyDao extends BaseDao<Policy>{

	
	public void add(Policy policy);
	public void delete(String id);
	public void alter(Policy policy);
	public Policy get(String id);
	
	public List<Policy> getListByProperties(int start, int count);

	public List<Policy> getListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int startIndex,
			int itemsPerpage);
	public int getCount();
	
	int getCountByProperties(Map<String, Object> like, Map<String, Object> and);
	
}
