package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.CountryDao;
import com.matrix.brainsense.entity.Country;
import com.matrix.brainsense.service.CountryManager;

@Service
@Transactional
public class CountryManagerImpl implements CountryManager {
	
	@Autowired
	private CountryDao countryDao;
	
	@Override
	public List<Country> getAllAvailable() {
		return countryDao.getAvailableByStatus(Country.AVAILABLE);
	}

	@Override
	public Country getAvailableCountryById(int countryId) {
		return countryDao.getCountryByIdAndStatus(countryId, Country.AVAILABLE);
	}

	@Override
	public boolean addCountry(Country country) {
		boolean flag = true;
		try {
			countryDao.add(country);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean update(Country country) {
		boolean flag = true;
		try {
			countryDao.update(country);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
