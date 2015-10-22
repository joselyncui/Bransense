package com.matrix.brainsense.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.action.AdminAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results({ @Result(name = "default", type = "redirect", location = "/struts/generateKeyCode"),
	@Result(name = "input", location = "/WEB-INF/login.jsp")})
@Validations(requiredStrings={
        @RequiredStringValidator(fieldName="userName",message="Username can not be null!"),
        @RequiredStringValidator(fieldName="password",message="Password can not be null!")
    }
)
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class CheckLoginAction extends ActionSupport implements ServletRequestAware {
	
	public static String USER = "user";
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Autowired
	private AdminAction adminAction;
	private String message;
	private String userName;
	private String password;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Action(value = "loginCheck", results = { @Result(name = "error", type = "chain", location = "login") })
	public String login() throws Exception {
		if (adminAction.canLogin(this.userName, this.password)) {
			request.getSession().setAttribute(USER, userName);
			return "default";
		} else {
			this.message = "Login failed, please check your username and password!";
			return ERROR;
		}
	}

	@Action(value = "logout", results = { @Result(name = "success", type = "redirect", location = "/struts/login") })
	@SkipValidation
	public String logout() {
		request.getSession().setAttribute(USER, null);
		return SUCCESS;
	}
	
}
