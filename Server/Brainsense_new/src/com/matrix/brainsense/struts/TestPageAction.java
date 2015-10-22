package com.matrix.brainsense.struts;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.User;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect", location = "initUser"), 
	@Result(name = "error", type = "redirect", location = "initUser"),
	})
public class TestPageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminAction action;
	
	private List<User> users;
	private String macId;
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	@Action(value = "initUser", results = {@Result(name="success", location="/WEB-INF/ForTest.jsp")})
	public String initUser(){
		users = action.getAllUser();
		return SUCCESS;
	}
	
	@Action(value = "resetUser")
	public String reset(){
		action.resetUser(this.macId);
		return SUCCESS;
	}
	
	@Action(value = "deleteUser")
	public String deleteUser(){
		action.deleteUser(this.macId);
		return SUCCESS;
	}

}
