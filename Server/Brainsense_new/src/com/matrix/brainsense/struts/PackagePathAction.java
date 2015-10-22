package com.matrix.brainsense.struts;

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
import com.matrix.brainsense.entity.PackagePath;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect" ,location = "getPath?message=${message}"),
	@Result(name = "invalid.token", location = "getPath?message=${message}") } )
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class PackagePathAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminAction adminAction;
	
	private String basePath;
	private String contentPath;
	private String message;
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getContentPath() {
		return contentPath;
	}
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Action(value = "getPath", results = { @Result(name = "success", location = "/WEB-INF/PackagePath.jsp") })
	public String getPath(){
		PackagePath packagePath = adminAction.getPath();
		if(packagePath == null){
			this.basePath = "";
			this.contentPath = "";
		}
		this.basePath = packagePath.getBasePath();
		this.contentPath = packagePath.getContentPath();
		return SUCCESS;
	}
	
	@Action(value = "updatePackagePath")
	public String updatePath(){
		PackagePath packagePath = adminAction.getPath();
		if(packagePath == null){
			if(adminAction.addPath(this.basePath, this.contentPath)){
				this.message = "Add success!";
			} else {
				this.message = "Add failed!";
			}
			
		} else {
			if(adminAction.updatePath(this.basePath, this.contentPath)){
				this.message = "Update Success!";
			} else {
				this.message = "Update failed!";
			}
		}
		return SUCCESS;
	}
	
}
