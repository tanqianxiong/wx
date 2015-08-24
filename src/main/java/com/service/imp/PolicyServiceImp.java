package com.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PolicyDao;
import com.entity.Policy;
import com.service.PolicyService;

@Service("policyService")
public class PolicyServiceImp implements PolicyService{
	
	@Autowired
	PolicyDao policyDao;
	
	@Override
	public int getCount(){
		return policyDao.getCount();
	}
	@Override
	public void add(Policy policy){
		policyDao.add(policy);
	}
	
	
	@Override
	public void delete(String id){
		policyDao.delete(id);
	}
	
	
	@Override
	public List<Policy> getListByProperties(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage) {
		// TODO Auto-generated method stub
		return this.policyDao.getListByProperties(like, and, i, itemsPerPage);
	}
	@Override
	public int getCountByProperties(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.policyDao.getCountByProperties(like, and);
	}
	@Override
	public Policy get(String id){
		return policyDao.get(id);
	}
	@Override
	public List<Policy> getListByProperties(int start, int count){
		return this.policyDao.getListByProperties(start,count);
	}
	@Override
	public void alter(Policy policy){
		policyDao.alter(policy);
	}
	
}
