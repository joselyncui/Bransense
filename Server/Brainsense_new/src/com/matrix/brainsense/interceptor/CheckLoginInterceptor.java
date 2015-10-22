package com.matrix.brainsense.interceptor;

import java.util.Map;

import org.springframework.stereotype.Controller;

import com.matrix.brainsense.struts.CheckLoginAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Controller
public class CheckLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if (CheckLoginAction.class == actionInvocation.getAction().getClass()) {
			return actionInvocation.invoke();
		}
		Map<String, Object> map = actionInvocation.getInvocationContext().getSession();
		if (null == map.get(CheckLoginAction.USER)) {
			return "login";
		}
		return actionInvocation.invoke();
	}

}
