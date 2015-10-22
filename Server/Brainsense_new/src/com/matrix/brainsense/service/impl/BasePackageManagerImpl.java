package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.BasePackageDao;
import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.service.BasePackageManager;

@Service
@Transactional
public class BasePackageManagerImpl implements BasePackageManager {
	
	@Autowired
	private BasePackageDao basePackageDao;
	
	@Override
	public boolean save(BasePackage basePackage) {
		boolean flag = true;
		try {
			basePackageDao.add(basePackage);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean update(BasePackage basePackage) {
		boolean flag = true;
		try {
			basePackageDao.update(basePackage);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<BasePackage> getBasePackageByStatus(int status) {
		return basePackageDao.getBasePackageByStatus(status);
	}

	@Override
	public BasePackage getByTypeAndLanguage(int languageId, int typeId) {
		return basePackageDao.getByTypeAndLanguage(languageId, typeId);
	}

	@Override
	public List<BasePackage> getBaseByLanguage(int languageId) {
		return basePackageDao.getBaseByLanguage(languageId);
	}

	@Override
	public List<BasePackage> getBaseByType(int typeId) {
		return basePackageDao.getBaseByType(typeId);
	}

}
