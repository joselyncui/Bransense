package com.matrix.brainsense.service;

import java.util.List;

import com.matrix.brainsense.entity.Category;

public interface CategoryManager {
	
	/**
	 * get all category
	 * @return return a list of <code>Category</code>
	 */
	List<Category> getAllAvailable();

	/**
	 * get category by id
	 * @param categoryId the id of category
	 * @return see <code>Category</code>
	 */
	Category getAvailableCategoryById(int categoryId);

	/**
	 * add category to database
	 * @param category <code>Category</code>
	 * @return if add success return true, else return false
	 */
	boolean addCategory(Category category);

	/**
	 * update category to database
	 * @param category <code>Category</code>
	 * @return is update success return true, else return false
	 */
	boolean update(Category category);

}
