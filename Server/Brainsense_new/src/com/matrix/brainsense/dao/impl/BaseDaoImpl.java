package com.matrix.brainsense.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.BaseDao;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void add(T t) throws Exception {
		hibernateTemplate.save(t);
	}
	
	@Override
	public T addWithResult(T t) throws Exception{
		hibernateTemplate.save(t);
		return t;
	}

	@Override
	public void update(T t) throws Exception {
		hibernateTemplate.update(t);
	}

	@Override
	public void delete(T t) throws Exception {
		hibernateTemplate.delete(t);
	}

	@Override
	public void deleteById(Serializable id) throws Exception {
		String hql = "delete " + getEntityClassName() + " where "
				+ getPkColunmName() + " = ?";
		executeHql(hql, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) throws Exception {
		return (T) hibernateTemplate.get(getEntityClassName(), id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws Exception {
		String hql = "from " + this.getEntityClassName();
		return (List<T>) hibernateTemplate.find(hql);
	}
	
	@Override
	public T findForObject(String columnName, Object object) throws Exception {
		String hql = "from " + this.getEntityClassName() + " where " + columnName + " = ?";
		return queryForObject(hql, object);
	}
	
	@Override
	public T findForObject(List<String> columnNames, List<Object> objects) throws Exception {
		Object[] objArray = convertListToArray(objects);
		StringBuilder hql = new StringBuilder("from " + this.getEntityClassName() + " where ");
		for(int i=0; i<columnNames.size(); i++){
			if(i != columnNames.size() - 1){
				hql.append(columnNames.get(i) + " = ? and ");
			}else{
				hql.append(columnNames.get(i) + " = ?");
			}
		}
		List<T> ts = queryForList(hql.toString(), objArray);
		if(ts == null || ts.size() == 0){
			return null;
		}
		return ts.get(0);
	}
	
	@Override
	public List<T> findForList(List<String> columnNames, List<Object> objects) throws Exception {
		Object[] objArray = convertListToArray(objects);
		StringBuilder hql = new StringBuilder("from " + this.getEntityClassName() + " where ");
		for(int i=0; i<columnNames.size(); i++){
			if(i != columnNames.size() - 1){
				hql.append(columnNames.get(i) + " = ? and ");
			}else{
				hql.append(columnNames.get(i) + " = ?");
			}
		}
		return queryForList(hql.toString(), objArray);
	}
	
	@Override
	public List<T> findForList(String columnName, Object object) throws Exception {
		String hql = "from " + this.getEntityClassName() + " where " + columnName + " = ?";
		return queryForList(hql.toString(), object);
	}
	
	public List<String> getColumnList(String ... columnNames){
		List<String> results = new ArrayList<String>();
		if(columnNames == null || columnNames.length == 0){
			return results;
		}
		for(int i=0; i<columnNames.length; i++){
			results.add(columnNames[i]);
		}
		return results;
	}
	
	public List<Object> getParams(Object... params){
		List<Object> result = new ArrayList<Object>();
		if(params != null){
			for(Object param : params){
				result.add(param);
			}
		}
		return result;
	}

	private void executeHql(final String hql, final Object... objects) {
		hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				Query createQuery = createQuery(session, hql, objects);
				return createQuery.executeUpdate();
			}
		});
	}
	
	private List<T> queryForList(final String hql, final Object ... objects){
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException {
				Query createQuery = createQuery(session, hql, objects);
				return createQuery.list();
			}
		});
	}
	
	private T queryForObject(final String hql, final Object object){
		List<T> ts = queryForList(hql, object);
		if(ts == null || ts.size() == 0){
			return null;
		}
		return ts.get(0);
	}

	private Class<T> getEntityClass() {
		@SuppressWarnings("unchecked")
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}

	private String getPkColunmName() {
		ClassMetadata meta = hibernateTemplate.getSessionFactory()
				.getClassMetadata(getEntityClass());
		return meta.getIdentifierPropertyName();
	}

	private String getEntityClassName() {
		ClassMetadata meta = hibernateTemplate.getSessionFactory()
				.getClassMetadata(getEntityClass());
		return meta.getEntityName();
	}
	
	private Object[] convertListToArray(List<Object> objects){
		Object [] objArray = new Object[objects.size()];
		for(int i=0; i<objects.size(); i++){
			objArray[i] = objects.get(i);
		}
		return objArray;
	}

	private Query createQuery(Session session, String hql, Object... objects) {
		Query query = session.createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query;
	}
	
}
