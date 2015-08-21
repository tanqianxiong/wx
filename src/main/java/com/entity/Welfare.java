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
@Table(name = "WELFARE", schema = "wx")
public class Welfare  implements Serializable{

	// Fields
	
	private String id;
	private String name;
	private String introduction;
	private String state;
	// Constructors
	private int commentNum;

	/** default constructor */
	public Welfare() {
	}

	/** minimal constructor */
	public Welfare(String id) {
		this.id = id;
	}

	/** full constructor */
	public Welfare(String name,String introduction,String state,int commentNum) {
		this.name = name;
		this.introduction=introduction;
		this.state=state;
		this.commentNum=commentNum;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Welfare_ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "Welfare_Name", nullable = true, length = 36)
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "Welfare_Introduction", nullable = true , length = 500)
	public String getIntroduction(){
		return this.introduction;
	}
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}

	@Column(name = "Welfare_State", nullable = true , length = 10)
	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
	@Column(name = "Welfare_Comment_Num", nullable = true, length = 11)
	public int getCommentNum(){
		return this.commentNum;
	}
	public void setCommentNum(int commentNum){
		this.commentNum=commentNum;
	}
}