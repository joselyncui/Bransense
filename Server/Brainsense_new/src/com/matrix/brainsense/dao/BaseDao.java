package com.matrix.brainsense.dao;

import java.util.List;

/**
 * Base function to database
 * @author Like
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * add an object to database
	 * @param t
	 * @throws Exception
	 */
	void add(T t) throws Exception;
	
	/**
	 * update an object to database
	 * @param t
	 * @throws Exception
	 */
	void update(T t) throws Exception;
	
	/**
	 * delete an object from database
	 * @param t
	 * @throws Exception
	 */
	void delete(T t) throws Exception;
	
	/**
	 * delete an object by id
	 * @param id
	 * @throws Exception
	 */
	void deleteById(java.io.Serializable id) throws Exception;
	
	/**
	 * get all rows in database
	 * @return
	 * @throws Exception
	 */
	List<T> findAll() throws Exception;
	
	/**
	 * get a row from database
	 * @param id
	 * @return
	 * @throws Exception
	 */
	T findById(java.io.Serializable id) throws Exception;
	
	/**
	 * get a row by where
	 * @param columnName
	 * @param object
	 * @return
	 * @throws Exception
	 */
	T findForObject(String columnName, Object object) throws Exception;
	
	/**
	 * get a row by where
	 * @param columnNames
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	T findForObject(List<String> columnNames, List<Object> objects) throws Exception;
	
	/**
	 * get a list of row by where
	 * @param columnNames
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	List<T> findForList(List<String> columnNames, List<Object> objects) throws Exception;
	
	/**
	 * get a list of row by where
	 * @param columnName
	 * @param object
	 * @return
	 * @throws Exception
	 */
	List<T> findForList(String columnName, Object object) throws Exception;
	
	/**
	 * return an object after insert to database
	 * @param t
	 * @return
	 * @throws Exception
	 */
	T addWithResult(T t) throws Exception;
	
	
}
