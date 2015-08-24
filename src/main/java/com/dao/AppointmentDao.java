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
	/*List<Appointment> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	int getNumByWelfare(Welfare welfare);
	List<Appointment> getListByWelfare(Welfare wf);
	public List<Appointment> getListByProperty(String propertyName, Object propertyValue);
	
	public List<Appointment> getPagination(int start, int count);
	public int getCount();
	//List<Appointment> getPaginationByWelfareID(String welfareID, Map<String, Object> like, Map<String, Object> and, int i, int itemsPerPage);
	List<Appointment> getEntityListByWelfareID(String welfareID, Object propertyValue, int start, int count, Map<String, String> orderProps); 
	*/
	//int getCountByWelfareID(String welfareID,Object value);
	public List<Appointment> getListByProperties(Map<String, Object> andProps, int startIndex, int itemsPerPage,
			Map<String, String> orderMap);
	public int getCountByProperty(String propKey, Object propValue);
	public List<Appointment> getListByProperty(String propKey, Object propValue);
	
}
