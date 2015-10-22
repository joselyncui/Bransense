package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.PackagePath;

public interface PackagePathManager {

	/**
	 * get the latest package path of base and content
	 * @return see <code>PackagePath</code>
	 */
	PackagePath getPackagePath();

	/**
	 * get all path in package path
	 * @return return a list of <code>PackagePath</code>
	 */
	List<PackagePath> getAll();

	/**
	 * add package path to database
	 * @param packagePath the path of package
	 * @return if add success return true, else return false
	 */
	boolean add(PackagePath packagePath);

	/**
	 * update package path to database
	 * @param packagePath the package of package
	 * @return if success return true, else return false
	 */
	boolean update(PackagePath packagePath);

}
