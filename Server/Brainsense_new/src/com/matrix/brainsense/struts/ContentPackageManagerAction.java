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
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.entity.Country;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results({
		@Result(name = "success", type = "redirect", location = "getContentPackage?message=${message}") })
@InterceptorRefs({@InterceptorRef("annotatedStack") })
public class ContentPackageManagerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminAction adminAction;
	
	private List<ContentPackage> packages;
	private List<Country> countries;
	private List<Category> categories;
	private String countrySelect;
	private String categorySelect;
	public List<ContentPackage> getPackages() {
		return packages;
	}
	public void setPackages(List<ContentPackage> packages) {
		this.packages = packages;
	}
	public List<Country> getCountries() {
		return countries;
	}
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getCountrySelect() {
		return countrySelect;
	}
	public void setCountrySelect(String countrySelect) {
		this.countrySelect = countrySelect;
	}
	public String getCategorySelect() {
		return categorySelect;
	}
	public void setCategorySelect(String categorySelect) {
		this.categorySelect = categorySelect;
	}
	private void init(){
		this.countries = adminAction.getDownloadableCountry();
		this.categories = adminAction.getDownloadableCategory();
	}
	
	@Action(value = "getContentPackage", results = {@Result(name="success", location = "/WEB-INF/contentPackageManager.jsp")})
	public String getContentPackage(){
		init();
		this.packages = adminAction.getAvailableContentPackage();
		return SUCCESS;
	}
	
	@Action(value="getContentByCountryAndCategory", results = {@Result(name="success", location = "/WEB-INF/contentPackageManager.jsp")} )
	public String getContentByCountryAndCategory(){
		init();
		int countryId = Integer.valueOf(countrySelect);
		int categoryId = Integer.valueOf(categorySelect);
		this.packages = adminAction.getPackageByCountryAndCategory(countryId, categoryId);
		return SUCCESS;
	}
	
}
