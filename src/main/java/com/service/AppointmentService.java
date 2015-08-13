package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Appointment;
import com.entity.Welfare;

public interface AppointmentService {
	public List<Appointment> getAll();
	public void add(Appointment appointment);
	public void delete(String id);
	public void update(Appointment appointment);
	public Appointment get(String id);
	List<Appointment> getLikeProperty(Map<String, Object> map);
	public List<Appointment> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	public int getNumByWelfare(Welfare welfare);
	public List<Appointment> getListByWelfare(Welfare wf);
}
