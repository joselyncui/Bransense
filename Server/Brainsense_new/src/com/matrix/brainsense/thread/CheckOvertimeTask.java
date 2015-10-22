package com.matrix.brainsense.thread;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.Device;
import com.matrix.brainsense.util.TimeUtil;

@Component
public class CheckOvertimeTask {
	
	@Autowired
	private AdminAction action;
	
	@Scheduled(fixedDelay = 86400)
	public void check(){
		List<Device> devices = action.getWaittingDevice();
		if(devices == null){
			return;
		}
		for(int i=0; i<devices.size(); i++){
			String currentTime = TimeUtil.convert(new Date());
			String compareTime = devices.get(i).getDuration();
			if(TimeUtil.isOvertime(currentTime, compareTime)){
				action.updateDeviceStatus(devices.get(i).getMacId(), Device.OVER_TIME);
			}
		}
	}

}
