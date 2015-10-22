package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.Country;

public interface CountryManager {

	/**
	 * get all country
	 * @return reutnr a list of <code>Country</code>
	 */
	List<Country> getAllAvailable();

	/**
	 * get country by country id
	 * @param countryId the id of country
	 * @return see <code>Country</code>
	 */
	Country getAvailableCountryById(int countryId);

	/**
	 * add country to database
	 * @param country <code>Country</code>
	 * @return if add success return true, else return false
	 */
	boolean addCountry(Country country);

	/**
	 * update country to database
	 * @param country <code>Country</code>
	 * @return if update success return true, else return false
	 */
	boolean update(Country country);

}
