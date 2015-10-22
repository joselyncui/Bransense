package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.Language;

public interface LanguageDao extends BaseDao<Language> {

	/**
	 * get language by id and status
	 * @param languageId the id of language
	 * @param status the status of language 
	 * @return see <code>Language</code>
	 */
	Language getLanguageByIdAndStatus(int languageId, int status);

	/**
	 * get language by status
	 * @param status the status of language
	 * @return return a list of <code>Language</code>
	 */
	List<Language> getLanguageByStatus(int status);

}
