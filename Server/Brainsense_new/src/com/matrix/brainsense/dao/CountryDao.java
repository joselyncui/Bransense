package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.Country;

public interface CountryDao extends BaseDao<Country> {
	
	/**
	 * get country by country id
	 * @param countryId the id of country
	 * @return see <code>Country</code>
	 */
	Country getCountryByIdAndStatus(int countryId, int status);

	/**
	 * get available country by status
	 * @param status the status of country
	 * @return return a list of <code>Country</code>
	 */
	List<Country> getAvailableByStatus(int status);

}
