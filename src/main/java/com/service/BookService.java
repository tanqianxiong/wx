package com.service;

import java.util.List;

import com.entity.Book;

public interface BookService {
	public List<Book> getAll();
	public void add(Book book);
	public List<Book> getLikeProperty(String propertyName,Object value);
	public void delete(String id);
	public void alter(Book book);
	public Book get(String id);
}
