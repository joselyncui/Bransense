package com.matrix.brainsense.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.entity.Country;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.PublicInfo;
import com.matrix.brainsense.entity.Type;
import com.matrix.brainsense.entity.Watchdog;
import com.matrix.brainsense.entity.XBMCInfo;
import com.matrix.brainsense.service.AdminManager;
import com.matrix.brainsense.service.BasePackageManager;
import com.matrix.brainsense.service.CategoryManager;
import com.matrix.brainsense.service.ContentPackageManager;
import com.matrix.brainsense.service.CountryManager;
import com.matrix.brainsense.service.DeviceManager;
import com.matrix.brainsense.service.KeyCodeManager;
import com.matrix.brainsense.service.LanguageManager;
import com.matrix.brainsense.service.PackagePathManager;
import com.matrix.brainsense.service.PublicInfoManager;
import com.matrix.brainsense.service.TypeManager;
import com.matrix.brainsense.service.UserHardwareManager;
import com.matrix.brainsense.service.UserManager;
import com.matrix.brainsense.service.WatchdogManager;
import com.matrix.brainsense.service.XBMCInfoManager;

@Controller
public class Action {

	protected static String TYPE_BASE = "base";
	protected static String TYPE_CONTENT = "content";
	protected static String TYPE_XBMC = "xbmc";
	@Autowired
	protected UserManager userManager;
	@Autowired
	protected UserHardwareManager userHardwareManager;
	@Autowired
	protected DeviceManager deviceManager;
	@Autowired
	protected KeyCodeManager keyCodeManager;
	@Autowired
	protected LanguageManager languageManager;
	@Autowired
	protected CountryManager countryManager;
	@Autowired
	protected CategoryManager categoryManager;
	@Autowired
	protected ContentPackageManager contentPackageManager;
	@Autowired
	protected PackagePathManager packagePathManager;
	@Autowired
	protected BasePackageManager basePackageManager;
	@Autowired
	protected AdminManager adminManager;
	@Autowired
	protected PublicInfoManager publicInfoManager;
	@Autowired
	protected XBMCInfoManager xbmcInfoManager;
	@Autowired
	protected TypeManager typeManager;
	@Autowired
	protected WatchdogManager watchdogManager;

	public List<Language> getAllAvailableLanguage() {
		return languageManager.getAllAvailable();
	}

	/**
	 * get all country in database
	 * 
	 * @return return a list of <code>Country</code>
	 */
	public List<Country> getAllAvailableCountry() {
		return countryManager.getAllAvailable();
	}

	/**
	 * get all category in database
	 * 
	 * @return return a list of <code>Category</code>
	 */
	public List<Category> getAllAvailableCategory() {
		return categoryManager.getAllAvailable();
	}

	/**
	 * get public information by id
	 * 
	 * @param id
	 *            the id of publicInfo
	 * @return <code>PublicInfo</code>
	 */
	protected PublicInfo getPublicInfoById(int id) {
		return publicInfoManager.getPublicInfoById(id);
	}

	/**
	 * get the latest version of xbmc
	 * 
	 * @return see <code>XBMCInfo</code>
	 */
	public XBMCInfo getLastXBMC() {
		return xbmcInfoManager.getLastXBMC();
	}
	
	/**
	 * get base package by type and language
	 * @param language <code>Language</code>
	 * @param type <code>Type</code>
	 * @return see <code>BasePackage</code>
	 */
	public BasePackage getBaseByTypeAndLanguage(Language language, Type type) {
		return basePackageManager.getByTypeAndLanguage(language.getLanguageId(), type.getTypeId());
	}
	
	/**
	 * get base package by type and language
	 * @param language <code>Language</code>
	 * @param type <code>Type</code>
	 * @return see <code>BasePackage</code>
	 */
	public BasePackage getBaseByTypeAndLanguage(int languageId, int typeId) {
		return basePackageManager.getByTypeAndLanguage(languageId, typeId);
	}
	
	/**
	 * get all language that have base package
	 * @return return a list of <code>Language</code>
	 */
	public List<Language> getDownloadableLanguage() {
		List<Language> languages = languageManager.getAllAvailable();
		List<Language> result = new ArrayList<Language>();
		for(Language language : languages){
			if(language.getBasePackages() != null &&
					language.getBasePackages().size() != 0){
				result.add(language);
			}
		}
		return result;
	}

	/**
	 * get all type that have base package
	 * @return return a list of <code>Type</code>
	 */
	public List<Type> getDownloadableType() {
		List<Type> types = typeManager.getAllAvailable();
		List<Type> result = new ArrayList<Type>();
		for(Type type : types){
			if(type.getBasePackages() != null && type.getBasePackages().size() != 0){
				result.add(type);
			}
		}
		return result;
	}
	
	/**
	 * get content package by country and category
	 * 
	 * @param countryId
	 *            country's id
	 * @param categoryId
	 *            category's id
	 * @return return a list of <code>ContentPackage</code>
	 */
	public List<ContentPackage> getPackageByCountryAndCategory(int countryId,
			int categoryId) {
		if (countryId == 0 && categoryId == 0) {
			return contentPackageManager.getAllAvailable();
		} else if (countryId == 0) {
			return contentPackageManager
					.getContentPackageByCategory(categoryId);
		} else if (categoryId == 0) {
			return contentPackageManager.getContentPackageByCountry(countryId);
		} else {
			return contentPackageManager.getContentPackageByCountryAndCategory(
					categoryId, countryId);
		}
	}
	
	public XBMCInfo getAvailableXbmc(){
		return xbmcInfoManager.getXBMCByAvailable(XBMCInfo.AVAILABLE);
	}
	

	/**
	 * get available watchdog
	 * @return see <code>Watchdog</code>
	 */
	public Watchdog getAvailableWatchdog() {
		return watchdogManager.getByAvailable(Watchdog.AVAILABLE);
	}
	
	/**
	 * get downloadable country
	 * @return return a list of <code>Country</code>
	 */
	public List<Country> getDownloadableCountry() {
		List<Country> countries = countryManager.getAllAvailable();
		List<Country> result = new ArrayList<Country>();
		for(Country country : countries){
			if(country.getContentPackages() != null && 
					country.getContentPackages().size() != 0){
				result.add(country);
			}
		}
		return result;
	}
	
	/**
	 * get downloadable category
	 * @return return a list of <code>Category</code>
	 */
	public List<Category> getDownloadableCategory() {
		List<Category> categories = categoryManager.getAllAvailable();
		List<Category> result = new ArrayList<Category>();
		for(Category category : categories){
			if(category.getContentPackages() != null && 
					category.getContentPackages().size() != 0){
				result.add(category);
			}
		}
		return result;
	}


}
