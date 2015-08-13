package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Book;

public interface BookService {
	public List<Book> getAll();
	public void add(Book book);
	public void delete(String id);
	public void alter(Book book);
	public Book get(String id);
	List<Book> getLikeProperty(Map<String, Object> map);
	public List<Book> getLikeProperty(Map<String, Object> map, Map<String, Object> map2);
	public List<Book> getListByProperty(Map<String, Object> prop);
}
