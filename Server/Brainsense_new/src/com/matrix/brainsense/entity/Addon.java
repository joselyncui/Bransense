package com.matrix.brainsense.entity;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class Addon {

	public static String getContentPacTmpAddonPath(String packageName, int countryId, int categoryId) {
		return ContentPackage.getContentPackagePath(packageName, countryId,
				categoryId) + File.separator + "addonTmp";
	}
	
	public static String getContentPacTmpAddonName(String packageName, int countryId, int categoryId) {
		return countryId + "_" + categoryId + "_" + packageName + ".zip";
	}
	
	public static String getAddonName() {
		return "plugin.brainsense";
	}
	
	public static String getRelativeAddonResourcesPath() {
		return File.separator + getAddonName() + File.separator + "resources" + File.separator + "content";
	}
	
	public static String getRelativeAddonXmlPath() {
		return File.separator + getAddonName() + File.separator + "addon.xml";
	}
	
	public static String getAddonPath() {
		ServletContext servletContext = (ServletContext) ActionContext
				.getContext().get(ServletActionContext.SERVLET_CONTEXT);
		String rootPath = new File(servletContext.getRealPath("/")).getAbsolutePath();
		return rootPath + File.separator + "Addon";
	}
	
	public static String getBasePacTmpAddonPath(int languageId, int typeId) {
		return BasePackage.getBasePacPath(languageId, typeId) + File.separator + "tmpAddon";
	}
	
	public static String getBasePacTmpAddonName(int languageId, int typeId) {
		return languageId + "_" + typeId + ".zip";
	}
	
}
