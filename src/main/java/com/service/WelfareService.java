package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Book;
import com.entity.Welfare;

public interface WelfareService {
	public List<Welfare> getAll();
	public List<Welfare> getPagination(int start, int count);
	public int getCount();
	public void add(Welfare welfare);
	public void delete(String id);
	public void alter(Welfare welfare);
	public Welfare get(String id);
	List<Welfare> getLikeProperty(Map<String, Object> map);
	public List<Welfare> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	public List<Welfare> getListByProperty(Map<String, Object> prop);
	public List<Welfare> getPaginationByLikeProperty(Map<String, Object> like,
			Map<String, Object> and, int i, int itemsPerPage);
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and);
}
