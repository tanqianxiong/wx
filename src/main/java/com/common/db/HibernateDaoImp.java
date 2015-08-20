package com.common.db;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.entity.Book;


public class HibernateDaoImp<T> extends HibernateDaoSupport {

	/**
	 * 关联实体
	 */
	public String entityName;

	/**
	 * 是否使用缓存
	 */
	private boolean useCache = false;

	/**
	 * 错误信息
	 */
	private String errorMsg = "Error! The type of entity is not what it is expected!";

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@PostConstruct
	public void init() {
		@SuppressWarnings("unchecked")
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityName = entityClass.getCanonicalName();
	}

	/**
	 * 拼装sql
	 * 
	 * @param hql
	 * @param orderProps
	 * @return
	 */
	public String doGenOrderHql(String hql, Map<String, String> orderProps) {
		StringBuffer orderStr = new StringBuffer();
		orderStr.append(hql);
		if (orderProps != null && orderProps.size() != 0) {
			orderStr.append(" order by ");
			int k = 0;
			for (String key : orderProps.keySet()) {
				if (k != orderProps.keySet().size() - 1) {
					orderStr.append(" _t001."
							+ key
							+ " "
							+ (orderProps.get(key) == null ? ""
									: ("asc".equals(orderProps.get(key))
											|| "desc".equals(orderProps
													.get(key)) ? orderProps
											.get(key) : "")) + ",");
				} else {
					orderStr.append(" _t001."
							+ key
							+ " "
							+ (orderProps.get(key) == null ? ""
									: ("asc".equals(orderProps.get(key))
											|| "desc".equals(orderProps
													.get(key)) ? orderProps
											.get(key) : "")));
				}
				k++;
			}
		}
		return orderStr.toString();
	}

	
	/*
	 * 插入一个实体
	 */
	public String doInsert(T entity) {
		if (entity.getClass().getCanonicalName().equals(this.entityName)) {
			return this.getHibernateTemplate().save(entity).toString();
		} else {
			throw new DataAccessResourceFailureException(errorMsg);
		}
	}

	/*
	 * 修改一个实体
	 */
	public void doUpdate(T entity) {
//		if (entity.getClass().getCanonicalName().equals(this.entityName)) {
			this.getHibernateTemplate().update(entity);
//		} else {
//			throw new DataAccessResourceFailureException(errorMsg);
//		}
	}
	
	public int doUpdateProperty(String id,String propertyName,Object value) {
		String hql = "update "+this.entityName+" set "+propertyName+" = ? where id = ?";
		return this.getHibernateTemplate().bulkUpdate(hql,value,id);
	}

	/*
	 * 删除一个实体
	 */
	public void doDelete(T entity) {
		this.getHibernateTemplate().delete(entity);		

	}

	public void doDeleteById(Object propertyValue) {
		String hql = "delete " + this.entityName + " _t001 where _t001.id=?";
		logger.info(hql);
		this.getHibernateTemplate().bulkUpdate(hql,
				new Object[] { propertyValue });

	}

	/*
	 * 根据属性删除实体
	 */
	public void doDeleteByProperty(String propertyName, Object propertyValue) {
		String hql = "delete " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		logger.info(hql);
		this.getHibernateTemplate().bulkUpdate(hql,
				new Object[] { propertyValue });

	}

	/*
	 * 根据属性集合删除实体
	 */
	public void doDeleteByProperties(Map<String, Object> param) {
		StringBuffer keys = new StringBuffer();
		Object[] values = new Object[param.keySet().size()];
		int i = 0;
		for (String key : param.keySet()) {
			keys.append("and _t001." + key + "=? ");
			values[i] = param.get(key);
			i++;
		}
		String hql = "delete " + this.entityName + " _t001 where 1=1 "
				+ keys.toString();
		logger.info(hql);
		this.getHibernateTemplate().bulkUpdate(hql, values);
	}

	/*
	 * 根据id得到实体
	 */
	@SuppressWarnings("unchecked")
	public T doGetById(String id) {
		return (T) this.getHibernateTemplate().get(this.entityName, id);
	}

	public T doGetByProperty(String propertyName, String propertyValue) {
		List<T> list = this
				.doGetListByProperty(propertyName, propertyValue, null);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T doGetByProperties(Map<String, Object> param,
			Map<String, String> orderProps)
	{
		List list = this.doGetListByProperties(param, orderProps);
		if (list.size() > 0) {
			return (T)list.get(0);
		} else {
			return null;
		}
		
	}

	/*
	 * 根据实体属性得到实体列表
	 */
	@SuppressWarnings({ "unchecked" })
	public List<T> doGetListByProperty(String propertyName, Object propertyValue,
			Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		hql = this.doGenOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql, propertyValue);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> doGetListByNullProperty(String propertyName,
			Map<String, String> orderProps) {

		String hql = "from " + this.entityName + " _t001 where _t001."
				+ propertyName + " is null ";
		hql = this.doGenOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/*
	 * 根据实体属性得到记录数
	 */
	public int doGetEntityCountByProperty(String propertyName,
			Object propertyValue) {
		String hql = "select count(_t001) from " + this.entityName
				+ " _t001 where _t001." + propertyName + "=?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, propertyValue);
		query.setCacheable(useCache);
		logger.info(hql);
		return ((Long) query.uniqueResult()).intValue();
	}

	/*
	 * 根据实体属性得到一页记录
	 */
	@SuppressWarnings("rawtypes")
	public List doGetEntityListByProperty(String propertyName,
			Object propertyValue, int start, int count,
			Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		hql = this.doGenOrderHql(hql, orderProps);
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, propertyValue);
		query.setCacheable(useCache);
		query.setFirstResult(start);
		query.setMaxResults(count);
		logger.info(hql);
		return query.list();
	}

	/**
	 * 根据实体属性键值对得到实体列表
	 * 
	 * @param param
	 * @param orderProps
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> doGetListByProperties(Map<String, Object> param,
			Map<String, String> orderProps) {
		StringBuffer keys = new StringBuffer();
		Object[] values = new Object[param.keySet().size()];
		int i = 0;
		for (String key : param.keySet()) {
			keys.append("and _t001." + key + "=? ");
			values[i] = param.get(key);
			i++;
		}
		String hql = "from " + this.entityName + " _t001 where 1=1 "
				+ keys.toString();
		hql = this.doGenOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql, values);
	}

	/**
	 * 
	 * @param param
	 * @return
	 */
	public int doGetCountByProperties(Map<String, Object> param) {
		if (param == null) {
			return this.doGetCount();
		} else {
			StringBuffer keys = new StringBuffer();
			Object[] values = new Object[param.keySet().size()];
			int i = 0;
			for (String key : param.keySet()) {
				keys.append("and _t001." + key + "=? ");
				values[i] = param.get(key);
				i++;
			}
			String hql = "select count(_t001) from " + this.entityName
					+ " _t001 where 1=1 " + keys.toString();
			Query query = this.getSession().createQuery(hql);
			for (int j = 0; j < values.length; j++) {
				query.setParameter(j, values[j]);
			}
			query.setCacheable(useCache);
			logger.info(hql);
			return ((Long) query.uniqueResult()).intValue();
		}
	}

	/**
	 * hl
	 * 
	 * @param param
	 * @return
	 */
	public int doGetCountByProperty(String property, Object value) {
		if (property == null || property.isEmpty()) {
			return this.doGetCount();
		} else {
			StringBuffer keys = new StringBuffer();
			keys.append("and _t001." + property + "=? ");
			String hql = "select count(_t001) from " + this.entityName
					+ " _t001 where 1=1 " + keys.toString();
			Query query = this.getSession().createQuery(hql);
			query.setParameter(0, value);
			query.setCacheable(useCache);
			logger.info(hql);
			return ((Long) query.uniqueResult()).intValue();
		}
	}

	public int doGetCount() {
		String hql = "select count(_t001) from " + this.entityName
				+ " _t001 where 1=1 ";
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(useCache);
		logger.info(hql);
		return ((Long) query.uniqueResult()).intValue();
	}

	/**
	 * 获取一页的记录
	 * 
	 * @param paramMap
	 * @param start
	 * @param count
	 * @param orderProps
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> doGetListByPage(Map<String, Object> paramMap, int start,
			int count, Map<String, String> orderProps) {
		if (paramMap == null) {
			return this.doGetListByPage(start, count, orderProps);
		} else {
			StringBuffer keys = new StringBuffer();
			Object[] values = new Object[paramMap.keySet().size()];
			int i = 0;
			for (String key : paramMap.keySet()) {
				keys.append("and _t001." + key + "=? ");
				values[i] = paramMap.get(key);
				i++;
			}
			String hql = "from " + this.entityName + " _t001 where 1=1 "
					+ keys.toString();
			hql = this.doGenOrderHql(hql, orderProps);
			Query query = this.getSession().createQuery(hql);
			for (int j = 0; j < values.length; j++) {
				query.setParameter(j, values[j]);
			}
			query.setCacheable(useCache);
			query.setFirstResult(start);
			query.setMaxResults(count);
			logger.info(hql);
			return query.list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> doGetListByPage(int start, int count,
			Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001 where 1=1 ";
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(useCache);
		query.setFirstResult(start);
		query.setMaxResults(count);
		logger.info(hql);
		return query.list();
	}

	public List<T> doGetAll() {
		return this.doGetAll(null);
	}

	/*
	 * 得到所有实体集合
	 */

	@SuppressWarnings("unchecked")
	public List<T> doGetAll(Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001";
		hql = this.doGenOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/*
	 * 得到所有实体集合记录数
	 */
	public int doGetEntityCountAll() {
		String hql = "select count(_t001) from " + this.entityName + " _t001";
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(useCache);
		logger.info(hql);
		return ((Long) query.uniqueResult()).intValue();
	}

	/*
	 * 得到所有实体集合一页记录
	 */
	@SuppressWarnings("rawtypes")
	public List doGetAll(int start, int count, Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001";
		hql = this.doGenOrderHql(hql, orderProps);
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(useCache);
		query.setFirstResult(start);
		query.setMaxResults(count);
		logger.info(hql);
		return query.list();
	}
	public List<T> doGetListByLikeProperties(Map<String, Object> likeProps){
		return doGetListByLikeProperties(likeProps,null);
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<T> doGetListByLikeProperties(Map<String, Object> likeProps,Map<String, Object> andProps){
		Criteria criteria = this.getSession(true).createCriteria(this.entityName);
		if(likeProps!=null && !likeProps.isEmpty()){
			List<SimpleExpression> seList=new ArrayList<SimpleExpression>();
			for (String key : likeProps.keySet()) {
				seList.add(Restrictions.like(key,likeProps.get(key)));
			}
			if(likeProps.size()<2){
				criteria.add(seList.get(0));
			}
			else{
				criteria.add(orExpressionLink(seList));
			}
		}
		if(andProps!=null && !andProps.isEmpty()){
			criteria.add(Restrictions.allEq(andProps));
		}
		return criteria.list();
	}

	/*
	 * 递归函数
	 */
	private LogicalExpression orExpressionLink(List<SimpleExpression> seList){
		if(seList.size()<3){
			return Restrictions.or(seList.get(0),seList.get(1));
		}
		else{
			//List<SimpleExpression> tmpList=new ArrayList<SimpleExpression>(seList.subList(1, seList.size()-1));
			SimpleExpression se=seList.get(0);
			seList.subList(0, 1).clear();
			return Restrictions.or(se,orExpressionLink(seList));
		}
	}
	@SuppressWarnings("unchecked")
	public List<T> doGetListByLikeProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int i,
			int itemsPerPage) {
		Criteria criteria = this.getSession(true).createCriteria(this.entityName);
		if(likeProps!=null && !likeProps.isEmpty()){
			List<SimpleExpression> seList=new ArrayList<SimpleExpression>();
			for (String key : likeProps.keySet()) {
				seList.add(Restrictions.like(key,likeProps.get(key)));
			}
			if(likeProps.size()<2){
				criteria.add(seList.get(0));
			}
			else{
				criteria.add(orExpressionLink(seList));
			}
		}
		if(andProps!=null && !andProps.isEmpty()){
			criteria.add(Restrictions.allEq(andProps));
		}
		criteria.setFirstResult(i);
		criteria.setMaxResults(itemsPerPage);
		return criteria.list();
	}
	public int doGetCountByLikeProperty(Map<String, Object> likeProps, Map<String, Object> andProps) {
		Criteria criteria = this.getSession(true).createCriteria(this.entityName);
		if(likeProps!=null && !likeProps.isEmpty()){
			List<SimpleExpression> seList=new ArrayList<SimpleExpression>();
			for (String key : likeProps.keySet()) {
				seList.add(Restrictions.like(key,likeProps.get(key)));
			}
			if(likeProps.size()<2){
				criteria.add(seList.get(0));
			}
			else{
				criteria.add(orExpressionLink(seList));
			}
		}
		if(andProps!=null && !andProps.isEmpty()){
			criteria.add(Restrictions.allEq(andProps));
		}
		return criteria.list().size();
	}
}
