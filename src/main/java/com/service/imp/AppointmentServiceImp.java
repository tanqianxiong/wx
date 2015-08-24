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
	public List<Appointment> getListByProperties(Map<String, Object> andProps, int startIndex, int itemsPerPage,
			Map<String, String> orderMap) {
		// TODO Auto-generated method stub
		return this.appointmentDao.getListByProperties(andProps, startIndex, itemsPerPage,orderMap);
	}

	@Override
	public int getCountByProperty(String propKey, Object propValue) {
		// TODO Auto-generated method stub
		return this.appointmentDao.getCountByProperty(propKey,propValue);
	}

}
