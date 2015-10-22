package com.matrix.brainsense.struts;

import java.io.File;

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
import com.matrix.brainsense.addon.AddonOperate;
import com.matrix.brainsense.addon.ContentPacAddonOperate;
import com.matrix.brainsense.addon.ContentPackageOperate;
import com.matrix.brainsense.addon.PackageOperate;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.Country;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("custom-default")
@Namespace("/struts")
@Controller
@Results({
		@Result(name = "success", type = "redirect", location = "initPackage?message=${message}"),
		@Result(name = "input", type = "redirect", location = "initPackage?message=${message}") })
@Validations(requiredStrings = {
		@RequiredStringValidator(fieldName = "iconName", message = "You must upload an ICON!"),
		@RequiredStringValidator(fieldName = "packageName", message = "Package name can not be null!"),
		@RequiredStringValidator(fieldName = "contentName", message = "You must upload a content package!") })
@InterceptorRefs({ @InterceptorRef("annotatedStack") })
public class ContentPackageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminAction adminAction;
	private String message;
	private String packageName;
	private File iconFile;
	private String iconFileFileName;
	private String iconName;
	private File contentFile;
	private String contentFileFileName;
	private String contentName;
	private String countrySelect;
	private String categorySelect;
	private String description = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public File getIconFile() {
		return iconFile;
	}

	public void setIconFile(File iconFile) {
		this.iconFile = iconFile;
	}

	public String getIconFileFileName() {
		return iconFileFileName;
	}

	public void setIconFileFileName(String iconFileFileName) {
		this.iconFileFileName = iconFileFileName;
	}

	public File getContentFile() {
		return contentFile;
	}

	public void setContentFile(File contentFile) {
		this.contentFile = contentFile;
	}

	public String getContentFileFileName() {
		return contentFileFileName;
	}

	public void setContentFileFileName(String contentFileFileName) {
		this.contentFileFileName = contentFileFileName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	@Action(value = "addContent", results = { @Result(name = "error", type = "redirect", location = "initPackage?message=${message}") })
	public String addContentPackage() {
		int countryId = Integer.valueOf(countrySelect);
		int categoryId = Integer.valueOf(categorySelect);
		Country country = adminAction.getCountryById(countryId);
		Category category = adminAction.getCategoryById(categoryId);
		if (packageName == null || packageName.equals("")) {
			this.message = "Package name can not be null!";
			return ERROR;
		}
		if (!adminAction.checkPackageName(packageName, countryId, categoryId)) {
			this.message = "Country, category and package name repeat!";
			return ERROR;
		}
		AddonOperate addonOperate = new ContentPacAddonOperate(packageName, country, category, contentFile, iconFile);
		PackageOperate packageOperate = new ContentPackageOperate(iconFile, iconFileFileName, 
				packageName, countryId, categoryId, description, country, category, adminAction);
		if(!packageOperate.generate(addonOperate)) {
			this.message = "Add content package failed!";
			return ERROR;
		}
		this.message = "Add content package success!";
		return SUCCESS;
	}

}
