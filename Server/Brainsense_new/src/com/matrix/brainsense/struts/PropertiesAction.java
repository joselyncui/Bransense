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
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", location = "/WEB-INF/AddProperties.jsp"),
	@Result(name = "error", location = "/WEB-INF/AddProperties.jsp") } )
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class PropertiesAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	public static String LANGUAGE_ID = "1";
	public static String COUNTRY_ID = "2";
	public static String CATEGORY_ID = "3";
	public static String TYPE_ID = "4";
	
	@Autowired
	private AdminAction adminAction;
	
	private String select;
	private Object results;
	private String message;
	private String propertiesName;
	private int propertiesId;
	private String addPropertiesName;

	public Object getResults() {
		return results;
	}
	public void setResults(Object results) {
		this.results = results;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPropertiesName() {
		return propertiesName;
	}
	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}
	public int getPropertiesId() {
		return propertiesId;
	}
	public void setPropertiesId(int propertiesId) {
		this.propertiesId = propertiesId;
	}
	public String getAddPropertiesName() {
		return addPropertiesName;
	}
	public void setAddPropertiesName(String addPropertiesName) {
		this.addPropertiesName = addPropertiesName;
	}
	
	@Action(value = "AddProperties", results = { @Result(name = "success", location = "/WEB-INF/AddProperties.jsp") })
	public String getLanguage(){
		this.select = PropertiesAction.LANGUAGE_ID;
		this.results = adminAction.getAllAvailableLanguage();
		return SUCCESS;
	}
	
	@Action(value = "getCountry")
	public String getCountry(){
		this.select = COUNTRY_ID;
		this.results= adminAction.getAllAvailableCountry();
		return SUCCESS;
	}
	
	@Action(value = "getCategory")
	public String getCategory(){
		this.select = CATEGORY_ID;
		this.results= adminAction.getAllAvailableCategory();
		return SUCCESS;
	}
	
	@Action(value = "getType")
	public String getType(){
		this.select = TYPE_ID;
		this.results = adminAction.getAllAvailableType();
		return SUCCESS;
	}
	
	@Action(value = "updateLanguage", results = { @Result(name = "success", type = "redirect", location = "AddProperties") })
	public String updateLanguage(){
		this.select = LANGUAGE_ID;
		if(adminAction.updateLanguage(this.propertiesId, this.propertiesName)){
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	@Action(value = "updateCountry", results = { @Result(name = "success", type = "redirect", location = "getCountry") })
	public String updateCountry(){
		this.select = COUNTRY_ID;
		if(adminAction.updateCountry(this.propertiesId, this.propertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "updateCategory", results = { @Result(name = "success", type = "redirect", location = "getCategory") })
	public String updateCategory(){
		this.select = CATEGORY_ID;
		if(adminAction.updateCategory(this.propertiesId, this.propertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "updateType", results = { @Result(name = "success", type = "redirect", location = "getType") })
	public String updateType(){
		this.select = TYPE_ID;
		if(adminAction.updateType(this.propertiesId, this.propertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "deleteLanguage", results = { @Result(name = "success", type = "redirect", location = "AddProperties") })
	public String deleteLanguage(){
		this.select = LANGUAGE_ID;
		if(adminAction.deleteLanguage(this.propertiesId)){
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	@Action(value = "deleteCountry", results = { @Result(name = "success", type = "redirect", location = "getCountry") })
	public String deleteCountry(){
		this.select = COUNTRY_ID;
		if(adminAction.deleteCountry(this.propertiesId)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "deleteCategory", results = { @Result(name = "success", type = "redirect", location = "getCategory") })
	public String deleteCategory(){
		this.select = CATEGORY_ID;
		if(adminAction.deleteCategory(this.propertiesId)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "deleteType", results = { @Result(name = "success", type = "redirect", location = "getType") })
	public String deleteType(){
		this.select = TYPE_ID;
		if(adminAction.deleteType(this.propertiesId)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "insertLanguage", results = { @Result(name = "success", type = "redirect", location = "AddProperties") })
	public String insertLanguage(){
		this.select = LANGUAGE_ID;
		if(adminAction.addLanguage(this.addPropertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "insertCategory", results = { @Result(name = "success", type = "redirect", location = "getCategory") })
	public String insertCategory(){
		this.select = CATEGORY_ID;
		if(adminAction.addCategory(this.addPropertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "insertCountry", results = { @Result(name = "success", type = "redirect", location = "getCountry") })
	public String insertCountry(){
		this.select = COUNTRY_ID;
		if(adminAction.addCountry(this.addPropertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
	@Action(value = "insertType", results = { @Result(name = "success", type = "redirect", location = "getType") })
	public String insertType(){
		this.select = TYPE_ID;
		if(adminAction.addType(this.addPropertiesName)){
			return SUCCESS;
		}
		return ERROR;
	}
	
}
