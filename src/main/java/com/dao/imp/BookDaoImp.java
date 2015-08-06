package com.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dao.BookDao;
import com.entity.Book;
import com.common.db.HibernateDaoImp;
@Repository("bookDao")
public class BookDaoImp extends HibernateDaoImp<Book> implements BookDao{

	@Override
	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return this.getAll(null);
	}
	@Override
	public void add(Book book){
		this.insert(book);
	}
	@Override
	public List<Book> getLikeProperty(String propertyName,Object propertyValue){
		
		return this.getListLikeProperty(propertyName,propertyValue,null);
	}
	@Override
	public void delete(String id){
		this.deleteById(id);
	}
	@Override
	public Book get(String id){
		return this.getById(id);
	}
	@Override
	public void alter(Book book){
		this.update(book);
	}
}
