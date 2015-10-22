package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.BasePackage;

public interface BasePackageManager {

	/**
	 * save base package to database 
	 * @param basePackage <code>BasePackage</code>
	 */
	boolean save(BasePackage basePackage);

	/**
	 * update base package to database
	 * @param basePackage <code>BasePackage</code>
	 * @return if update success return true, else return false
	 */
	boolean update(BasePackage basePackage);

	/**
	 * get available base package 
	 * @param status the status of base package
	 * @return return a list of <code>BasePackage</code>
	 */
	List<BasePackage> getBasePackageByStatus(int status);

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
	 * get base package by type
	 * @param typeId the id of type
	 * @return return a list of <code>BasePackage</code>
	 */
	List<BasePackage> getBaseByType(int typeId);

}
