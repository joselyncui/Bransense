package com.matrix.brainsense.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.PublicInfoDao;
import com.matrix.brainsense.entity.PublicInfo;
import com.matrix.brainsense.service.PublicInfoManager;

@Service
@Transactional
public class PublicInfoManagerImpl implements PublicInfoManager {

	@Autowired
	private PublicInfoDao publicInfoDao;
	
	@Override
	public PublicInfo getPublicInfoById(int id) {
		try {
			return publicInfoDao.findById(id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean add(PublicInfo publicInfo) {
		boolean flag = true;
		try {
			publicInfoDao.add(publicInfo);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		};
		return flag;
	}

	@Override
	public boolean update(PublicInfo publicInfo) {
		boolean flag = true;
		try {
			publicInfoDao.update(publicInfo);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
