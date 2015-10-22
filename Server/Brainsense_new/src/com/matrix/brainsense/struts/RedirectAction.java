package com.matrix.brainsense.struts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Action(value = "*", results={@Result (location="/WEB-INF/{1}.jsp") })
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class RedirectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
}
