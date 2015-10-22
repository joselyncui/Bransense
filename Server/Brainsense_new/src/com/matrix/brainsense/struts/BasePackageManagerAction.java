package com.matrix.brainsense.struts;

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
import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.Type;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect", location = "getBasePackage?message=${message}") })
@InterceptorRefs({@InterceptorRef("annotatedStack") })
public class BasePackageManagerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminAction action;
	
	private List<BasePackage> packages;
	private List<Type> types;
	private List<Language> languages;
	private String typeSelect;
	private String languageSelect;
	public List<BasePackage> getPackages() {
		return packages;
	}
	public void setPackages(List<BasePackage> packages) {
		this.packages = packages;
	}
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.types = types;
	}
	public List<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	public String getTypeSelect() {
		return typeSelect;
	}
	public void setTypeSelect(String typeSelect) {
		this.typeSelect = typeSelect;
	}
	public String getLanguageSelect() {
		return languageSelect;
	}
	public void setLanguageSelect(String languageSelect) {
		this.languageSelect = languageSelect;
	}
	
	private void init(){
		this.types = action.getDownloadableType();
		this.languages = action.getDownloadableLanguage();
	}
	
	@Action(value = "getBasePackage", results = {@Result(name = "success", location="/WEB-INF/basePackageManager.jsp") })
	public String getBasePackage(){
		init();
		this.packages = action.getAvailableBasePackage();
		return SUCCESS;
	}
	
	@Action(value = "getBaseByTypeAndLanguage", results = {@Result(name = "success", location="/WEB-INF/basePackageManager.jsp") })
	public String getBaseByTypeAndLanguage(){
		init();
		int typeSelectNum = Integer.valueOf(typeSelect);
		int languageSelectNum = Integer.valueOf(languageSelect);
		this.packages = action.getBaseByOption(typeSelectNum, languageSelectNum);
		return SUCCESS;
	}
	
	
}
