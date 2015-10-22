package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.LanguageDao;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.service.LanguageManager;

@Service
@Transactional
public class LanguageManagerImpl implements LanguageManager {
	
	@Autowired
	private LanguageDao languageDao;
	
	@Override
	public List<Language> getAllAvailable() {
		return languageDao.getLanguageByStatus(Language.AVAILABLE);
	}

	@Override
	public Language getAvailableLanguageById(int languageId) {
		return languageDao.getLanguageByIdAndStatus(languageId, Language.AVAILABLE);
	}

	@Override
	public boolean updateLanguage(Language language) {
		boolean flag = true;
		try {
			languageDao.update(language);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean add(Language language) {
		boolean flag = true;
		try {
			languageDao.add(language);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
