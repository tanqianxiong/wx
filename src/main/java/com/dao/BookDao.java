package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Book;
import com.common.db.BaseDao;

public interface BookDao extends BaseDao<Book>{

	List<Book> getAll();
	public void add(Book book);
	List<Book> getListByLikeProperties(Map<String,Object> map);
	public void delete(String id);
	public void alter(Book book);
	public Book get(String id);
	List<Book> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	List<Book> getListByProperty(Map<String, Object> prop);
}
