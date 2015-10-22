package com.matrix.brainsense.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.UserHardwareDao;
import com.matrix.brainsense.entity.UserHardware;
import com.matrix.brainsense.service.UserHardwareManager;

@Service
@Transactional
public class UserHardwareManagerImpl implements UserHardwareManager {
	
	@Autowired
	private UserHardwareDao userHardwareDao;
	
	@Override
	public boolean saveUserHardware(UserHardware userHardware) {
		boolean result = true;
		try {
			userHardwareDao.add(userHardware);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public UserHardware getUserHardwareByUserId(int userId) {
		return userHardwareDao.getUserHardwareByUserId(userId);
	}

	@Override
	public boolean update(UserHardware userHardware) {
		boolean flag = true;
		try {
			userHardwareDao.update(userHardware);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
