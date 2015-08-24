package com.service.imp;

import java.util.List;
import java.util.Map;

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
	public int getCount(){
		return bookDao.getCount();
	}
	@Override
	public void add(Book book){
		bookDao.add(book);
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
	public void update(Book book){
		bookDao.update(book);
	}
	@Override
	public List<Book> getListByProperty(String propKey, Object propValue) {
		// TODO Auto-generated method stub
		return this.bookDao.getListByProperty(propKey, propValue);
	}
	@Override
	public List<Book> getListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int startIndex,
			int itemsPerpage, Map<String, String> orderProps) {
		// TODO Auto-generated method stub
		return this.bookDao.getListByProperties(likeProps, andProps, startIndex,itemsPerpage, orderProps);
	}
	@Override
	public int getCountByProperties(Map<String, Object> likeProps, Map<String, Object> andProps) {
		// TODO Auto-generated method stub
		return this.bookDao.getCountByProperties(likeProps, andProps);
	}
	@Override
	public List<Book> getListByProperties(int startIndex, int itemsPerpage, Map<String, String> orderProps) {
		// TODO Auto-generated method stub
		return this.bookDao.getListByProperties(startIndex, itemsPerpage, orderProps);
	}
	@Override
	public List<Book> getListByLikeProperties(Map<String, Object> likeProps) {
		// TODO Auto-generated method stub
		return this.bookDao.getListByLikeProperties(likeProps);
	}
	

}
