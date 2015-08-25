package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Book;
import com.common.db.BaseDao;

public interface BookDao extends BaseDao<Book>{

	public List<Book> getAll();
	public int getCount();
	public void add(Book book);
	public void delete(String id);
	public void update(Book book);
	public Book get(String id);
	public List<Book> getListByProperty(String propKey, Object propValue);
	public List<Book> getListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int startIndex,
			int itemsPerpage, Map<String, String> orderProps);
	public int getCountByProperties(Map<String, Object> likeProps, Map<String, Object> andProps);
	public List<Book> getListByProperties(int startIndex, int itemsPerpage, Map<String, String> orderProps);
	public List<Book> getListByLikeProperties(Map<String, Object> likeProps);
	public void updateBookState();
}
