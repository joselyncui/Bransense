package com.matrix.brainsense.dao;

import com.matrix.brainsense.entity.Admin;

public interface AdminDao extends BaseDao<Admin> {

	/**
	 * get admin by user name
	 * @param userName the name of admin
	 * @return see <code>Admin</code>
	 */
	Admin getAdminByName(String userName);

}
