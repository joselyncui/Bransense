package com.matrix.brainsense.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.DeviceDao;
import com.matrix.brainsense.entity.Device;

@Repository
public class DeviceDaoImpl extends BaseDaoImpl<Device> implements DeviceDao {
	
	@Override
	public Device getDeviceByMac(String macAdd) {
		try {
			return findForObject("macId", macAdd);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Device> getDeviceByStatus(int status) {
		try {
			return findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
