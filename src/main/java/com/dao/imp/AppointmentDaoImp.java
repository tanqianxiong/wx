package com.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.db.HibernateDaoImp;
import com.dao.AppointmentDao;
import com.entity.Appointment;
import com.entity.Welfare;

@Repository("appointmentDao")
public class AppointmentDaoImp extends HibernateDaoImp<Appointment> implements AppointmentDao {

	@Override
	public List<Appointment> getAll() {
		return this.doGetAll(null);
	}

	@Override
	public void add(Appointment appointment) {
		this.doInsert(appointment);

	}

	@Override
	public void delete(String id) {
		this.doDeleteById(id);
	}

	@Override
	public void update(Appointment appointment) {
		this.doUpdate(appointment);
	}

	@Override
	public Appointment get(String id) {
		// TODO Auto-generated method stub
		return this.doGetById(id);
	}

	@Override
	public List<Appointment> getListByLikeProperties(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(map);
	}

	@Override
	public List<Appointment> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(map, map2);
	}

	@Override
	public int getNumByWelfare(Welfare welfare) {
		// TODO Auto-generated method stub
		return this.doGetCountByProperty("welfare", welfare);
	}

	@Override
	public List<Appointment> getListByWelfare(Welfare wf) {
		// TODO Auto-generated method stub
		return this.doGetListByProperty("welfare", wf, null);
	}
}
