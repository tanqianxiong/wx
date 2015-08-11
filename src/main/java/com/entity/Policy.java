package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "POLICY", schema = "wx")
public class Policy  implements Serializable{

	// Fields
	
	private String id;
	private String name;
	private String content;
	private Date createTime;
		
	// Constructors

	/** default constructor */
	public Policy() {
	}

	/** minimal constructor */
	public Policy(String id) {
		this.id = id;
	}

	/** full constructor */
	public Policy(String name,String content,Date createTime) {
		this.name = name;
		this.content=content;
		this.createTime=createTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Policy_ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "Policy_Name", nullable = true, length = 100)
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "Policy_Content", nullable = true , length = 1024)
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	
	@Column(name = "Create_Time", nullable = true)
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

}