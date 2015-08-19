package com.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AppointmentDao;
import com.entity.Appointment;
import com.entity.Welfare;
import com.service.AppointmentService;

@Service("appointmentService")
public class AppointmentServiceImp implements AppointmentService {

	@Autowired
	AppointmentDao appointmentDao;
	@Override
	public List<Appointment> getAll() {
		// TODO Auto-generated method stub
		return appointmentDao.getAll();
	}

	@Override
	public void add(Appointment appointment) {
		// TODO Auto-generated method stub
		appointmentDao.add(appointment);
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		appointmentDao.delete(id);
	}

	@Override
	public void update(Appointment appointment) {
		// TODO Auto-generated method stub
		appointmentDao.update(appointment);

	}

	@Override
	public Appointment get(String id) {
		// TODO Auto-generated method stub
		return appointmentDao.get(id);
	}

	@Override
	public List<Appointment> getLikeProperty(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointment> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		return this.appointmentDao.getLikeProperty(map, map2);
	}

	@Override
	public int getNumByWelfare(Welfare welfare) {
		return this.appointmentDao.getNumByWelfare(welfare);
	}

	@Override
	public List<Appointment> getListByWelfare(Welfare wf) {
		// TODO Auto-generated method stub
		return this.appointmentDao.getListByWelfare(wf);
	}
	
	@Override
	public List<Appointment> getListByProperty(String propertyName, Object propertyValue){
		// TODO Auto-generated method stub
		return this.appointmentDao.getListByProperty(propertyName, propertyValue);
	}

}
