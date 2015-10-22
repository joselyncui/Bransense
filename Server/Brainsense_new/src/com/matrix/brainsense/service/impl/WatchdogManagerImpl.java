package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.WatchdogDao;
import com.matrix.brainsense.entity.Watchdog;
import com.matrix.brainsense.service.WatchdogManager;

@Service
@Transactional
public class WatchdogManagerImpl implements WatchdogManager {

	@Autowired
	private WatchdogDao watchdogDao;
	
	@Override
	public List<Watchdog> getWatchdogByStatus(int status) {
		return watchdogDao.getWatchdogByStatus(status);
	}

	@Override
	public Watchdog getLastWatchdog() {
		List<Watchdog> watchdogs = watchdogDao.getWatchdogByStatus(Watchdog.AVAILABLE);
		if(watchdogs != null && watchdogs.size() != 0){
			return watchdogs.get(watchdogs.size() - 1);
		}
		return null;
	}

	@Override
	public boolean add(Watchdog watchdog) {
		boolean flag = true;
		try {
			watchdogDao.add(watchdog);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public Watchdog getByAvailable(int available) {
		return watchdogDao.getByAvailable(available);
	}

	@Override
	public boolean update(Watchdog watchdog) {
		boolean flag = true;
		try {
			watchdogDao.update(watchdog);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public Watchdog getAvailableById(int watchdogId) {
		return watchdogDao.getAvailableById(watchdogId);
	}

	@Override
	public Watchdog getAvailableByVersionName(String versionName) {
		return watchdogDao.getAvailableByVersionName(versionName);
	}

}
