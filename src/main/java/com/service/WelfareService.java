package com.service;

import java.util.List;

import com.entity.Welfare;

public interface WelfareService {
	public List<Welfare> getAll();
	public void add(Welfare welfare);
	public List<Welfare> getLikeProperty(String propertyName,Object value);
	public void delete(String id);
	public void alter(Welfare welfare);
	public Welfare get(String id);
}
