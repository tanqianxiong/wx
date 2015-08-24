package com.dao.imp;

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
	public List<Book> getPagination(int start, int count,Map<String,String> orderProps) {
		// TODO Auto-generated method stub
		return this.doGetAll(start,count,orderProps);
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
	public List<Book> getListByLikeProperties(Map<String,Object> map){
		
		return this.doGetListByLikeProperties(map);
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
	public void alter(Book book){
		this.doUpdate(book);
	}
	@Override
	public List<Book> getLikeProperty(Map<String, Object> map, Map<String, Object> map2) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(map, map2);
	}
	@Override
	public List<Book> getListByProperty(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		return this.doGetListByProperties(prop, null);
	}
	@Override
	public List<Book> getPaginationByLikeProperty(Map<String, Object> like, Map<String, Object> and, int i,
			int itemsPerPage,Map<String,String> orderMap) {
		// TODO Auto-generated method stub
		return this.doGetListByLikeProperties(like, and, i, itemsPerPage,orderMap);
	}
	@Override
	public int getCountByLikeProperty(Map<String, Object> like, Map<String, Object> and) {
		// TODO Auto-generated method stub
		return this.doGetCountByLikeProperty(like, and);
	}
	
}
