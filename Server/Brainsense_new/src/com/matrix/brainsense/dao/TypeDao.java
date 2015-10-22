package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.Type;

public interface TypeDao extends BaseDao<Type> {

	/**
	 * get type by status
	 * @param status the status of the type
	 * @return return a list of <code>Type</code>
	 */
	List<Type> getTypeByStatus(int status);

	/**
	 * get type by status and id
	 * @param typeId the id of type
	 * @param status the status of type
	 * @return see <code>Type</code>
	 */
	Type getTypeByIdAndStatus(int typeId, int status);

}
