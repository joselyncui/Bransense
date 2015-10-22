package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.TypeDao;
import com.matrix.brainsense.entity.Type;
import com.matrix.brainsense.service.TypeManager;

@Service
@Transactional
public class TypeManagerImpl implements TypeManager {

	@Autowired
	private TypeDao typeDao;
	
	@Override
	public List<Type> getAllAvailable() {
		return typeDao.getTypeByStatus(Type.STATUS_AVAILABLE);
	}

	@Override
	public Type getAvailableTypeById(int typeId) {
		return typeDao.getTypeByIdAndStatus(typeId, Type.STATUS_AVAILABLE);
	}

	@Override
	public boolean update(Type type) {
		boolean flag = true;
		try {
			typeDao.update(type);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean add(Type type) {
		boolean flag = true;
		try {
			typeDao.add(type);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
}
