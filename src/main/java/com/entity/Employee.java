package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "EMPLOYEE", schema = "wx")
public class Employee  implements Serializable{

	// Fields
	
	private String id;
	private String userNo;
	private String username;
	private String gender;
	private String department;
	private String position;
	private int point;
	
	// Constructors

	/** default constructor */
	public Employee() {
	}

	/** minimal constructor */
	public Employee(String id) {
		this.id = id;
	}

	/** full constructor */
	public Employee(String userNo,String username, String gender, String department, 
	int point,String position) {
		this.userNo = userNo;
		this.username = username;
		this.gender = gender;
		this.department = department;
		this.point = point;
		this.position = position;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Employee_ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "Employee_NO", unique = true, nullable = false, length = 10)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "Employee_NAME", nullable = false, length = 30)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "Employee_SEX", length = 4)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name = "Employee_DEPARTMENT", length = 20)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	@Column(name = "Employee_POINT")
	public int getPoint() {
		return this.point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	@Column(name = "Employee_POSITION", length = 36)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}