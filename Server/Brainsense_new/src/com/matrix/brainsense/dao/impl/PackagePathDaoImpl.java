package com.matrix.brainsense.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.PackagePathDao;
import com.matrix.brainsense.entity.PackagePath;

@Repository
public class PackagePathDaoImpl extends BaseDaoImpl<PackagePath> implements
		PackagePathDao {

	@Override
	public PackagePath getPackagePath() {
		List<PackagePath> packagePaths;
		try {
			packagePaths = findAll();
			if(packagePaths == null || packagePaths.size() == 0){
				return null;
			}
			return packagePaths.get(packagePaths.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
