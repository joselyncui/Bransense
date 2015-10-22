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
import com.matrix.brainsense.entity.KeyCode;
import com.matrix.brainsense.util.NumberUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results({
		@Result(name = "success", location = "/WEB-INF/generateKeyCode.jsp"),
		@Result(name = "invalid.token", location = "/WEB-INF/generateKeyCode.jsp") })
@InterceptorRefs({ @InterceptorRef("defaultStack"), @InterceptorRef("token"), @InterceptorRef("annotatedStack")  })
@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "codeCount", message = "Please check your code number!") })
public class KeyCodeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private AdminAction adminAction;

	private String codeCount;
	private String message;
	private List<KeyCode> keyCodes = new ArrayList<KeyCode>();

	public String getCodeCount() {
		return codeCount;
	}

	public void setCodeCount(String codeCount) {
		this.codeCount = codeCount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String mesage) {
		this.message = mesage;
	}

	public List<KeyCode> getKeyCodes() {
		return keyCodes;
	}

	public void setKeyCodes(List<KeyCode> keyCodes) {
		this.keyCodes = keyCodes;
	}

	@Action(value = "createKeyCode", results = { @Result(name = "error", location = "/WEB-INF/generateKeyCode.jsp") })
	public String generateKeyCode() {
		if(!NumberUtil.isNumeric(codeCount)){
			this.message = "Please check code count you input!";
			return ERROR;
		}
		int codeCountNum = Integer.valueOf(codeCount);
		if (codeCountNum > 10) {
			this.message = "You can only generate 10 key codes every time!";
			return ERROR;
		}
		if (codeCountNum == 0) {
			this.message = "The number must bigger then 0!";
			return ERROR;
		}
		this.keyCodes = adminAction.generateKeyCode(codeCountNum);
		this.message = "";
		return SUCCESS;
	}
	
}
