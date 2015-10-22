package com.matrix.brainsense.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.AdminDao;
import com.matrix.brainsense.entity.Admin;
import com.matrix.brainsense.service.AdminManager;

@Service
@Transactional
public class AdminManagerImpl implements AdminManager {

	@Autowired
	private AdminDao adminDao;
	
	@Override
	public Admin getAdminByName(String userName) {
		return adminDao.getAdminByName(userName);
	}

}
