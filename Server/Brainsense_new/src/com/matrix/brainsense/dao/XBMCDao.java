package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.XBMCInfo;

public interface XBMCDao extends BaseDao<XBMCInfo> {

	/**
	 * get XBMC by status
	 * @param statusAvailable the status of XBMC
	 * @return return a list of <code>XBMCInfo</code>
	 */
	List<XBMCInfo> getXBMCByStatus(int statusAvailable);

	/**
	 * get XBMC by available
	 * @param available 
	 * @return see <code>XBMCInfo</code>
	 */
	XBMCInfo getXBMCByAvailable(int available);

}
