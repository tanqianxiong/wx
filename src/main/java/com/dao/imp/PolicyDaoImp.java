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
	public void add(Policy policy){
		this.doInsert(policy);
	}
	
	@Override
	public int getCount(){
		return this.doGetCount();
	}
	
	
	@Override
	public List<Policy> getListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int startIndex,
			int itemsPerpage) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(likeProps, andProps, startIndex, itemsPerpage, null);
	}
	@Override
	public int getCountByProperties(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.doGetCountByProperties(like, and);
	}
	@Override
	public List<Policy> getListByProperties(int startIndex, int itemsPerpage) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(null, null, startIndex, itemsPerpage,null);
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
