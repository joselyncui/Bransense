package com.matrix.brainsense.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.BasePackageDao;
import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.Type;

@Repository
public class BasePackageDaoImpl extends BaseDaoImpl<BasePackage> implements BasePackageDao {

	@Override
	public BasePackage getByTypeAndLanguage(int languageId, int typeId) {
		try {
			return findForObject(getColumnList("language.languageId", "type.typeId"), 
					getParams(languageId, typeId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BasePackage> getBaseByLanguage(int languageId) {
		try {
			return findForList(getColumnList("language.status", "type.status", "status", "language.languageId"),
					getParams(Language.AVAILABLE, Type.STATUS_AVAILABLE, BasePackage.STATUS_AVAILABLE, languageId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BasePackage> getBaseByType(int typeId) {
		try {
			return findForList(getColumnList("type.status", "language.status", "status", "type.typeId"),
					getParams(Type.STATUS_AVAILABLE, Language.AVAILABLE, BasePackage.STATUS_AVAILABLE, typeId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BasePackage> getBasePackageByStatus(int status) {
		try {
			return findForList(getColumnList("language.status", "type.status", "status"), getParams(status, status, status));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
