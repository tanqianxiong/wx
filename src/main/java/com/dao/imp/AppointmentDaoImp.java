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
	public List<Appointment> getListByProperties(Map<String, Object> andProps, int startIndex, int itemsPerpage,
			Map<String, String> orderMap) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(null, andProps, startIndex, itemsPerpage, orderMap);
	}

	@Override
	public int getCountByProperty(String propKey, Object propValue) {
		// TODO Auto-generated method stub
		return this.doGetCountByProperty(propKey, propValue);
	}


}
