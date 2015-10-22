package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.CategoryDao;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.service.CategoryManager;

@Service
@Transactional
public class CategoryManagerImpl implements CategoryManager {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<Category> getAllAvailable() {
		return categoryDao.getCategoryByStatus(Category.AVAILABLE);
	}

	@Override
	public Category getAvailableCategoryById(int categoryId) {
		return categoryDao.getCategoryByIdAndStatus(categoryId, Category.AVAILABLE);
	}

	@Override
	public boolean addCategory(Category category) {
		boolean flag = true;
		try {
			categoryDao.add(category);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean update(Category category) {
		boolean flag = true;
		try {
			categoryDao.update(category);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
