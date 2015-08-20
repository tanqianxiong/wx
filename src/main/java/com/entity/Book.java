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
	//图书的唯一识别码
	private String ISBN;
	//图书名称
	private String bookName;
	//作者
	private String author;
	//出版社
	private String publisher;
	//出版年份
	private int publishTime;
	//图书类型（管理类、文学类、经济类、技术类、综合类、其他）
	private String type;
	//当前图书的馆藏数量
	private int amount;
	//已借人数
	private int borrowed;
	//图书评分
	private double points;
	//图书简介
	private String brief;
	//图书入库时间
	private Date bookInputTime;
	//图书状态（新书、旧书）
	private String bookState;
	//图书已评人数
	private int commentNum;
	
	
	// Constructors

	/** default constructor */
	public Book() {
	}

	/** minimal constructor */
	public Book(String id) {
		this.id = id;
	}

	/** full constructor */
	public Book(String ISBN,String bookName,String author, String publisher,int publishTime,String type,int amount,int borrowed,double points,String brief,Date bookInputTime,String bookState,int commentNum) {
		this.ISBN = ISBN;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.publishTime=publishTime;
		this.type=type;
		this.amount=amount;
		this.borrowed=borrowed;
		this.points=points;
		this.brief=brief;
		this.bookInputTime=bookInputTime;
		this.bookState=bookState;
		this.commentNum=commentNum;
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
	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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
	public int getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(int publishTime) {
		this.publishTime = publishTime;
	}
	
	@Column(name = "Book_Type", nullable = true, length = 20)
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	@Column(name = "Book_Total_Num", nullable = true, length = 11)
	public int getAmount(){
		return this.amount;
	}
	public void setAmount(int amount){
		this.amount=amount;
	}
	@Column(name = "Book_Out_Num", nullable = true, length =11)
	public int getBorrowed(){
		return this.borrowed;
	}
	public void setBorrowed(int borrowed){
		this.borrowed = borrowed;
	}
	@Column(name = "Book_Points", nullable = true, length = 4)
	public double getPoints(){
		return this.points;
	}
	public void setPoints(double points){
		this.points = points;
	}
	@Column(name = "Book_Brief", nullable = true, length = 1000)
	public String getBrief(){
		return this.brief;
	}
	public void setBrief(String brief){
		this.brief = brief;
	}
	@Column(name = "Book_Input_Time", nullable = true)
	public Date getBookInputTime(){
		return this.bookInputTime;
	}
	public void setBookInputTime(Date bookInputTime){
		this.bookInputTime=bookInputTime;
	}
	
	@Column(name = "Book_State", nullable = true, length = 10)
	public String getBookState(){
		return this.bookState;
	}
	public void setBookState(String bookState){
		this.bookState = bookState;
	}
	@Column(name = "Book_Comment_Num", nullable = true, length = 11)
	public int getCommentNum(){
		return this.commentNum;
	}
	public void setCommentNum(int commentNum){
		this.commentNum=commentNum;
	}
}