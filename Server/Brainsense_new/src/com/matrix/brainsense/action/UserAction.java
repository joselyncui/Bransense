package com.matrix.brainsense.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.Country;
import com.matrix.brainsense.entity.Device;
import com.matrix.brainsense.entity.KeyCode;
import com.matrix.brainsense.entity.PackagePath;
import com.matrix.brainsense.entity.PublicInfo;
import com.matrix.brainsense.entity.User;
import com.matrix.brainsense.entity.UserHardware;
import com.matrix.brainsense.util.TimeUtil;

@Controller
public class UserAction extends Action {

	/**
	 * set the device to off-line
	 * 
	 * @param macAdd
	 *            the MAC address of device
	 * @return if success return true, else return false
	 */
	public boolean logout(String macAdd) {
		Device device = deviceManager.getDeviceByMac(macAdd);
		if (device == null) {
			return false;
		}
		device.setOnline(Device.OFFLINE);
		return deviceManager.update(device);
	}

	/**
	 * get device's status
	 * 
	 * @param macAdd
	 *            device's mac address
	 * @return return the status of device
	 */
	public int getDeviceStatus(String macAdd) {
		Device device = deviceManager.getDeviceByMac(macAdd);
		int result = -1;
		if (device == null) {
			result = Device.KEYCODE_UNEXIST;
		} else {
			result = device.getStatus();
		}
		return result;
	}

	/**
	 * get user's status
	 * 
	 * @param macAdd
	 *            user's mac address
	 * @param deviceId
	 *            user's device id
	 * @return return the status of user
	 */
	public int getUserStatus(String macAdd, String deviceId) {
		int result = -1;
		Device device = deviceManager.getDeviceByMac(macAdd);
		if (device == null) {
			return User.NEW_USER;
		} else if (!device.getDeviceId().equals(deviceId)) {
			return User.NO_MATCH;
		}
		changeOnline(device);
		User user = device.getUser();
		if (user == null) {
			result = User.NEW_USER;
		} else if (user.getStatusWithBase() == User.UNINSTALL) {
			result = User.WITHOUT_BASIC;
		} else if (user.getStatusWithXbmc() == User.UNINSTALL) {
			result = User.WITHOUT_XBMC;
		} else if (user.getStatusWithContent() == User.UNINSTALL) {
			result = User.WITHOUT_CONTENT;
		} else {
			result = User.INSTALLED_ALL;
		}
		return result;
	}
	
	private void changeOnline(Device device){
		device.setOnline(Device.ONLINE);
		deviceManager.update(device);
	}

	/**
	 * register a new user
	 * 
	 * @param macAdd
	 *            the macAdd of user's device
	 * @param name
	 *            user's name
	 * @param email
	 *            user's email
	 * @param keyCode
	 *            user's key code
	 * @param deviceId
	 *            the device id of user's device
	 * @return return the status of register
	 */
	public int userRegister(String macAdd, String name, String email,
			String keyCode, String deviceId) {
		int result = -1;
		if (!keyCodeManager.exist(keyCode)) {
			result = KeyCode.KEYCODE_UNEXIST;
		} else if (keyCodeManager.isInUse(keyCode)) {
			result = KeyCode.KEYCODE_BE_USED;
		} else {
			KeyCode keyCodeEntity = keyCodeManager.getKeyCodeByKeyCode(keyCode);
			Device device = new Device(deviceId, macAdd, keyCodeEntity);
			User user = new User(device, name, email);
			device.setUser(user);
			device.setDuration(TimeUtil.addDays(user.getDateTime(), Integer
					.valueOf(getPublicInfoById(PublicInfo.ID_DURATION)
							.getValue())));
			userManager.addUser(user);
			result = KeyCode.SUCCESS;
		}
		return result;
	}

	/**
	 * save user's hardware information to database
	 * 
	 * @param macAdd
	 *            the mac address of user's device
	 * @param cpu
	 *            the cpu of user's device
	 * @param os
	 *            the os of user's device
	 * @param memory
	 *            the memory of user's device
	 * @param sdcard
	 *            the sdcard of user's device
	 * @return if save success return true, else return false
	 */
	public boolean saveUserHardware(String macAdd, String cpu, String os,
			String memory, String sdcard) {
		Device device = deviceManager.getDeviceByMac(macAdd);
		if (device == null) {
			return false;
		}
		User user = device.getUser();
		if (user == null) {
			return false;
		}
		UserHardware userHardware = null;
		if (userHardwareManager.getUserHardwareByUserId(user.getUserId()) != null) {
			userHardware = userHardwareManager.getUserHardwareByUserId(user
					.getUserId());
			userHardware.setMacAdd(macAdd);
			userHardware.setCpu(cpu);
			userHardware.setOs(os);
			userHardware.setMemory(memory);
			userHardware.setSdcard(sdcard);
			return userHardwareManager.update(userHardware);
		}
		userHardware = new UserHardware(user, macAdd, cpu, os, memory, sdcard);
		return userHardwareManager.saveUserHardware(userHardware);
	}

	/**
	 * get country by country id
	 * 
	 * @param countryId
	 *            country id
	 * @return see <code>Country</code>
	 */
	public Country getCountryById(int countryId) {
		return countryManager.getAvailableCountryById(countryId);
	}

	/**
	 * get category by category id
	 * 
	 * @param categoryId
	 *            category's id in database
	 * @return see <code>Category</code>
	 */
	public Category getCategoryById(int categoryId) {
		return categoryManager.getAvailableCategoryById(categoryId);
	}


	/**
	 * get the latest package path of base and content
	 * 
	 * @return see <code>PackagePath</code>
	 */
	public PackagePath getPackagePath() {
		return packagePathManager.getPackagePath();
	}

	/**
	 * get the save path of android side
	 * 
	 * @param macAdd
	 *            android device's mac address
	 * @return see <code>User</code>
	 */
	public User getLocalPackagePath(String macAdd) {
		User user = null;
		Device device = deviceManager.getDeviceByMac(macAdd);
		if (device != null) {
			user = device.getUser();
		}
		return user;
	}

	/**
	 * update the save path of android side
	 * 
	 * @param macAdd
	 *            the mac address of device
	 * @param type
	 *            the type should be updated
	 * @return if update success return true, else return false
	 */
	public boolean updateLocalPackagePath(String macAdd, String type) {
		boolean flag = false;
		try {
			Device device = deviceManager.getDeviceByMac(macAdd);
			User user = device.getUser();
			PackagePath packagePath = getPackagePath();
			if (type.equals(TYPE_BASE)) {
				user.setLocalPathBase(packagePath.getBasePath());
			} else if (type.equals(TYPE_CONTENT)) {
				user.setLocalPathContent(packagePath.getContentPath());
			}
			flag = userManager.update(user);
		} catch (NullPointerException e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * update the download status of user
	 * 
	 * @param macAdd
	 *            the mac address of device
	 * @param type
	 *            the type should be updated
	 * @return if update success return true, else return false
	 */
	public boolean updateUserDownloadStatus(String macAdd, String type) {
		boolean flag = true;
		try {
			Device device = deviceManager.getDeviceByMac(macAdd);
			User user = device.getUser();
			if (type.equals(TYPE_BASE)) {
				user.setStatusWithBase(User.INSTALLED);
			} else if (type.equals(TYPE_CONTENT)) {
				user.setStatusWithContent(User.INSTALLED);
			} else if (type.equals(TYPE_XBMC)) {
				user.setStatusWithXbmc(User.INSTALLED);
			}
			flag = userManager.update(user);
		} catch (NullPointerException e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * get available country
	 * 
	 * @return return a list of <code>Country</code>
	 */
	public List<Country> getAvailableCountry() {
		List<Country> countries = countryManager.getAllAvailable();
		List<Country> result = new ArrayList<Country>();
		for (Country country : countries) {
			if (!(country.getContentPackages() == null || country
					.getContentPackages().size() == 0)) {
				result.add(country);
			}
		}
		return result;
	}

	/**
	 * get available category
	 * 
	 * @return return a list of <code>Category</code>
	 */
	public List<Category> getAvailableCategory() {
		List<Category> categories = categoryManager.getAllAvailable();
		List<Category> result = new ArrayList<Category>();
		for (Category category : categories) {
			if (!(category.getContentPackages() == null || category
					.getContentPackages().size() == 0)) {
				result.add(category);
			}
		}
		return result;
	}
	
}
