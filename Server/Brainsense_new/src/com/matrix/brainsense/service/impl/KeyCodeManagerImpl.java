package com.matrix.brainsense.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.KeyCodeDao;
import com.matrix.brainsense.entity.KeyCode;
import com.matrix.brainsense.service.KeyCodeManager;

@Service
@Transactional
public class KeyCodeManagerImpl implements KeyCodeManager {
	
	@Autowired
	KeyCodeDao keyCodeDao;
	
	@Override
	public boolean exist(String keyCodeValue) {
		KeyCode keyCode = keyCodeDao.getKeyCodeByKeyCode(keyCodeValue);
		if(keyCode == null){
			return false;
		}
		return true;
	}

	@Override
	public boolean isInUse(String keyCodeValue) {
		KeyCode keyCode = keyCodeDao.getKeyCodeByKeyCode(keyCodeValue);
		if(keyCode != null){
			if(keyCode.getDevice() != null && keyCode.getDevice().getUser() != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public KeyCode getKeyCodeByKeyCode(String keyCode) {
		return keyCodeDao.getKeyCodeByKeyCode(keyCode);
	}

	@Override
	public KeyCode add(KeyCode tmpKeyCode) {
		try {
			return keyCodeDao.addWithResult(tmpKeyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<KeyCode> getAll() {
		List<KeyCode> keyCodes = new ArrayList<KeyCode>();
		try {
			keyCodes = keyCodeDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyCodes;
	}

}
