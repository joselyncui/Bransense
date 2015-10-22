package com.matrix.brainsense.struts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.like.likeutils.file.FileUtil;
import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.Watchdog;
import com.matrix.brainsense.util.APKUtil;
import com.matrix.brainsense.util.DataStore;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect", location = "initWatchdog?message=${message}"), 
	@Result(name = "error", type = "redirect", location = "initWatchdog?message=${message}"),
	})
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class AddWatchdogAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminAction action;
	
	private int watchdogId;
	private File watchdogFile;
	private String message;
	private List<Watchdog> watchdogs = new ArrayList<Watchdog>();
	public int getWatchdogId() {
		return watchdogId;
	}
	public void setWatchdogId(int watchdogId) {
		this.watchdogId = watchdogId;
	}
	public File getWatchdogFile() {
		return watchdogFile;
	}
	public void setWatchdogFile(File watchdogFile) {
		this.watchdogFile = watchdogFile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Watchdog> getWatchdogs() {
		return watchdogs;
	}
	public void setWatchdogs(List<Watchdog> watchdogs) {
		this.watchdogs = watchdogs;
	}
	
	private Watchdog watchdog;
	
	@Action(value = "initWatchdog", results = { @Result(name = "success", location = "/WEB-INF/uploadWatchdog.jsp")})
	public String initWatchdog(){
		this.watchdogs = action.getAllAvailableWatchdog();
		return SUCCESS;
	}
	
	// save watch dog information to database
	private boolean save(int versionCode, String versionName){
		watchdog = action.getAvailableWatchdog();
		// if the database is empty, add a new watchdog to database
		if(watchdog == null){
			return addWatchdog(String.valueOf(versionCode), versionName);
		} else if(Integer.valueOf(watchdog.getVersionCode()) >= versionCode) { // check the version
			this.message = "You must upload a bigger version!";
			return false;
		} else {
			return addWatchdog(String.valueOf(versionCode), versionName);
		}
	}

	// copy the file to ftp server
	private boolean copy(String versionCode){
		try {
			FileUtil.copyFileByChannels(watchdogFile.getAbsolutePath(), DataStore.getWatchdogPath(versionCode), DataStore.getWatchdogName());
		} catch (IOException e) {
			e.printStackTrace();
			this.message = "Upload failed!";
			return false;
		}
		return true;
	}
	
	// change the old available watchdog to unavailable
	private void addCheck(){
		Watchdog unWatchdog = action.getAvailableWatchdog();
		if(unWatchdog != null){
			unWatchdog.setAvailable(Watchdog.UNAVAILABLE);
			action.updateWatchdog(unWatchdog);
		}
	}
	
	// watchdog doesn't exist in database, then save new watchdog to database
	private boolean addWatchdog(String versionCode, String versionName){
		// copy the file to ftp server
		copy(versionCode);
		Watchdog newWatchdog = new Watchdog(DataStore.getRelativeWatchdogPath(versionCode), versionCode, versionName, Watchdog.AVAILABLE);
		// change the old available watchdog to unavailable
		addCheck();
		if(action.addWatchdog(newWatchdog)) {
			this.message = "Add new Watchdog success! The version is " + versionName;
			return true;
		}
		this.message = "Add new Watchdog failed! The version is " + versionName;
		return false;
	}
	
	@Action(value = "uploadWatchdogFile")
	public String uploadWatchdogFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getServletContext().getRealPath("/");
		int versionCode = APKUtil.getApkVersionCode(path, watchdogFile.getAbsolutePath());
		String versionName = APKUtil.getApkVersionName(path, watchdogFile.getAbsolutePath()).trim();
		if(save(versionCode, versionName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "setWatchdogAvailable",  results = { @Result(name = ERROR, type="redirect", location = "initXBMC?message=${message}") })
	public String setAvailable(){
		Watchdog unWatchdog = action.getAvailableWatchdogById(this.watchdogId);
		Watchdog aviWatchdog = action.getAvailableWatchdog();
		unWatchdog.setAvailable(Watchdog.AVAILABLE);
		aviWatchdog.setAvailable(Watchdog.UNAVAILABLE);
		action.updateWatchdog(unWatchdog);
		action.updateWatchdog(aviWatchdog);
		this.message = "Success!";
		return SUCCESS;
	}
	
}
