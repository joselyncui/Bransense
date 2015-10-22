package com.matrix.brainsense.dao;

import java.util.List;

import com.matrix.brainsense.entity.Category;

public interface CategoryDao extends BaseDao<Category> {

	/**
	 * get category by id
	 * @param categoryId the id of category
	 * @return see <code>Category</code>
	 */
	Category getCategoryByIdAndStatus(int categoryId, int status);

	/**
	 * get category by status
	 * @param status the status of category
	 * @return return a list of <code>Category</code>
	 */
	List<Category> getCategoryByStatus(int status);

}
