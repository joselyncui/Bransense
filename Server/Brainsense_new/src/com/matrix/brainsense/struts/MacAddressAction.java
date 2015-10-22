package com.matrix.brainsense.struts;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.Device;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect" ,location = "macAddressManager"),
	@Result(name = "invalid.token", location = "initMac"),
	@Result(name = "approve", type = "redirect" ,location = "getApprove"),
	@Result(name = "rejected", type = "redirect" ,location = "getRejected"),
	@Result(name = "waiting", type = "redirect" ,location = "macAddressManager"),
	@Result(name = "timeout", type = "redirect" ,location = "getTimeout")} )
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class MacAddressAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static String APPROVE = "approve";
	private static String REJECTED = "rejected";
	private static String WAITING = "waiting";
	private static String TIME_OUT = "timeout";
	private static String STATUS_WAITING = "1";
	private static String STATUS_APPROVE = "2";
	private static String STATUS_REJECTED = "3";
	private static String STATUS_TIME_OUT = "4";
	
	@Autowired
	private AdminAction adminAction;
	
	private List<Device> devices = new ArrayList<Device>();
	private String macId;
	private String select;
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	
	private String getReturnValue(){
		if(this.select.equals(STATUS_WAITING)){
			return WAITING;
		} else if(this.select.equals(STATUS_APPROVE)) {
			return APPROVE;
		} else if(this.select.equals(STATUS_REJECTED)) {
			return REJECTED;
		} else if(this.select.equals(STATUS_TIME_OUT)){
			return TIME_OUT;
		}
		return ERROR;
	}
	
	@Action(value = "macAddressManager", results = { @Result(name = "success", location = "/WEB-INF/macAddressManager.jsp"),
			@Result(name = "error", location = "/WEB-INF/macAddressManager.jsp") })
	public String getWaitingMac(){
		this.select = "1";
		devices = adminAction.getWaittingDevice();
		if(devices == null){
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "getApprove", results = { @Result(name = "success", location = "/WEB-INF/macAddressManager.jsp"),
			@Result(name = "error", location = "/WEB-INF/macAddressManager.jsp") })
	public String getApprove(){
		this.select = "2";
		devices = adminAction.getApproveDevice();
		if(devices == null){
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "getRejected", results = { @Result(name = "success", location = "/WEB-INF/macAddressManager.jsp"),
			@Result(name = "error", location = "/WEB-INF/macAddressManager.jsp") })
	public String getRejected(){
		this.select = "3";
		devices = adminAction.getRejectedDevice();
		if(devices == null){
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "getTimeout", results = { @Result(name = "success", location = "/WEB-INF/macAddressManager.jsp"),
			@Result(name = "error", location = "/WEB-INF/macAddressManager.jsp") })
	public String getTimeout(){
		this.select = "4";
		devices = adminAction.getTimeoutDevice();
		if(devices == null){
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "approve" )
	public String approve(){
		if(adminAction.updateDeviceStatus(macId, Device.APPROVED)){
			return getReturnValue();
		}
		return ERROR;
	}
	
	@Action(value = "reject" )
	public String reject(){
		if(adminAction.updateDeviceStatus(macId, Device.DENIED)){
			return getReturnValue();
		} else {
			return ERROR;
		}
	}

}
