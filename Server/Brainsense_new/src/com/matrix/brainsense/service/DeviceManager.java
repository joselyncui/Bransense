package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.Device;

public interface DeviceManager {

	/**
	 * get device by the MAC address of device
	 * @param macAdd the MAC address of device
	 * @return see <code>Device</code>
	 */
	Device getDeviceByMac(String macAdd);

	/**
	 * get all device
	 * @return return a list of <code>Device</code>
	 */
	List<Device> getAll();

	/**
	 * update device information in database
	 * @param device <code>device</code>
	 */
	boolean update(Device device);

	/**
	 * get device by status
	 * @param status 
	 * @return return a list of <code>Device</code>
	 */
	List<Device> getDeviceByStatus(int status);

}
