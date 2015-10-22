package com.matrix.brainsense.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.matrix.brainsense.entity.Admin;
import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.entity.Country;
import com.matrix.brainsense.entity.Device;
import com.matrix.brainsense.entity.KeyCode;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.PackagePath;
import com.matrix.brainsense.entity.PublicInfo;
import com.matrix.brainsense.entity.Type;
import com.matrix.brainsense.entity.User;
import com.matrix.brainsense.entity.Watchdog;
import com.matrix.brainsense.entity.XBMCInfo;

@Controller
public class AdminAction extends Action {

	/**
	 * get all device
	 * 
	 * @return return a list of <code>Device</code>
	 */
	public List<Device> getAllDevice() {
		return deviceManager.getAll();
	}

	/**
	 * get the device which the state is waiting
	 * 
	 * @return return a list of <code>Device</code>
	 */
	public List<Device> getWaittingDevice() {
		return deviceManager.getDeviceByStatus(Device.WAITING);
	}

	/**
	 * get the device which the state is approve
	 * 
	 * @return return a list of <code>Device</code>
	 */
	public List<Device> getApproveDevice() {
		return deviceManager.getDeviceByStatus(Device.APPROVED);
	}

	/**
	 * get the device which the state is reject
	 * 
	 * @return return a list of <code>Device</code>
	 */
	public List<Device> getRejectedDevice() {
		return deviceManager.getDeviceByStatus(Device.DENIED);
	}

	/**
	 * get the device which the statue is timeout
	 * 
	 * @return return a list of <code>Device</code>
	 */
	public List<Device> getTimeoutDevice() {
		return deviceManager.getDeviceByStatus(Device.OVER_TIME);
	}

	/**
	 * update the status of device
	 * 
	 * @param macId
	 *            the MAC address of device
	 * @param status
	 *            the status you want to update
	 * @return if update success return true, else return false
	 */
	public boolean updateDeviceStatus(String macId, int status) {
		boolean flag = false;
		Device device = deviceManager.getDeviceByMac(macId);
		if (device != null) {
			device.setStatus(status);
			if (deviceManager.update(device)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * get the language which is available
	 * 
	 * @param languageId
	 *            the id of language
	 * @return see <code>Language</code>
	 */
	public Language getAvailableLanguageById(int languageId) {
		return languageManager.getAvailableLanguageById(languageId);
	}
	
	/**
	 * get available language in database
	 * 
	 * @return return a list of <code>Language</code>
	 */
	public List<Language> getAvailableLanguage() {
		List<Language> languages = languageManager.getAllAvailable();
		List<Language> results = new ArrayList<Language>();
		if (languages != null) {
			for (Language language : languages) {
				if (language.getBasePackages() != null) {
					results.add(language);
				}
			}
		}
		return results;
	}
	
	public boolean saveBaseToDB(BasePackage basePackage){
		BasePackage dbPackage = getBaseByTypeAndLanguage(basePackage.getLanguage(), basePackage.getType());
		if(dbPackage == null){
			return basePackageManager.save(basePackage);
		} else {
			basePackage.setBaseId(dbPackage.getBaseId());
			return basePackageManager.update(basePackage);
		}
	}

	private String formatKeyCode(String keyCode) {
		String result = "";
		for (int i = 0; i < 8 - keyCode.length(); i++) {
			result += "0";
		}
		return result + keyCode;
	}

	private String getKeyCode() {
		List<KeyCode> keyCodes = keyCodeManager.getAll();
		KeyCode keyCode = null;
		if (keyCodes != null && keyCodes.size() != 0) {
			keyCode = keyCodes.get(keyCodes.size() - 1);
			String oldKeyCode = keyCode.getKeyCodeId();
			String newKeyCode = String.valueOf(Integer.valueOf(oldKeyCode
					.split("-")[0]) + 1);
			return formatKeyCode(newKeyCode) + "-1111-1111-1111";
		} else {
			return "00000001-1111-1111-1111";
		}
	}

	/**
	 * generate key code
	 * 
	 * @param codeNum
	 *            the number you want to create
	 * @return return a list of <code>KeyCode</code>
	 */
	public List<KeyCode> generateKeyCode(int codeNum) {
		List<KeyCode> keyCodes = new ArrayList<KeyCode>();
		for (int i = 0; i < codeNum; i++) {
			KeyCode tmpKeyCode = new KeyCode(getKeyCode());
			tmpKeyCode = keyCodeManager.add(tmpKeyCode);
			if (tmpKeyCode == null) {
				break;
			}
			keyCodes.add(tmpKeyCode);
		}
		return keyCodes;
	}

	/**
	 * check package name can use or not
	 * 
	 * @param packageName
	 *            the name of package
	 * @param countryId
	 *            the id of country
	 * @param categoryId
	 *            the id of category
	 * @return if can use return true, else return false
	 */
	public boolean checkPackageName(String packageName, int countryId,
			int categoryId) {
		List<ContentPackage> contentPackages = contentPackageManager
				.getContentPackageByPackageName(packageName);
		for (ContentPackage contentPackage : contentPackages) {
			if (contentPackage.getCountry().getCountryId() == countryId
					&& contentPackage.getCategory().getCategoryId() == categoryId) {
				return false;
			}
		}
		return true;
	}

	/**
	 * get country by id
	 * 
	 * @param countryId
	 *            the id of country
	 * @return see <code>Country</code>
	 */
	public Country getCountryById(int countryId) {
		return countryManager.getAvailableCountryById(countryId);
	}

	/**
	 * get category by id
	 * 
	 * @param categoryId
	 *            the id of category
	 * @return see <code>Category</code>
	 */
	public Category getCategoryById(int categoryId) {
		return categoryManager.getAvailableCategoryById(categoryId);
	}
	
	public boolean saveContentToDB(ContentPackage contentPackage) {
		return contentPackageManager.save(contentPackage);
	}

	/**
	 * check the user name and password can login or not
	 * 
	 * @param userName
	 * @param password
	 * @return if can login return true, else return false
	 */
	public boolean canLogin(String userName, String password) {
		Admin admin = adminManager.getAdminByName(userName);
		if (admin != null) {
			if (admin.getName().equals(userName)
					&& admin.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * update language information
	 * 
	 * @param languageId
	 *            the id of language
	 * @param propertiesName
	 *            the name of language
	 * @return if update success return true, else return false
	 */
	public boolean updateLanguage(int languageId, String propertiesName) {
		Language language = languageManager
				.getAvailableLanguageById(languageId);
		language.setName(propertiesName);
		return languageManager.updateLanguage(language);
	}

	/**
	 * update country
	 * 
	 * @param propertiesId
	 * @param propertiesName
	 * @return if success return true, else return false
	 */
	public boolean updateCountry(int propertiesId, String propertiesName) {
		Country country = countryManager.getAvailableCountryById(propertiesId);
		country.setName(propertiesName);
		return countryManager.update(country);
	}

	/**
	 * update category
	 * 
	 * @param propertiesId
	 * @param propertiesName
	 * @return if update success return true, else return false
	 */
	public boolean updateCategory(int propertiesId, String propertiesName) {
		Category category = categoryManager
				.getAvailableCategoryById(propertiesId);
		category.setName(propertiesName);
		return categoryManager.update(category);
	}

	/**
	 * add language to database
	 * 
	 * @param addPropertiesName
	 * @return if add success return true, else return false
	 */
	public boolean addLanguage(String addPropertiesName) {
		Language language = new Language(addPropertiesName);
		return languageManager.add(language);
	}

	/**
	 * add category to database
	 * 
	 * @param addPropertiesName
	 * @return if add success return true, else return false
	 */
	public boolean addCategory(String addPropertiesName) {
		Category category = new Category(addPropertiesName);
		return categoryManager.addCategory(category);
	}

	/**
	 * add country to database
	 * 
	 * @param addPropertiesName
	 * @return if add success return true, else return false
	 */
	public boolean addCountry(String addPropertiesName) {
		Country country = new Country(addPropertiesName);
		return countryManager.addCountry(country);
	}

	/**
	 * delete language
	 * 
	 * @param languageId
	 *            the id of language
	 * @return if delete success return true, else return false
	 */
	public boolean deleteLanguage(int languageId) {
		Language language = languageManager
				.getAvailableLanguageById(languageId);
		language.setStatus(Language.UNAVAILABLE);
		return languageManager.updateLanguage(language);
	}

	/**
	 * delete category
	 * 
	 * @param categoryId
	 *            the id of category
	 * @return if delete success return true, else return false
	 */
	public boolean deleteCategory(int categoryId) {
		Category category = categoryManager
				.getAvailableCategoryById(categoryId);
		category.setStatus(Language.UNAVAILABLE);
		return categoryManager.update(category);
	}

	/**
	 * delete country
	 * 
	 * @param countryId
	 *            the id of country
	 * @return if delete success return true, else return false
	 */
	public boolean deleteCountry(int countryId) {
		Country country = countryManager.getAvailableCountryById(countryId);
		country.setStatus(Language.UNAVAILABLE);
		return countryManager.update(country);
	}

	/**
	 * update XBMC
	 * 
	 * @param xbmc
	 *            see <code>XBMCInfo</code>
	 * @return if update success return true, else return false
	 */
	public boolean updateXBMC(XBMCInfo xbmc) {
		return xbmcInfoManager.update(xbmc);
	}

	/**
	 * add XBMC to database
	 * 
	 * @param xbmc
	 *            see <code>XBMCInfo</code>
	 * @return if add success return true, else return false
	 */
	public boolean addXBMC(XBMCInfo xbmc) {
		return xbmcInfoManager.add(xbmc);
	}

	/**
	 * get all available XBMC
	 * 
	 * @return return a list of <code>XBMCInfo</code>
	 */
	public List<XBMCInfo> getAllAvailableXBMC() {
		return xbmcInfoManager.getXBMCByStatus(XBMCInfo.AVAILABLE);
	}

	/**
	 * delete XBMC
	 * 
	 * @param xbmcInfo
	 *            see <code>XBMCInfo</code>
	 * @return if delete success return true, else return false
	 */
	public boolean deleteXBMC(XBMCInfo xbmcInfo) {
		return xbmcInfoManager.delete(xbmcInfo);
	}

	/**
	 * get XBMC by id
	 * 
	 * @param xbmcId
	 *            the id of XBMC
	 * @return see <code>XBMCInfo</code>
	 */
	public XBMCInfo getXBMCById(int xbmcId) {
		return xbmcInfoManager.getXBMCById(xbmcId);
	}

	/**
	 * get the days should be waiting
	 * 
	 * @return see <code>PublicInfo</code>
	 */
	public PublicInfo getWaitingDays() {
		return publicInfoManager.getPublicInfoById(PublicInfo.ID_DURATION);
	}

	/**
	 * add public to database
	 * 
	 * @param publicInfo
	 *            <code>PublicInfo</code>
	 * @return if add success return true, else return false
	 */
	public boolean addPublic(PublicInfo publicInfo) {
		return publicInfoManager.add(publicInfo);
	}

	/**
	 * update public information to database
	 * 
	 * @param publicInfo
	 *            <code>PublicInfo</code>
	 * @return if update success return true, else return false
	 */
	public boolean updatePublic(PublicInfo publicInfo) {
		return publicInfoManager.update(publicInfo);
	}

	/**
	 * get the package path
	 * 
	 * @return see <code>PackagePath</code>
	 */
	public PackagePath getPath() {
		List<PackagePath> paths = packagePathManager.getAll();
		if (paths == null || paths.size() == 0) {
			return null;
		}
		return paths.get(0);
	}

	/**
	 * add path to database
	 * 
	 * @param basePath
	 *            the path of base package
	 * @param contentPath
	 *            the path of content package
	 * @return if add success return true, else return false
	 */
	public boolean addPath(String basePath, String contentPath) {
		PackagePath packagePath = new PackagePath(basePath, contentPath);
		return packagePathManager.add(packagePath);
	}

	/**
	 * update package path
	 * 
	 * @param basePath
	 *            the path of base package
	 * @param contentPath
	 *            the path of content package
	 * @return if update success return true, else return false
	 */
	public boolean updatePath(String basePath, String contentPath) {
		PackagePath packagePath = getPath();
		if (packagePath == null) {
			return addPath(basePath, contentPath);
		}
		packagePath.setBasePath(basePath);
		packagePath.setContentPath(contentPath);
		return packagePathManager.update(packagePath);
	}

	/**
	 * get available content packages
	 * @return return a list of <code>ContentPackage</code>
	 */
	public List<ContentPackage> getAvailableContentPackage() {
		return contentPackageManager.getAllAvailable();
	}

	/**
	 * get available base package
	 * @return return a list of <code>BasePackage</code>
	 */
	public List<BasePackage> getAvailableBasePackage() {
		return basePackageManager.getBasePackageByStatus(BasePackage.STATUS_AVAILABLE);
	}
	
	/**
	 * get all available type
	 * @return return a list of <code>Type</code>
	 */
	public List<Type> getAllAvailableType() {
		return typeManager.getAllAvailable();
	}

	/**
	 * get available type by id
	 * @param typeId
	 * @return
	 */
	public Type getAvailableTypeById(int typeId) {
		return typeManager.getAvailableTypeById(typeId);
	}
	
	public boolean updateType(int typeId, String typeName) {
		Type type = typeManager.getAvailableTypeById(typeId);
		type.setName(typeName);
		return typeManager.update(type);
	}
	

	public boolean deleteType(int typeId) {
		Type type = typeManager.getAvailableTypeById(typeId);
		type.setStatus(Type.STATUS_UNAVAILABLE);
		return typeManager.update(type);
	}
	
	public boolean addType(String typeName) {
		Type type = new Type(typeName);
		return typeManager.add(type);
	}

	public List<BasePackage> getBaseByOption(int typeId,
			int languageId) {
		if(typeId == 0 && languageId == 0){
			return getAvailableBasePackage();
		} else if (typeId == 0){
			return basePackageManager.getBaseByLanguage(languageId);
		} else if (languageId == 0){
			return basePackageManager.getBaseByType(typeId);
		} 
		BasePackage basePackage = getBaseByTypeAndLanguage(languageId, typeId);
		if(basePackage == null){
			return null;
		}
		List<BasePackage> basePackges = new ArrayList<BasePackage>();
		basePackges.add(basePackage);
		return basePackges;
	}

	public List<Watchdog> getAllAvailableWatchdog() {
		return watchdogManager.getWatchdogByStatus(Watchdog.AVAILABLE);
	}
	
	public Watchdog getWatchdogByVersionName(String versionName) {
		return watchdogManager.getAvailableByVersionName(versionName);
	}

	/**
	 * get the lastest version of watchdog
	 * @return see <code>Watchdog</code>
	 */
	public Watchdog getLastWatchdog() {
		return watchdogManager.getLastWatchdog();
	}

	/**
	 * add watchdog to database
	 * @param newWatchdog see <code>Watchdog</code>
	 * @return
	 */
	public boolean addWatchdog(Watchdog watchdog) {
		return watchdogManager.add(watchdog);
	}
	
	/**
	 * update watchdog information
	 * @param unWatchdog see <code>Watchdog</code>
	 * @return if update success return true, else return false
	 */
	public boolean updateWatchdog(Watchdog watchdog) {
		return watchdogManager.update(watchdog);
	}

	/**
	 * get available watchdog by id
	 * @param watchdogId
	 * @return
	 */
	public Watchdog getAvailableWatchdogById(int watchdogId) {
		return watchdogManager.getAvailableById(watchdogId);
	}
	
	
	
	
	/**
	 * 
	 * 
	 * For test code
	 * 
	 * 
	 * 
	 */

	public List<User> getAllUser() {
		return userManager.getAll();
	}

	public void resetUser(String macId) {
		Device device = deviceManager.getDeviceByMac(macId);
		User user = device.getUser();
		user.setStatusWithBase(User.UNINSTALL);
		user.setStatusWithXbmc(User.UNINSTALL);
		user.setStatusWithContent(User.UNINSTALL);
		userManager.update(user);
	}

	public void deleteUser(String macId) {
		Device device = deviceManager.getDeviceByMac(macId);
		User user = device.getUser();
		userManager.delete(user);
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}
