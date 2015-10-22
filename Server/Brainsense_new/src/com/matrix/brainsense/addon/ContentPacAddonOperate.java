package com.matrix.brainsense.addon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.like.likeutils.file.FileUtil;
import com.like.likeutils.file.ZipUtil;
import com.like.likeutils.xml.XmlUtil;
import com.matrix.brainsense.entity.Addon;
import com.matrix.brainsense.entity.Category;
import com.matrix.brainsense.entity.Country;

public class ContentPacAddonOperate implements AddonOperate {
	private String packageName;
	private int countryId;
	private int categoryId;
	private String countryName;
	private String categoryName;
	private File contentFile;
	private File iconFile;
	
	public ContentPacAddonOperate(String packageName, Country country, Category category, File contentFile, File iconFile) {
		this.packageName = packageName;
		this.countryId = country.getCountryId();
		this.categoryId = category.getCategoryId();
		this.countryName = country.getName();
		this.categoryName = category.getName();
		this.contentFile = contentFile;
		this.iconFile = iconFile;
	}

	@Override
	public boolean copyPackage() {
		boolean flag = true;
		try {
			// delete the tmp file
			FileUtil.delete(Addon.getContentPacTmpAddonPath(packageName,
					countryId, categoryId)
					+ Addon.getRelativeAddonResourcesPath()
					+ File.separator
					+ Addon.getContentPacTmpAddonName(packageName, countryId,
							categoryId));
		} catch (FileNotFoundException e) {
		}
		try {
			// copy new package to tmp addon path
			FileUtil.copyFileByChannels(
					contentFile.getAbsolutePath(),
					Addon.getContentPacTmpAddonPath(packageName, countryId,
							categoryId) + Addon.getRelativeAddonResourcesPath(),
					Addon.getContentPacTmpAddonName(packageName, countryId,
							categoryId));
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean unzipPackage() {
		boolean flag = true;
		try {
			// unzip the zip file in tmp folder to the position
			ZipUtil.unZip(
					Addon.getContentPacTmpAddonPath(packageName, countryId,
							categoryId)
							+ Addon.getRelativeAddonResourcesPath()
							+ File.separator
							+ Addon.getContentPacTmpAddonName(packageName,
									countryId, categoryId),
					Addon.getContentPacTmpAddonPath(packageName, countryId,
							categoryId)
							+ Addon.getRelativeAddonResourcesPath(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				// delete package zip file
				FileUtil.delete(new File(Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId)
						+ Addon.getRelativeAddonResourcesPath(), 
						Addon.getContentPacTmpAddonName(packageName, countryId, categoryId)));
			} catch (FileNotFoundException e) {
			}
		}
		return flag;
	}

	private String getAddonFolderName() {
		return "plugin." + categoryName + ".content-" + countryName + 
				"-" + categoryName + "-" + packageName;
	}
	
	private String getAddonName() {
		return "content-" + countryName + "-" + categoryName + "-" + packageName;
	}
	
	@Override
	public boolean changeAddonName() {
		File addonFile = new File(Addon.getContentPacTmpAddonPath(packageName,
				countryId, categoryId), Addon.getAddonName());
		try {
			XmlUtil.updateAttributeByDom(
					Addon.getContentPacTmpAddonPath(packageName, countryId,
							categoryId) + Addon.getRelativeAddonXmlPath(),
					"addon", "id", getAddonFolderName());
			XmlUtil.updateAttributeByDom(
					Addon.getContentPacTmpAddonPath(packageName, countryId,
							categoryId) + Addon.getRelativeAddonXmlPath(),
					"addon", "name", getAddonName());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (!addonFile.renameTo(new File(Addon.getContentPacTmpAddonPath(
				packageName, countryId, categoryId), getAddonFolderName()))) {
			return false;
		}
		return true;
	}

	@Override
	public boolean copyAddon() {
		boolean flag = true;
		try {
			// before put new addon, delete old one
			FileUtil.delete(new File(Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId)).getParent());
		} catch (FileNotFoundException e1) {
		}
		try {
			// copy addon to tmp addon path
			FileUtil.copyFolderByChannels(Addon.getAddonPath(),
					Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId));
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean zipAddon() {
		try {
			ZipUtil.compress(Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId),
					new File(Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId)).getParent() + File.separator
							+ Addon.getContentPacTmpAddonName(packageName, countryId, categoryId));
			FileUtil.delete(Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean setIcon() {
		if(iconFile == null){
			return true;
		}
		try {
			FileUtil.copyFileByChannels(iconFile.getAbsolutePath(), 
					Addon.getContentPacTmpAddonPath(packageName, countryId, categoryId) + File.separator + Addon.getAddonName(), "icon.png");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
