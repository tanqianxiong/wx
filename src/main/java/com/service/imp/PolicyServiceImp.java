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
	public int getCount(){
		return policyDao.getCount();
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
	public List<Policy> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return this.policyDao.getLikeProperty(map, map2);
	}
	@Override
	public List<Policy> getListByProperty(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		return this.policyDao.getListByProperty(prop);
	}
	@Override
	public List<Policy> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage) {
		// TODO Auto-generated method stub
		return this.policyDao.getPaginationByLikeProperty(like, and, i, itemsPerPage);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.policyDao.getCountByLikeProperty(like, and);
	}
	@Override
	public Policy get(String id){
		return policyDao.get(id);
	}
	@Override
	public void alter(Policy policy){
		policyDao.alter(policy);
	}
	@Override
	public List<Policy> getPagination(int start, int count) {
		// TODO Auto-generated method stub
		return policyDao.getPagination(start,count);
	}
}
