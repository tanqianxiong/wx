package com.dao;

import java.util.List;
import java.util.Map;

import com.common.db.BaseDao;
import com.entity.Appointment;
import com.entity.Welfare;

public interface AppointmentDao extends BaseDao<Appointment> {
	List<Appointment> getAll();
	public void add(Appointment appointment);
	List<Appointment> getListByLikeProperties(Map<String,Object> map);
	public void delete(String id);
	public void update(Appointment appointment);
	public Appointment get(String id);
	List<Appointment> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	int getNumByWelfare(Welfare welfare);
	List<Appointment> getListByWelfare(Welfare wf);
	public List<Appointment> getListByProperty(String propertyName, Object propertyValue);
}
