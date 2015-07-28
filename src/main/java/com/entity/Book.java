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
@Table(name = "BOOK", schema = "wx")
public class Book  implements Serializable{

	// Fields
	
	private String id;
	private String name;
	private String author;
	private String publisher;
	private Date publishTime;
	// Constructors

	/** default constructor */
	public Book() {
	}

	/** minimal constructor */
	public Book(String id) {
		this.id = id;
	}

	/** full constructor */
	public Book(String name, String author, String publisher,Date publishTime) {
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.publishTime=publishTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AUTHOR", nullable = false, length = 50)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "PUBLISHER", nullable = false, length = 50)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "PUBLISH_TIME", nullable = false)
	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}