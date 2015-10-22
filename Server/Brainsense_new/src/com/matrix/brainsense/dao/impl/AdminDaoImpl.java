package com.matrix.brainsense.dao.impl;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.AdminDao;
import com.matrix.brainsense.entity.Admin;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao {

	@Override
	public Admin getAdminByName(String userName) {
		try {
			return findForObject("name", userName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
