package com.matrix.brainsense.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.WatchdogDao;
import com.matrix.brainsense.entity.Watchdog;

@Repository
public class WatchdogDaoImpl extends BaseDaoImpl<Watchdog> implements
		WatchdogDao {

	@Override
	public List<Watchdog> getWatchdogByStatus(int status) {
		try {
			return findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Watchdog getByAvailable(int available) {
		try {
			return findForObject(getColumnList("status", "available"),  getParams(Watchdog.AVAILABLE, available));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Watchdog getAvailableById(int watchdogId) {
		try {
			return findForObject(getColumnList("status", "watchdogId"), getParams(Watchdog.AVAILABLE, watchdogId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Watchdog getAvailableByVersionName(String versionName) {  
		try {
			return findForObject(getColumnList("status", "versionName"), getParams(Watchdog.AVAILABLE, versionName) );
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
