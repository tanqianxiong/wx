package com.dao.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.BookDao;
import com.entity.Book;
import com.common.db.HibernateDaoImp;
@Repository("bookDao")
public class BookDaoImp extends HibernateDaoImp<Book> implements BookDao{

	@Override
	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return this.doGetAll(null);
	}
	
	@Override
	public int getCount(){
		return this.doGetCount();
	}
	@Override
	public void add(Book book){
		this.doInsert(book);
	}
	
	@Override
	public void delete(String id){
		this.doDeleteById(id);
	}
	@Override
	public Book get(String id){
		return this.doGetById(id);
	}
	@Override
	public void update(Book book){
		this.doUpdate(book);
	}

	@Override
	public List<Book> getListByProperty(String propKey, Object propValue) {
		// TODO Auto-generated method stub
		return this.doGetListByProperty(propKey, propValue);
	}

	@Override
	public List<Book> getListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int startIndex,
			int itemsPerpage, Map<String, String> orderProps) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(likeProps, andProps, startIndex, itemsPerpage, orderProps);
	}

	@Override
	public int getCountByProperties(Map<String, Object> likeProps, Map<String, Object> andProps) {
		// TODO Auto-generated method stub
		return this.doGetCountByProperties(likeProps, andProps);
	}

	@Override
	public List<Book> getListByProperties(int startIndex, int itemsPerpage, Map<String, String> orderProps) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(null, null, startIndex, itemsPerpage, orderProps);
	}

	@Override
	public List<Book> getListByLikeProperties(Map<String, Object> likeProps) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(likeProps);
	}

	@Override
	public void updateBookState() {
		// TODO Auto-generated method stub
		Map<String,Object> props=new HashMap<String,Object>();
		props.put("bookState", "旧书");
		this.doUpdateProperty(props,"bookInputTime","time");
	}

	
}
