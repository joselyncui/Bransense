package com.matrix.brainsense.dao;

import com.matrix.brainsense.entity.UserHardware;

public interface UserHardwareDao extends BaseDao<UserHardware> {

	/**
	 * get user hardware by user id
	 * @param userId the id of user
	 * @return see <code>UserHardware</code>
	 */
	UserHardware getUserHardwareByUserId(int userId);

}
