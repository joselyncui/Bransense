package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.UserDao;
import com.matrix.brainsense.entity.User;
import com.matrix.brainsense.service.UserManager;

@Service
@Transactional
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User addUser(User user) {
		try {
			return userDao.addWithResult(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(User user) {
		boolean flag = true;
		try {
			userDao.update(user);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		};
		return flag;
	}

	
	
	
	@Override
	public List<User> getAll() {
		try {
			return userDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void delete(User user) {
		try {
			userDao.delete(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
