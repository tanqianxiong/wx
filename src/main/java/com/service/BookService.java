package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Book;

public interface BookService {
	public List<Book> getAll();
	public List<Book> getPagination(int start, int count);
	public int getCount();
	public void add(Book book);
	public void delete(String id);
	public void alter(Book book);
	public Book get(String id);
	List<Book> getLikeProperty(Map<String, Object> map);
	public List<Book> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	public List<Book> getListByProperty(Map<String, Object> prop);
	public List<Book> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage);
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and);
}
