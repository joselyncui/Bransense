package com.matrix.brainsense.dao;

import com.matrix.brainsense.entity.PackagePath;

public interface PackagePathDao extends BaseDao<PackagePath> {

	/**
	 * get the package path
	 * @return see <code>PackagePath</code>
	 */
	PackagePath getPackagePath();

}
