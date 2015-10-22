package com.matrix.brainsense.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matrix.brainsense.dao.XBMCDao;
import com.matrix.brainsense.entity.XBMCInfo;

@Repository
public class XBMCInfoDaoImpl extends BaseDaoImpl<XBMCInfo> implements XBMCDao {

	@Override
	public List<XBMCInfo> getXBMCByStatus(int status) {
		try {
			return findForList("status", status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public XBMCInfo getXBMCByAvailable(int available) {
		try {
			return findForObject(getColumnList("status", "available"), getParams(XBMCInfo.AVAILABLE, available));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
