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
	public List<Policy> getAll() {
		// TODO Auto-generated method stub
		return policyDao.getAll();
	}
	@Override
	public void add(Policy policy){
		policyDao.add(policy);
	}
	
	@Override
	public List<Policy> getLikeProperty(Map<String,Object> map){
		return policyDao.getLikeProperties(map);
	}
	@Override
	public void delete(String id){
		policyDao.delete(id);
	}
	@Override
	public Policy get(String id){
		return policyDao.get(id);
	}
	@Override
	public void alter(Policy policy){
		policyDao.alter(policy);
	}

}
