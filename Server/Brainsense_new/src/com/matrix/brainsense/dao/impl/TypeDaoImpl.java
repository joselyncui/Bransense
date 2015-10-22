package com.matrix.brainsense.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.TypeDao;
import com.matrix.brainsense.entity.Type;

@Repository
public class TypeDaoImpl  extends BaseDaoImpl<Type>  implements TypeDao {

	@Override
	public List<Type> getTypeByStatus(int status) {
		try {
			return findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Type getTypeByIdAndStatus(int typeId, int status) {
		try {
			return findForObject(getColumnList("typeId", "status"),
					getParams(typeId, status));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
