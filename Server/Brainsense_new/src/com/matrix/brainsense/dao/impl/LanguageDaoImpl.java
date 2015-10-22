package com.matrix.brainsense.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.LanguageDao;
import com.matrix.brainsense.entity.Language;

@Repository
public class LanguageDaoImpl extends BaseDaoImpl<Language> implements LanguageDao {

	@Override
	public Language getLanguageByIdAndStatus(int languageId, int status) {
		try {
			return findForObject(getColumnList("languageId", "status"), getParams(languageId, status));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Language> getLanguageByStatus(int status) {
		List<Language> results = new ArrayList<Language>();
		try {
			results = findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
}
