package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.PackagePathDao;
import com.matrix.brainsense.entity.PackagePath;
import com.matrix.brainsense.service.PackagePathManager;

@Service
@Transactional
public class PackagePathManagerImpl implements PackagePathManager {
	
	@Autowired
	private PackagePathDao packagePathDao;
	
	@Override
	public PackagePath getPackagePath() {
		return packagePathDao.getPackagePath();
	}

	@Override
	public List<PackagePath> getAll() {
		try {
			return packagePathDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean add(PackagePath packagePath) {
		boolean flag = true;
		try {
			packagePathDao.add(packagePath);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean update(PackagePath packagePath) {
		boolean flag = true;
		try {
			packagePathDao.update(packagePath);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
