package com.matrix.brainsense.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.ContentPackageDao;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.entity.Country;

@Repository
public class ContentPackageDaoImpl extends BaseDaoImpl<ContentPackage>
		implements ContentPackageDao {

	@Override
	public List<ContentPackage> getContentPackageByCountryAndCategory(
			int countryId, int categoryId) {
		try {
			return findForList(
					getColumnList("country.countryId", "category.categoryId", "country.status", "category.status"),
					getParams(countryId, categoryId, Country.AVAILABLE, Category.AVAILABLE));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ContentPackage> getContentPackageByCountry(int countryId) {
		try {
			return findForList(
					getColumnList("country.countryId", "category.status", "country.status"),
					getParams(countryId, Category.AVAILABLE, Country.AVAILABLE));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ContentPackage> getContentPackageByCategory(int categoryId) {
		try {
			return findForList(
					getColumnList("category.categoryId", "country.status", "category.status"),
					getParams(categoryId, Category.AVAILABLE, Country.AVAILABLE));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ContentPackage> getContentPackageByPackageName(
			String packageName) {
		List<ContentPackage> contentPackages = new ArrayList<ContentPackage>();
		try {
			contentPackages = findForList("name", packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentPackages;
	}

	@Override
	public List<ContentPackage> getContentPackageByStatus(int status) {
		List<ContentPackage> result = new ArrayList<ContentPackage>();
		try {
			result = findForList(
					getColumnList("country.status", "category.status", "status"),
					getParams(status, status, status));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
