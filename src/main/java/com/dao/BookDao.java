package com.dao;

import java.util.List;

import com.entity.Book;
import com.common.db.BaseDao;

public interface BookDao extends BaseDao<Book>{

	List<Book> getAll();
	public void add(Book book);
	List<Book> getLikeProperty(String propertyName,Object value);
	public void delete(String id);
	public void alter(Book book);
	public Book get(String id);
}
