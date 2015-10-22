package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.Language;

public interface LanguageManager {

	/**
	 * get all languages
	 * @return return a list of <code>Language</code>
	 */
	List<Language> getAllAvailable();

	/**
	 * get language by language id
	 * @param languageId the id of language
	 * @return see <code>Language</code>
	 */
	Language getAvailableLanguageById(int languageId);

	/**
	 * update language name
	 * @param language <code>Language</code>
	 * @return if update success return true, else reutrn false
	 */
	boolean updateLanguage(Language language);

	/**
	 * add language to database
	 * @param language <code>Language</code>
	 * @return if add success return true, else return false
	 */
	boolean add(Language language);

}
