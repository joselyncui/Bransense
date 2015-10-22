package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.BasePackage;

public interface BasePackageDao extends BaseDao<BasePackage> {

	/**
	 * get base package by type and language
	 * @param languageId the id of language
	 * @param typeId the id of type
	 * @return see <code>BasePackage</code>
	 */
	BasePackage getByTypeAndLanguage(int languageId, int typeId);

	/**
	 * get base package by language
	 * @param languageId the id of language
	 * @return return a list of <code>BasePackage</code>
	 */
	List<BasePackage> getBaseByLanguage(int languageId);

	/**
	 * get base package by language
	 * @param typeId the id of type
	 * @return return a list of <code>BasePackage</code>
	 */
	List<BasePackage> getBaseByType(int typeId);

	/**
	 * get base package by status
	 * @param status the status of base package
	 * @return return a list of <code>BasePackage</code>
	 */
	List<BasePackage> getBasePackageByStatus(int status);

}
