package com.matrix.brainsense.dao.impl;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.UserHardwareDao;
import com.matrix.brainsense.entity.UserHardware;

@Repository
public class UserHardwareDaoImpl extends BaseDaoImpl<UserHardware> implements UserHardwareDao {

	@Override
	public UserHardware getUserHardwareByUserId(int userId) {
		UserHardware result = null;
		try {
			result = findForObject("userId", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
