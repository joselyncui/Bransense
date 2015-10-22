package com.matrix.brainsense.dao.impl;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.KeyCodeDao;
import com.matrix.brainsense.entity.KeyCode;

@Repository
public class KeyCodeDaoImpl extends BaseDaoImpl<KeyCode> implements KeyCodeDao {
	
	@Override
	public KeyCode getKeyCodeByKeyCode(String keyCodeValue) {
		try {
			return findForObject("keyCodeId", keyCodeValue);
		} catch (Exception e) {
			return null;
		}
	}

}
