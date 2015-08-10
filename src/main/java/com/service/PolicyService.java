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
}
