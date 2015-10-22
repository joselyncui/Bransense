package com.matrix.brainsense.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.ContentPackageDao;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.service.ContentPackageManager;

@Service
@Transactional
public class ContentPackageManagerImpl implements ContentPackageManager {
	
	@Autowired
	private ContentPackageDao contentPackageDao;
	
	@Override
	public List<ContentPackage> getAll() {
		List<ContentPackage> result = new ArrayList<ContentPackage>();
		try {
			result = contentPackageDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		};
		return result;
	}

	@Override
	public List<ContentPackage> getContentPackageByCategory(int categoryId) {
		return contentPackageDao.getContentPackageByCategory(categoryId);
	}

	@Override
	public List<ContentPackage> getContentPackageByCountry(int countryId) {
		return contentPackageDao.getContentPackageByCountry(countryId);
	}

	@Override
	public List<ContentPackage> getContentPackageByCountryAndCategory(
			int categoryId, int countryId) {
		return contentPackageDao.getContentPackageByCountryAndCategory(countryId, categoryId);
	}

	@Override
	public List<ContentPackage> getContentPackageByPackageName(
			String packageName) {
		return contentPackageDao.getContentPackageByPackageName(packageName);
	}

	@Override
	public boolean save(ContentPackage contentPackage) {
		boolean flag = true;
		try {
			contentPackageDao.add(contentPackage);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<ContentPackage> getAllAvailable() {
		return contentPackageDao.getContentPackageByStatus(ContentPackage.AVAILABLE);
	}

}
