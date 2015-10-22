package com.matrix.brainsense.service;

import com.matrix.brainsense.entity.Admin;

public interface AdminManager {

	/**
	 * get admin by user name
	 * @param userName the name of admin
	 * @return see <code>Admin</code>
	 */
	Admin getAdminByName(String userName);

}
