package com.matrix.brainsense.struts;

import java.io.File;
import java.io.FileNotFoundException;
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
import com.matrix.brainsense.entity.XBMCInfo;
import com.matrix.brainsense.util.APKUtil;
import com.matrix.brainsense.util.DataStore;
import com.opensymphony.xwork2.ActionSupport;
@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect", location = "initXBMC?message=${message}"), 
	@Result(name = "input", type = "redirect", location = "initXBMC?message=${message}"),
	})
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class AddXBMCAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminAction action;

	private int xbmcId;
	private  File xbmcFile;
    private  String  xbmcFileFileName;
    private String xbmcName;
    private String rdoAvailable;
    private String message;
    private List<XBMCInfo> xbmcs = new ArrayList<XBMCInfo>();
    
    private XBMCInfo xbmcInfo;
    
    
	public int getXbmcId() {
		return xbmcId;
	}
	public void setXbmcId(int xbmcId) {
		this.xbmcId = xbmcId;
	}
	public File getXbmcFile() {
		return xbmcFile;
	}
	public void setXbmcFile(File xbmcFile) {
		this.xbmcFile = xbmcFile;
	}
	public String getXbmcFileFileName() {
		return xbmcFileFileName;
	}
	public void setXbmcFileFileName(String xbmcFileFileName) {
		this.xbmcFileFileName = xbmcFileFileName;
	}
	public String getXbmcName() {
		return xbmcName;
	}
	public void setXbmcName(String xbmeName) {
		this.xbmcName = xbmeName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<XBMCInfo> getXbmcs() {
		return xbmcs;
	}
	public void setXbmcs(List<XBMCInfo> xbmcs) {
		this.xbmcs = xbmcs;
	}
	public String getRdoAvailable() {
		return rdoAvailable;
	}
	public void setRdoAvailable(String rdoAvailable) {
		this.rdoAvailable = rdoAvailable;
	}
	
	
	private boolean copy(String versionCode){
		try {
			FileUtil.copyFileByChannels(xbmcFile.getAbsolutePath(), DataStore.getXBMCPath(versionCode), DataStore.getXBMCName());
		} catch (IOException e) {
			e.printStackTrace();
			this.message = "Upload failed!";
			return false;
		}
		return true;
	}
	
	private boolean save(int versionCode, String versionName){
		if((xbmcInfo = action.getLastXBMC()) == null){
			return addXBMC(String.valueOf(versionCode), versionName);
		}
		int dbVersionCode = Integer.valueOf(action.getLastXBMC().getVersionCode());
		if(dbVersionCode == versionCode){
			return updateXBMC(String.valueOf(versionCode), versionName);
		} else if(dbVersionCode < versionCode){
			return addXBMC(String.valueOf(versionCode), versionName);
		} else {
			this.message = "The latest version is bigger than the version you upload!";
			return false;
		}
	}
	
	private XBMCInfo updateCheck(XBMCInfo xbmcInfo, String versionCode){
		XBMCInfo unXbmc = action.getAvailableXbmc();
		addCheck();
		if(unXbmc == null){
			xbmcInfo.setAvailable(XBMCInfo.AVAILABLE);
		} else if(unXbmc.getVersionCode().equals(versionCode)) {
			xbmcInfo.setAvailable(XBMCInfo.AVAILABLE);
		} else {
			xbmcInfo.setAvailable(Integer.valueOf(this.rdoAvailable));
		}
		return xbmcInfo;
	}
	
	private boolean updateXBMC(String versionCode, String versionName){
		copy(versionCode);
		xbmcInfo.setVersionCode(versionCode);
		xbmcInfo.setVersionName(versionName);
		updateCheck(xbmcInfo, versionCode);
		xbmcInfo.setPackagePath(DataStore.getRelativeXBMCPath(versionName));
		if(action.updateXBMC(xbmcInfo)){
			this.message = "Update XBMC success! The version is " + versionName;
			return true;
		}
		this.message = "Update XBMC failed! The version is " + versionName;
		return false;
	}
	
	private void addCheck(){
		XBMCInfo unXbmc = action.getAvailableXbmc();
		if(unXbmc != null && Integer.valueOf(this.rdoAvailable) == XBMCInfo.AVAILABLE){
			unXbmc.setAvailable(XBMCInfo.UNAVAILABLE);
			action.updateXBMC(unXbmc);
		}
	}
	
	private boolean addXBMC(String versionCode, String versionName){
		copy(versionCode);
		XBMCInfo xbmc = new XBMCInfo(DataStore.getRelativeXBMCPath(versionCode), versionCode, versionName, Integer.valueOf(this.rdoAvailable));
		addCheck();
		if(action.addXBMC(xbmc)) {
			this.message = "Add new XBMC success! The version is " + versionName;
			return true;
		}
		this.message = "Add new XBMC failed! The version is " + versionName;
		return false;
	}
	
	@Action(value = "initXBMC", results = { @Result(name = "success", location = "/WEB-INF/uploadXbmc.jsp")} )
	public String getAllXBMC(){
		this.xbmcs = action.getAllAvailableXBMC();
		return SUCCESS;
	}
	
	@Action(value = "uploadXbmcFile", results = { @Result(name = ERROR, type="redirect", location = "initXBMC?message=${message}") })
	public String uploadXBMCFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getServletContext().getRealPath("/");
		int uploadedVersionCode = APKUtil.getApkVersionCode(path, xbmcFile.getAbsolutePath());
		String uploadedVersionname = APKUtil.getApkVersionName(path, xbmcFile.getAbsolutePath());
		if(save(uploadedVersionCode, uploadedVersionname)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "deleteXBMC",  results = { @Result(name = ERROR, type="redirect", location = "initXBMC?message=${message}"),
			@Result(name = SUCCESS, type = "redirect", location = "initXBMC?message=${message}") })
	public String deleteXBMC(){
		XBMCInfo xbmcInfo = action.getXBMCById(this.xbmcId);
		if(xbmcInfo == null){
			this.message = "The XBMC is not exist!";
			return ERROR;
		}
		if(xbmcInfo.getAvailable() == XBMCInfo.AVAILABLE){
			this.message = "Delete failed! There must have an available XBMC in the system!";
			return ERROR;
		}
		if(action.deleteXBMC(xbmcInfo)){
			try {
				FileUtil.delete(DataStore.getXBMCPath(xbmcInfo.getVersionName()));
			} catch (FileNotFoundException e) {
			}
			this.message = "Delete success!";
			return SUCCESS;
		}
		this.message = "Delete failed!";
		return ERROR;
	}
	
	@Action(value = "setAvailable",  results = { @Result(name = ERROR, type="redirect", location = "initXBMC?message=${message}") })
	public String setAvailable(){
		XBMCInfo unAviXbmcInfo = action.getXBMCById(this.xbmcId);
		XBMCInfo aviXbmcInfo = action.getAvailableXbmc();
		unAviXbmcInfo.setAvailable(XBMCInfo.AVAILABLE);
		aviXbmcInfo.setAvailable(XBMCInfo.UNAVAILABLE);
		action.updateXBMC(unAviXbmcInfo);
		action.updateXBMC(aviXbmcInfo);
		this.message = "Success!";
		return SUCCESS;
	}
	
}
