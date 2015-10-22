package com.matrix.brainsense.struts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.addon.AddonOperate;
import com.matrix.brainsense.addon.BaseAddonOperate;
import com.matrix.brainsense.addon.BasePackageOperate;
import com.matrix.brainsense.addon.PackageOperate;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.Country;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.Type;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results( { @Result(name = "success", type = "redirect", location = "initPackage?message=${message}"),
	@Result(name = "input", type = "redirect", location = "initPackage?message=${message}")})
@Validations(requiredStrings={
        @RequiredStringValidator(fieldName="baseName",message="You must upload a base package!")
    }
)
@InterceptorRefs({@InterceptorRef("annotatedStack") })
public class BasePackageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AdminAction adminAction;
	
	private File baseFile;
	private File iconFile;
	private String baseFileFileName;
	private String languageSelect;
	private String typeSelect;
	private String baseName;
	private List<Language> languages = new ArrayList<Language>();
	private List<Type> types = new ArrayList<Type>();
	private List<Country> countries = new ArrayList<Country>();
	private List<Category> categories = new ArrayList<Category>();
	private String message;
	public File getBaseFile() {
		return baseFile;
	}
	public void setBaseFile(File baseFile) {
		this.baseFile = baseFile;
	}
	public File getIconFile() {
		return iconFile;
	}
	public void setIconFile(File iconFile) {
		this.iconFile = iconFile;
	}
	public String getBaseFileFileName() {
		return baseFileFileName;
	}
	public void setBaseFileFileName(String baseFileFileName) {
		this.baseFileFileName = baseFileFileName;
	}
	public String getLanguageSelect() {
		return languageSelect;
	}
	public void setLanguageSelect(String languageSelect) {
		this.languageSelect = languageSelect;
	}
	public String getTypeSelect() {
		return typeSelect;
	}
	public void setTypeSelect(String typeSelect) {
		this.typeSelect = typeSelect;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	public List<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.types = types;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Action(value = "initPackage", results = { @Result(name = "success", location = "/WEB-INF/generatePackage.jsp") })
	@SkipValidation
	public String initPackage(){
		languages = adminAction.getAllAvailableLanguage();
		types = adminAction.getAllAvailableType();
		countries = adminAction.getAllAvailableCountry();
		categories = adminAction.getAllAvailableCategory();
		return SUCCESS;
	}
	
	@Action(value = "addBasePackage", results = { @Result(name = "error", type="redirect", location = "initPackage?message=${message}") })
	public String addBasePackage() {
		int languageId = Integer.valueOf(languageSelect);
		int typeId = Integer.valueOf(typeSelect);
		Language language = adminAction.getAvailableLanguageById(languageId);
		Type type = adminAction.getAvailableTypeById(typeId);
		if(!this.baseFileFileName.endsWith("zip")){
			this.message = "You must upload ZIP file!";
			return ERROR;
		}
		AddonOperate addonOperate = new BaseAddonOperate(language, type, baseFile, iconFile);
		PackageOperate packageOperate = new BasePackageOperate(adminAction, language, type, languageId, typeId);
		if(!packageOperate.generate(addonOperate)){
			this.message = "Add base package failed!";
			return ERROR;
		}
		this.message = "Add base package success!";
		return SUCCESS;
	}

}
