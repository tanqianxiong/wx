package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Policy;

public interface PolicyService {

	public void add(Policy policy);
	public void delete(String id);
	public void alter(Policy policy);
	public Policy get(String id);
	
	public List<Policy> getListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int startIndex,
			int itemsPerpage);
	
	public List<Policy> getListByProperties(int i,int itemsPerPage);
	public int getCount();
	public int getCountByProperties(Map<String, Object> like, Map<String, Object> and);
	
}
