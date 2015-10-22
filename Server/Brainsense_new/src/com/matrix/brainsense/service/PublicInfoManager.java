package com.matrix.brainsense.service;

import com.matrix.brainsense.entity.PublicInfo;

public interface PublicInfoManager {

	/**
	 * get public information by id
	 * @param id the id of public information
	 * @return if success return <code>PublicInfo</code>, else return null
	 */
	PublicInfo getPublicInfoById(int id);

	/**
	 * add publicInfo to database
	 * @param publicInfo see <code>PublicInfo</code>
	 * @return if add success return true, else reutrn false
	 */
	boolean add(PublicInfo publicInfo);

	/**
	 * update publicInfo to database
	 * @param publicInfo see <code>PublicInfo</code>
	 * @return if update success return true, else return false
	 */
	boolean update(PublicInfo publicInfo);

}
