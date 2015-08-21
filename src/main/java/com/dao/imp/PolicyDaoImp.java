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
	public List<Policy> getPagination(int start, int count) {
		// TODO Auto-generated method stub
		return this.doGetListByPage(null,start,count,null);
	}
	@Override
	public int getCount(){
		return this.doGetCount();
	}
	@Override
	public List<Policy> getLikeProperties(Map<String,Object> map){
		
		return this.doGetListByLikeProperties(map);
	}
	@Override
	public List<Policy> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(map, map2);
	}
	@Override
	public List<Policy> getListByProperty(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(prop, null);
	}
	@Override
	public List<Policy> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(like, and, i, itemsPerPage);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.doGetCountByLikeProperty(like, and);
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
