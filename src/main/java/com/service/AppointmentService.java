package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Appointment;
import com.entity.Book;
import com.entity.Employee;
import com.entity.Welfare;

public interface AppointmentService {
	public List<Appointment> getAll();
	public void add(Appointment appointment);
	public void delete(String id);
	public void update(Appointment appointment);
	public Appointment get(String id);
	/*List<Appointment> getLikeProperty(Map<String, Object> map);
	public List<Appointment> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	public int getNumByWelfare(Welfare welfare);
	public List<Appointment> getListByWelfare(Welfare wf);
	public List<Appointment> getListByProperty(String propertyName, Object propertyValue);
	
	public List<Appointment> getPagination(int start, int count);
	public int getCount();
	public 	List<Appointment> getEntityListByWelfareID(String welfareID, Object propertyValue, int start, int count, Map<String, String> orderProps); 

	public int getCountByWelfareID(String welfareID,Object value);*/
	public List<Appointment> getListByProperties(Map<String, Object> andProps, int startIndex, int itemsPerPage,
			Map<String, String> orderMap);
	public int getCountByProperty(String propKey, Object propValue);
	public List<Appointment> getListByProperty(String propKey, Object propValue);
}
