package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.XBMCInfo;

public interface XBMCInfoManager {

	/**
	 * get the latest version of XBMC
	 * @return <code>XBMCInfo</code>
	 */
	XBMCInfo getLastXBMC();

	/**
	 * update XBMC
	 * @param xbmc <code>XBMCInfo</code>
	 * @return if update success return true, else return false
	 */
	boolean update(XBMCInfo xbmc);

	/**
	 * add XBMC to database
	 * @param xbmc <code>XBMCInfo</code>
	 * @return if add success return true, else return false
	 */
	boolean add(XBMCInfo xbmc);

	/**
	 * get XBMC by status
	 * @param status the status of XBMC
	 * @return return a list of <code>XBMCInfo</code>
	 */
	List<XBMCInfo> getXBMCByStatus(int status);

	/**
	 * delete XBMC in database
	 * @param xbmcInfo see <code>XBMCInfo</code>
	 * @return if delete success return true, else return false
	 */
	boolean delete(XBMCInfo xbmcInfo);

	/**
	 * get XBMC by id
	 * @param xbmcId the id of XBMC
	 * @return see <code>XBMCInfo</code>
	 */
	XBMCInfo getXBMCById(int xbmcId);

	/**
	 * get XBMC by available
	 * @param available 
	 * @return see <code>XBMCInfo</code>
	 */
	XBMCInfo getXBMCByAvailable(int available);

}
