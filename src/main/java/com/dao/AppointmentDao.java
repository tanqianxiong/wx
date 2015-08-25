package com.dao;

import java.util.List;
import java.util.Map;

import com.common.db.BaseDao;
import com.entity.Appointment;
import com.entity.Book;
import com.entity.Welfare;

public interface AppointmentDao extends BaseDao<Appointment> {
	public List<Appointment> getAll();
	public void add(Appointment appointment);
	public void delete(String id);
	public void update(Appointment appointment);
	public Appointment get(String id);
	public List<Appointment> getListByProperties(Map<String, Object> andProps, int startIndex, int itemsPerPage,
			Map<String, String> orderMap);
	public int getCountByProperty(String propKey, Object propValue);
	public List<Appointment> getListByProperty(String propKey, Object propValue);
	
}
