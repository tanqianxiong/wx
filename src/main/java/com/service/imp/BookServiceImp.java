package com.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BookDao;
import com.entity.Book;
import com.service.BookService;

@Service("bookService")
public class BookServiceImp implements BookService{
	
	@Autowired
	BookDao bookDao;
	@Override
	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return bookDao.getAll();
	}
	@Override
	public void add(Book book){
		bookDao.add(book);
	}
	
	@Override
	public List<Book> getLikeProperty(String propertyName,Object propertyValue){
		return bookDao.getLikeProperty(propertyName,propertyValue);
	}
	@Override
	public void delete(String id){
		bookDao.delete(id);
	}
	@Override
	public Book get(String id){
		return bookDao.get(id);
	}
	@Override
	public void alter(Book book){
		bookDao.alter(book);
	}

}
