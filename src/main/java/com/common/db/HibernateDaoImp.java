package com.common.db;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.common.util.GBKOrder;

public class HibernateDaoImp<T> extends HibernateDaoSupport {

	/**
	 * 关联实体
	 */
	public String entityName;

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

	
	/**
	 * 插入一个实体
	 */
	public String doInsert(T entity) {
		if (entity.getClass().getCanonicalName().equals(this.entityName)) {
			return this.getHibernateTemplate().save(entity).toString();
		} else {
			throw new DataAccessResourceFailureException(errorMsg);
		}
	}

	/**
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

	/**
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

	/**
	 * 根据属性删除实体
	 */
	public void doDeleteByProperty(String propertyName, Object propertyValue) {
		String hql = "delete " + this.entityName + " _t001 where _t001."
				+ propertyName + "=?";
		logger.info(hql);
		this.getHibernateTemplate().bulkUpdate(hql,
				new Object[] { propertyValue });

	}

	/**
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

	/**
	 * 根据id得到实体
	 */
	@SuppressWarnings("unchecked")
	public T doGetById(String id) {
		return (T) this.getHibernateTemplate().get(this.entityName, id);
	}

	

	public List<T> doGetAll() {
		return this.doGetAll(null);
	}

	/**
	 * 得到所有实体集合
	 */

	@SuppressWarnings("unchecked")
	public List<T> doGetAll(Map<String, String> orderProps) {
		String hql = "from " + this.entityName + " _t001";
		hql = this.doGenOrderHql(hql, orderProps);
		logger.info(hql);
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 递归函数,构造orExpression
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
	/**
	 * 获取Criteria
	 * @param	likeProps
	 * 			模块查询条件
	 * @param	andProps
	 * 			与查询条件
	 * @param	pageIndex
	 * 			当前页数
	 * @param	itemsPerpage
	 * 			每页记录数
	 * @param	orderProps
	 * 			排序条件，格式为：<属性名称,排序方式>,其中排序方式可选:desc和asc
	 * @return Criteria
	 * 
	 */
	@SuppressWarnings("deprecation")
	private Criteria doGetCriteriaByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int pageIndex,
			int itemsPerpage,Map<String,String> orderProps) {
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
		if(orderProps!=null && !orderProps.isEmpty()){
			for (String key : orderProps.keySet()) {
				if(orderProps.get(key).equals("desc")){
					criteria.addOrder(GBKOrder.desc(key));
				}else{
					criteria.addOrder(GBKOrder.asc(key));
				}				
			}	
		}
		if(itemsPerpage!=0){
			criteria.setFirstResult(pageIndex);
			criteria.setMaxResults(itemsPerpage);
		}
		return criteria;
	}
	
	/**
	 * 获取一页数据
	 * @param	likeProps
	 * 			模块查询条件
	 * @param	andProps
	 * 			与查询条件
	 * @param	pageIndex
	 * 			当前页数
	 * @param	itemsPerpage
	 * 			每页记录数
	 * @param	orderProps
	 * 			排序条件，格式为：<属性名称,排序方式>,其中排序方式可选:desc和asc
	 * @return list
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<T> doGetListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int pageIndex,
			int itemsPerpage,Map<String,String> orderProps) {		
		return doGetCriteriaByProperties(likeProps,andProps,pageIndex,itemsPerpage,orderProps).list();
	}
	/**
	 * 获取一页数据,不排序
	 * @param	likeProps
	 * 			模块查询条件
	 * @param	andProps
	 * 			与查询条件
	 * @param	pageIndex
	 * 			当前页数
	 * @param	itemsPerpage
	 * 			每页记录数
	 * @return list
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<T> doGetListByProperties(Map<String, Object> likeProps, Map<String, Object> andProps, int pageIndex,
			int itemsPerpage) {
		return doGetCriteriaByProperties(likeProps,andProps,pageIndex,itemsPerpage,null).list();
	}
	/**
	 * 获取条件下的记录数
	 * @param	likeProps
	 * 			模块查询条件
	 * @param	andProps
	 * 			与查询条件
	 * @return int
	 * 
	 */
	public int doGetCountByProperties(Map<String, Object> likeProps, Map<String, Object> andProps) {
		return doGetListByProperties(likeProps,andProps,0,0,null).size();
	}
	/**
	 * 获取所有的记录数
	 * @return int
	 * 
	 */
	public int doGetCount() {
		return doGetListByProperties(null,null,0,0,null).size();
	}
	/**
	 * 获取条件下的记录数
	 * @param	andProps
	 * 			与查询条件
	 * @return int
	 * 
	 */
	public int doGetCountByProperties(Map<String, Object> andProps) {
		return doGetListByProperties(null,andProps,0,0,null).size();
	}
	/**
	 * 获取单个条件下的记录数
	 * @param	andProps
	 * 			与查询条件
	 * @return int
	 * 
	 */
	public int doGetCountByProperty(String propKey,Object propValue) {
		Map<String, Object> andProps=new HashMap<String,Object>();
		andProps.put(propKey, propValue);
		return doGetListByProperties(null,andProps,0,0,null).size();
	}
	
	/**
	 * 获取模糊查询结果,不排序,不分页
	 * @param	likeProps
	 * 			模块查询条件
	 * @return list
	 * 
	 */
	public List<T> doGetListByProperties(Map<String, Object> likeProps){
		return doGetListByProperties(likeProps,null);
	}
	/**
	 * 获取单个条件下结果,不排序,不分页
	 * @param	propKey
	 * 			条件名称
	 * @param	propValue
	 * 			条件值
	 * @return list
	 * 
	 */
	public List<T> doGetListByProperty(String propKey,Object propValue){
		Map<String,Object> props=new HashMap<String,Object>();
		props.put(propKey, propValue);
		return doGetListByProperties(null,props);
	}
	
	/**
	 * 获取模糊查询和条件查询结果,不排序
	 * @param	likeProps
	 * 			模块查询条件
	 * @param	andProps
	 * 			与查询条件
	 * @return list
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public List<T> doGetListByProperties(Map<String, Object> likeProps,Map<String, Object> andProps){
		return doGetCriteriaByProperties(likeProps,andProps,0,0,null).list();
	}
	/**
	 * 根据条件，修改属性值,暂时只供定时更新图书状态使用
	 * @param	props
	 * 			<属性名,属性值>
	 * @param	where
	 * 			修改条件
	 * @param	propType
	 * 			属性类型
	 */
	public void doUpdateProperty(Map<String, Object> props, String whereProp,String propType) {
		// TODO Auto-generated method stub
		StringBuffer keys = new StringBuffer();
		Object[] values = new Object[props.keySet().size()];
		int i = 0;
		for (String key : props.keySet()) {
			if(i==props.keySet().size()-1){
				keys.append("_t001." + key + "=? ");
			}
			else{
				keys.append(" _t001." + key + "=?, ");
			}
			values[i] = props.get(key);
			i++;
		}
		
		String hql ="update "+this.entityName+" _t001 set "+keys.toString()+" where TO_DAYS(NOW())-TO_DAYS("+whereProp+")>=5";
		this.getHibernateTemplate().bulkUpdate(hql, values);
	}
}
