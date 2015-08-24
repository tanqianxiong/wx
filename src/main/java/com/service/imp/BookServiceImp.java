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
	public List<Book> getPagination(int start, int count,Map<String,String> orderProps) {
		// TODO Auto-generated method stub
		return bookDao.getPagination(start,count,orderProps);
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
	public List<Book> getLikeProperty(Map<String,Object> map){
		return bookDao.getListByLikeProperties(map);
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
	@Override
	public List<Book> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return this.bookDao.getLikeProperty(map, map2);
	}
	@Override
	public List<Book> getListByProperty(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		return this.bookDao.getListByProperty(prop);
	}
	@Override
	public List<Book> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage,Map<String,String> orderMap) {
		// TODO Auto-generated method stub
		return this.bookDao.getPaginationByLikeProperty(like, and, i, itemsPerPage,orderMap);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.bookDao.getCountByLikeProperty(like, and);
	}

}
