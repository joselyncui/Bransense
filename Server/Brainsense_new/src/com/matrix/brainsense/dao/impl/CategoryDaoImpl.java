package com.matrix.brainsense.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.CategoryDao;
import com.matrix.brainsense.entity.Category;

@Repository
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {

	@Override
	public Category getCategoryByIdAndStatus(int categoryId, int status) {
		try {
			return findForObject(getColumnList("categoryId", "status"), getParams(categoryId, status));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Category> getCategoryByStatus(int status) {
		List<Category> result = new ArrayList<Category>();
		try {
			result = findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
