package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.Watchdog;

public interface WatchdogManager {

	/**
	 * get watchdog by status
	 * @return return a list of <code>Watchdog</code>
	 */
	List<Watchdog> getWatchdogByStatus(int status);

	/**
	 * get the latest version of watchdog
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getLastWatchdog();

	/**
	 * add watchdog to database
	 * @param watchdog see <code>Watchdog</code>
	 * @return if add success return true, else return false
	 */
	boolean add(Watchdog watchdog);

	/**
	 * get watchdog by available
	 * @param available 
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getByAvailable(int available);

	/**
	 * update watchdog information
	 * @param watchdog see <code>Watchdog</code>
	 * @return if update success return true, else return false
	 */
	boolean update(Watchdog watchdog);

	/**
	 * get available watchdog by id
	 * @param watchdogId the id of watchdog
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getAvailableById(int watchdogId);

	/**
	 * get available watchdog by version name
	 * @param versionName the name of watchdog verwsion
	 * @return see <code>Watchdog</code>
	 */
	Watchdog getAvailableByVersionName(String versionName);

}
