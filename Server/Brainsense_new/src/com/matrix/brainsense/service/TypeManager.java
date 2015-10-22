package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.Type;


public interface TypeManager {

	/**
	 * get all available type
	 * @return return a list of <code>Type</code>
	 */
	List<Type> getAllAvailable();

	/**
	 * get available
	 * @param typeId
	 * @return
	 */
	Type getAvailableTypeById(int typeId);

	/**
	 * update type information
	 * @param type see <code>Type</code>
	 * @return if update success return true, else return false
	 */
	boolean update(Type type);

	/**
	 * add type to database
	 * @param type see <code>Type</code>
	 * @return if add success return true, else return false
	 */
	boolean add(Type type);

}
