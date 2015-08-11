package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.PolicyDao;
import com.entity.Policy;
import com.common.db.HibernateDaoImp;
@Repository("policyDao")
public class PolicyDaoImp extends HibernateDaoImp<Policy> implements PolicyDao{

	@Override
	public List<Policy> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}
	@Override
	public void add(Policy policy){
		this.doInsert(policy);
	}
	@Override
	public List<Policy> getLikeProperties(Map<String,Object> map){
		
		return this.doGetListByLikeProperties(map);
	}
	@Override
	public void delete(String id){
		this.doDeleteById(id);
	}
	@Override
	public Policy get(String id){
		return this.doGetById(id);
	}
	@Override
	public void alter(Policy policy){
		this.doUpdate(policy);
	}
}
