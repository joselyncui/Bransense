package com.matrix.brainsense.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.brainsense.dao.XBMCDao;
import com.matrix.brainsense.entity.XBMCInfo;
import com.matrix.brainsense.service.XBMCInfoManager;

@Service
@Transactional
public class XBMCInfoManagerImpl implements XBMCInfoManager {

	@Autowired
	private XBMCDao xbmcInfoDao;
	
	@Override
	public XBMCInfo getLastXBMC() {
		try {
			List<XBMCInfo> xbmcs = xbmcInfoDao.getXBMCByStatus(XBMCInfo.AVAILABLE);
			if(xbmcs != null && xbmcs.size() != 0){
				return xbmcs.get(xbmcs.size() - 1);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean update(XBMCInfo xbmc) {
		boolean flag = true;
		try {
			xbmcInfoDao.update(xbmc);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean add(XBMCInfo xbmc) {
		boolean flag = true;
		try {
			xbmcInfoDao.add(xbmc);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<XBMCInfo> getXBMCByStatus(int status) {
		return xbmcInfoDao.getXBMCByStatus(status);
	}

	@Override
	public boolean delete(XBMCInfo xbmcInfo) {
		try {
			xbmcInfo.setStatus(XBMCInfo.UNAVAILABLE);
			xbmcInfoDao.update(xbmcInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public XBMCInfo getXBMCById(int xbmcId) {
		try {
			return xbmcInfoDao.findById(xbmcId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public XBMCInfo getXBMCByAvailable(int available) {
		return xbmcInfoDao.getXBMCByAvailable(available);
	}

}
