package com.matrix.brainsense.struts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.PublicInfo;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect" ,location = "initDays?message=${message}")})
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class WaitingDaysSetting extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private AdminAction action;
	
	private int currentDays = 0;
	private PublicInfo publicInfo;
	private String publicValue;
	private String publicDescription = "";
	private String message;
	
	public int getCurrentDays() {
		return currentDays;
	}
	public void setCurrentDays(int currentDays) {
		this.currentDays = currentDays;
	}
	public PublicInfo getPublicInfo() {
		return publicInfo;
	}
	public void setPublicInfo(PublicInfo publicInfo) {
		this.publicInfo = publicInfo;
	}
	public String getPublicValue() {
		return publicValue;
	}
	public void setPublicValue(String publicValue) {
		this.publicValue = publicValue;
	}
	public String getPublicDescription() {
		return publicDescription;
	}
	public void setPublicDescription(String publicDescription) {
		this.publicDescription = publicDescription;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Action(value = "initDays", results = { @Result(name = "success", location = "/WEB-INF/setWaitingDays.jsp"),
			@Result(name = "error", location = "/WEB-INF/setWaitingDays.jsp") })
	@SkipValidation
	public String init(){
		PublicInfo publicInfo = action.getWaitingDays();
		if(publicInfo != null){
			this.currentDays = Integer.valueOf(publicInfo.getValue());
			this.publicDescription = publicInfo.getDescription();
		}
		return SUCCESS;
	}
	
	@Action(value = "updateValue", results = { @Result(name = "success", type="redirect", location = "initDays?message=${message}"),
			@Result(name = "error",type="redirect", location = "initDays") })
	public String updateValue(){
		if((publicInfo = action.getWaitingDays()) == null){
			publicInfo = new PublicInfo(this.publicValue, this.publicDescription);
			if(action.addPublic(publicInfo)){
				this.message = "Update success!";
				return SUCCESS;
			}
			this.message = "Update failed!";
			return ERROR;
		} else {
			publicInfo.setValue(this.publicValue);
			publicInfo.setDescription(this.publicDescription);
			if(action.updatePublic(publicInfo)){
				this.message = "Update success!";
				return SUCCESS;
			}
			this.message = "Update failed!";
			return ERROR;
		}
	}

}
