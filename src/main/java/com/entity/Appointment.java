package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "APPOINTMENT", schema = "wx")
public class Appointment  implements Serializable{

	// Fields
	
	private String id;
	private Employee employee;
	private Welfare welfare;
	private Date applyTime;
	private Date checkTime;
	private String state;
	// Constructors

	/** default constructor */
	public Appointment() {
	}

	/** minimal constructor */
	public Appointment(String id) {
		this.id = id;
	}

	/** full constructor */
	public Appointment(Employee employee, Welfare welfare, Date applyTime,Date checkTime,String state) {
		this.employee = employee;
		this.welfare = welfare;
		this.applyTime = applyTime;
		this.checkTime = checkTime;
		this.state = state;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Appointment_ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WELFARE_ID", nullable = false)
	public Welfare getWelfare() {
		return this.welfare;
	}

	public void setWelfare(Welfare welfare) {
		this.welfare = welfare;
	}

	@Column(name = "APPLY_TIME", nullable = false)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	@Column(name = "CHECK_TIME", nullable = true)
	public Date getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@Column(name="STATE", nullable = false, length = 10)
	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
}
	
