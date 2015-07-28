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
@Table(name = "BORROW", schema = "wx")
public class Borrow  implements Serializable{

	// Fields
	
	private String id;
	private User user;
	private Book book;
	private Date borrowTime;
	private Date returnTime;
	// Constructors

	/** default constructor */
	public Borrow() {
	}

	/** minimal constructor */
	public Borrow(String id) {
		this.id = id;
	}

	/** full constructor */
	public Borrow(User user, Book book, Date borrowTime,Date returnTime) {
		this.user = user;
		this.book = book;
		this.borrowTime = borrowTime;
		this.returnTime=returnTime;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOK_ID", nullable = false)
	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Column(name = "BORROW_TIME", nullable = false)
	public Date getBorrowTime() {
		return this.borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}
	@Column(name = "RETURN_TIME", nullable = false)
	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
}