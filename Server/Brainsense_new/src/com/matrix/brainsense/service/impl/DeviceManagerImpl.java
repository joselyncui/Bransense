package com.matrix.brainsense.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.DeviceDao;
import com.matrix.brainsense.entity.Device;
import com.matrix.brainsense.service.DeviceManager;

@Service
@Transactional
public class DeviceManagerImpl implements DeviceManager {
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public Device getDeviceByMac(String macAdd) {
		return deviceDao.getDeviceByMac(macAdd);
	}

	@Override
	public List<Device> getAll() {
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = deviceDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices;
	}

	@Override
	public List<Device> getDeviceByStatus(int status) {
		return deviceDao.getDeviceByStatus(status);
	}

	@Override
	public boolean update(Device device) {
		boolean flag = true;
		try {
			deviceDao.update(device);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
