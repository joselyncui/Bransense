package com.matrix.brainsense.addon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.like.likeutils.file.FileUtil;
import com.like.likeutils.file.ZipUtil;
import com.like.likeutils.xml.XmlUtil;
import com.matrix.brainsense.entity.Addon;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.Type;

public class BaseAddonOperate implements AddonOperate {
	private File baseFile;
	private File iconFile;
	
	private int typeId;
	private int languageId;
	private String typeName;
	private String languageName;
	
	public BaseAddonOperate(Language language, Type type, File baseFile, File iconFile) {
		super();
		this.baseFile = baseFile;
		this.iconFile = iconFile;
		this.typeId = type.getTypeId();
		this.languageId = language.getLanguageId();
		this.typeName = type.getName().trim();
		this.languageName = language.getName().trim();
	}

	@Override
	public boolean copyPackage() {
		boolean flag = true;
		try {
			// delete the tmp file
			FileUtil.delete(Addon.getBasePacTmpAddonPath(languageId, typeId)
					+ Addon.getRelativeAddonResourcesPath()
					+ File.separator
					+ Addon.getBasePacTmpAddonName(languageId, typeId));
		} catch (FileNotFoundException e) {
		}
		try {
			// copy new package to tmp addon path
			FileUtil.copyFileByChannels(
					baseFile.getAbsolutePath(),
					Addon.getBasePacTmpAddonPath(languageId, typeId) + Addon.getRelativeAddonResourcesPath(),
					Addon.getBasePacTmpAddonName(languageId, typeId));
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
					Addon.getBasePacTmpAddonPath(languageId, typeId)
							+ Addon.getRelativeAddonResourcesPath()
							+ File.separator
							+ Addon.getBasePacTmpAddonName(languageId, typeId),
					Addon.getBasePacTmpAddonPath(languageId, typeId)
							+ Addon.getRelativeAddonResourcesPath(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				// delete package zip file
				FileUtil.delete(new File(Addon.getBasePacTmpAddonPath(languageId, typeId)
						+ Addon.getRelativeAddonResourcesPath(), 
						Addon.getBasePacTmpAddonName(languageId, typeId)));
			} catch (FileNotFoundException e) {
			}
		}
		return flag;
	}
	
	private String getAddonFolderName() {
		return "plugin." + typeName + ".base-" + languageName;
	}
	
	private String getAddonName() {
		return "base-" + languageName + "-" + typeName;
	}

	@Override
	public boolean changeAddonName() {
		File addonFile = new File(Addon.getBasePacTmpAddonPath(languageId, typeId),
				Addon.getAddonName());
		try {
			XmlUtil.updateAttributeByDom(
					Addon.getBasePacTmpAddonPath(languageId, typeId) + Addon.getRelativeAddonXmlPath(),
					"addon", "id", getAddonFolderName());
			XmlUtil.updateAttributeByDom(
					Addon.getBasePacTmpAddonPath(languageId, typeId) + Addon.getRelativeAddonXmlPath(),
					"addon", "name", getAddonName());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (!addonFile.renameTo(new File(Addon.getBasePacTmpAddonPath(languageId, typeId), 
				getAddonFolderName()))) {
			return false;
		}
		return true;
	}

	@Override
	public boolean copyAddon() {
		boolean flag = true;
		try {
			// before put new addon, delete old one
			FileUtil.delete(new File(Addon.getBasePacTmpAddonPath(languageId, typeId)).getParent());
		} catch (FileNotFoundException e1) {
		}
		try {
			// copy addon to tmp addon path
			FileUtil.copyFolderByChannels(Addon.getAddonPath(),
					Addon.getBasePacTmpAddonPath(languageId, typeId));
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean zipAddon() {
		try {
			ZipUtil.compress(Addon.getBasePacTmpAddonPath(languageId, typeId),
					new File(Addon.getBasePacTmpAddonPath(languageId, typeId)).getParent() + File.separator
							+ Addon.getBasePacTmpAddonName(languageId, typeId));
			FileUtil.delete(Addon.getBasePacTmpAddonPath(languageId, typeId));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean setIcon() {
		if(iconFile == null) {
			return true;
		}
		try {
			FileUtil.copyFileByChannels(iconFile.getAbsolutePath(), 
					Addon.getBasePacTmpAddonPath(languageId, typeId) + File.separator + Addon.getAddonName(), "icon.png");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
