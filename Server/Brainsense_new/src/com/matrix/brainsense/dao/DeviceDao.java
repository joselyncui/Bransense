package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.Device;

public interface DeviceDao extends BaseDao<Device> {

	/**
	 * get device by MAC address
	 * @param macAdd the MAC address of device
	 * @return see <code>Device</code>
	 */
	Device getDeviceByMac(String macAdd);

	/**
	 * get device by status
	 * @param status waiting, approved, rejected
	 * @return return a list of <code>Device</code>
	 */
	List<Device> getDeviceByStatus(int status);

}
