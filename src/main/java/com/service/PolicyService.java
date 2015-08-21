package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Policy;

public interface PolicyService {
	public List<Policy> getAll();
	public void add(Policy policy);
	public void delete(String id);
	public void alter(Policy policy);
	public Policy get(String id);
	List<Policy> getLikeProperty(Map<String, Object> map);
	public List<Policy> getPagination(int start, int count);
	public int getCount();
	public List<Policy> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	public List<Policy> getListByProperty(Map<String, Object> prop);
	public List<Policy> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage);
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and);
	
}
