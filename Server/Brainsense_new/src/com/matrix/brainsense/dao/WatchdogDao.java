package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.Watchdog;

public interface WatchdogDao extends BaseDao<Watchdog> {

	/**
	 * get watch dog by status
	 * @param status the status of watchdog
	 * @return return a list of <code>Watchdog</code>
	 */
	List<Watchdog> getWatchdogByStatus(int status);

	/**
	 * get watchdog by available
	 * @param available
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getByAvailable(int available);

	/**
	 * get available watchdog by id
	 * @param watchdogId the id of watchdog
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getAvailableById(int watchdogId);

	/**
	 * get available watchdog by version name
	 * @param versionName the name of version
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getAvailableByVersionName(String versionName);

}
