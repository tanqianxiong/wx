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
}
