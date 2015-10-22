package com.matrix.brainsense.addon;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.like.likeutils.file.FileUtil;
import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.Addon;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.ContentPackage;
import com.matrix.brainsense.entity.Country;
import com.opensymphony.xwork2.ActionContext;

public class ContentPackageOperate extends PackageOperate {
	private File iconFile;
	private String iconFileFileName;
	private String packageName;
	private int countryId;
	private int categoryId;
	private String description;
	private Country country;
	private Category category;
	private AdminAction adminAction;
	
	
	
	public ContentPackageOperate(File iconFile, String iconFileFileName,
			String packageName, int countryId, int categoryId,
			String description, Country country, Category category,
			AdminAction adminAction) {
		super();
		this.iconFile = iconFile;
		this.iconFileFileName = iconFileFileName;
		this.packageName = packageName;
		this.countryId = countryId;
		this.categoryId = categoryId;
		this.description = description;
		this.country = country;
		this.category = category;
		this.adminAction = adminAction;
	}

	private String getIconSaveName() {
		return "icon" + iconFileFileName.substring(iconFileFileName.lastIndexOf("."));
	}
	
	private String getIconSavePath() {
		ServletContext servletContext = (ServletContext) ActionContext
				.getContext().get(ServletActionContext.SERVLET_CONTEXT);
		String rootPath = new File(servletContext.getRealPath("/")).getParent();
		return rootPath
				+ File.separator
				+ ContentPackage.getRelativeIconPath(packageName, countryId,
						categoryId);
	}
	
	private boolean copyIcon() {
		boolean flag = true;
		try {
			FileUtil.copyFileByChannels(iconFile.getAbsolutePath(),
					getIconSavePath(), getIconSaveName());
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	private long getSize() {
		return FileUtil.getFolderSize(ContentPackage.getContentPackagePath(
				packageName, countryId, categoryId));
	}
	
	private boolean saveToDB() {
		String packageDBPath = ContentPackage.getContentPacDBPath(packageName, countryId,
				categoryId, Addon.getContentPacTmpAddonName(packageName, countryId, categoryId));
		String iconDBPath = ContentPackage.getIconDBPath(packageName, countryId, categoryId, iconFileFileName);
		ContentPackage contentPackage = new ContentPackage(packageName,
				packageDBPath, iconDBPath, description, country, category, getSize());
		return adminAction.saveContentToDB(contentPackage);
	}
	
	@Override
	public boolean myExecute() {
		if(! copyIcon()) {
			return false;
		}
		if(!saveToDB()) {
			return false;
		}
		return true;
	}
	
	

}
