package com.matrix.brainsense.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.CountryDao;
import com.matrix.brainsense.entity.Country;

@Repository
public class CountryDaoImpl extends BaseDaoImpl<Country> implements CountryDao {

	@Override
	public Country getCountryByIdAndStatus(int countryId, int status) {
		try {
			return findForObject(getColumnList("countryId", "status"), getParams(countryId, status));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Country> getAvailableByStatus(int status) {
		List<Country> result = new ArrayList<Country>();
		try {
			result = findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
