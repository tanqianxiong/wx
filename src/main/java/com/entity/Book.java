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
@Table(name = "BOOK", schema = "wx")
public class Book  implements Serializable{

	// Fields
	
	private String id;
	private String ISBN;
	private String name;
	private String author;
	private String publisher;
	private Integer year;
	private String type;
	private Integer totalNum;
	private Integer outNum;
	private Float points;
	private String brief;
	
	// Constructors

	/** default constructor */
	public Book() {
	}

	/** minimal constructor */
	public Book(String id) {
		this.id = id;
	}

	/** full constructor */
	public Book(String ISBN,String name,String author, String publisher,Integer year,String type,Integer totalNum,int outNum,float points,String brief) {
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.year=year;
		this.type=type;
		this.totalNum=totalNum;
		this.outNum=outNum;
		this.points=points;
		this.brief=brief;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Book_ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "ISBN", nullable = true, length = 36)
	public String getISBN() {
		return this.ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@Column(name = "Book_Name", nullable = true, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Book_Author", nullable = true, length = 100)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "Book_Publish", nullable = true, length = 100)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "Book_Year", nullable = true, length = 11)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Column(name = "Book_Type", nullable = true, length = 20)
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	@Column(name = "Book_Total_Num", nullable = true, length = 11)
	public Integer getTotalNum(){
		return this.totalNum;
	}
	public void setTotalNum(Integer totalNum){
		this.totalNum=totalNum;
	}
	@Column(name = "Book_Out_Num", nullable = true, length =11)
	public Integer getOutNum(){
		return this.outNum;
	}
	public void setOutNum(Integer outNum){
		this.outNum = outNum;
	}
	@Column(name = "Book_Points", nullable = true, length = 4)
	public Float getPoints(){
		return this.points;
	}
	public void setPoints(Float points){
		this.points = points;
	}
	@Column(name = "Book_Brief", nullable = true, length = 1000)
	public String getBrief(){
		return this.brief;
	}
	public void setBrief(String brief){
		this.brief = brief;
	}
	
}