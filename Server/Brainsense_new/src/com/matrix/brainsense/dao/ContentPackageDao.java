package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.ContentPackage;

public interface ContentPackageDao extends BaseDao<ContentPackage> {
	
	/**
	 * get content packages by country id and category id
	 * @param countryId the id of country
	 * @param categoryId the id of category
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByCountryAndCategory(int countryId, int categoryId);
	
	/**
	 * get content packages by country id
	 * @param countryId the id of country
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByCountry(int countryId);
	
	/**
	 * get content packages by category
	 * @param categoryId the id of category
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage>  getContentPackageByCategory(int categoryId);

	/**
	 * get content packages by package name
	 * @param packageName the name of pacakge
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByPackageName(String packageName);

	/**
	 * get content package by country and category's status
	 * @param status the status of country and category
	 * @return return a list of <code>ContentPackage</code>
	 */
	List<ContentPackage> getContentPackageByStatus(int status);

}
