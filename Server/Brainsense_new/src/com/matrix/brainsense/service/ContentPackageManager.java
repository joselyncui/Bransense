package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.ContentPackage;

public interface ContentPackageManager {

	/**
	 * get all content packages
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getAll();
	
	/**
	 * get content packages by category
	 * @param categoryId the id of category
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByCategory(int categoryId);

	/**
	 * get content packages by country
	 * @param countryId the id of country
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByCountry(int countryId);

	/**
	 * get content packages by country id and category id
	 * @param categoryId the id of category
	 * @param countryId the id of country
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByCountryAndCategory(int categoryId,
			int countryId);

	/**
	 * get content packages by package name
	 * @param packageName the name of package 
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByPackageName(String packageName);

	/**
	 * save content package to database
	 * @param contentPackage <code>ContentPackage</code>
	 * @return if success return true, else return false
	 */
	boolean save(ContentPackage contentPackage);

	/**
	 * get all available content package
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getAllAvailable();

}
