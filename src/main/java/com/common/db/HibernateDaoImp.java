package com.common.db;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


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
	public String genOrderHql(String hql, Map<String, String> orderProps) {
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
	public String insert(T entity) {
		if (entity.getClass().getCanonicalName().equals(this.entityName)) {
			return this.getHibernateTemplate().save(entity).toString();
		} else {
			throw new DataAccessResourceFailureException(errorMsg);
		}
	}

	/*
	 * 修改一个实体
	 */
	public void update(T entity) {
//		if (entity.getClass().getCanonicalName().equals(this.entityName)) {
			this.getHibernateTemplate().update(entity);
//		} else {
//			throw new DataAccessResourceFailureException(errorMsg);
//		}
	}
	
	public int updateProperty(String id,String propertyName,Object value) {
		String hql = "update "+this.entityName+" set "+propertyName+" = ? where id = ?";
		return this.getHibernateTemplate().bulkUpdate(hql,value,id);
	}

	/*
	 * 删除一个实体
	 */
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);		

	}

	public void deleteById(Object propertyValue) {
		String hql = "delete " + this.entityName + " _t001 where _t001.id=?";
		logger.info(hql);
		this.getHibernateTemplate().bulkUpdate(hql,
				new Object[] { propertyValue });

	}

	/*
	 * 根据属性删除实体
	 */
	public void deleteByProperty(String propertyName, Object propertyValue) {
		String hql = "delete " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		logger.info(hql);
		this.getHibernateTemplate().bulkUpdate(hql,
				new Object[] { propertyValue });

	}

	/*
	 * 根据属性集合删除实体
	 */
	public void deleteByProperties(Map<String, Object> param) {
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
	public T getById(String id) {
		return (T) this.getHibernateTemplate().get(this.entityName, id);
	}

	public T getByProperty(String propertyName, String propertyValue) {
		List<T> list = this
				.getListByProperty(propertyName, propertyValue, null);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T getByProperties(Map<String, Object> param,
			Map<String, String> orderProps)
	{
		List list = this.getListByProperties(param, orderProps);
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
	public List<T> getListByProperty(String propertyName, Object propertyValue,
			Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		hql = this.genOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql, propertyValue);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getListByNullProperty(String propertyName,
			Map<String, String> orderProps) {

		String hql = "from " + this.entityName + " _t001 where _t001."
				+ propertyName + " is null ";
		hql = this.genOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/*
	 * 根据实体属性得到记录数
	 */
	public int getEntityCountByProperty(String propertyName,
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
	public List getEntityListByProperty(String propertyName,
			Object propertyValue, int start, int count,
			Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		hql = this.genOrderHql(hql, orderProps);
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
	public List<T> getListByProperties(Map<String, Object> param,
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
		hql = this.genOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql, values);
	}

	/**
	 * 
	 * @param param
	 * @return
	 */
	public int getCountByProperties(Map<String, Object> param) {
		if (param == null) {
			return this.getCount();
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
	public int getCountByProperty(String property, Object value) {
		if (property == null || property.isEmpty()) {
			return this.getCount();
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

	public int getCount() {
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
	public List<T> getListByPage(Map<String, Object> paramMap, int start,
			int count, Map<String, String> orderProps) {
		if (paramMap == null) {
			return this.getListByPage(start, count, orderProps);
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
			hql = this.genOrderHql(hql, orderProps);
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
	public List<T> getListByPage(int start, int count,
			Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001 where 1=1 ";
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(useCache);
		query.setFirstResult(start);
		query.setMaxResults(count);
		logger.info(hql);
		return query.list();
	}

	public List<T> getAll() {
		return this.getAll(null);
	}

	/*
	 * 得到所有实体集合
	 */

	@SuppressWarnings("unchecked")
	public List<T> getAll(Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001";
		hql = this.genOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/*
	 * 得到所有实体集合记录数
	 */
	public int getEntityCountAll() {
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
	public List getAll(int start, int count, Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001";
		hql = this.genOrderHql(hql, orderProps);
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(useCache);
		query.setFirstResult(start);
		query.setMaxResults(count);
		logger.info(hql);
		return query.list();
	}


}
