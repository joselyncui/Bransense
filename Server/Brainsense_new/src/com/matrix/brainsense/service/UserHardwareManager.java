package com.matrix.brainsense.service;

import com.matrix.brainsense.entity.UserHardware;

public interface UserHardwareManager {

	/**
	 * save user hardware to database
	 * @param userHardware user's hardware information
	 * @return if save success return true, else return false
	 */
	boolean saveUserHardware(UserHardware userHardware);

	/**
	 * get user hardware by user id
	 * @param userId the id of user
	 * @return see <code>UserHardware</code>
	 */
	UserHardware getUserHardwareByUserId(int userId);

	/**
	 * update user hardware
	 * @param userHardware <code>UserHardware</code>
	 * @return if update success return true, else return false
	 */
	boolean update(UserHardware userHardware);

}
