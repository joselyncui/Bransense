package com.matrix.brainsense.addon;

import java.io.File;

import com.like.likeutils.file.FileUtil;
import com.matrix.brainsense.action.AdminAction;
import com.matrix.brainsense.entity.BasePackage;
import com.matrix.brainsense.entity.Language;
import com.matrix.brainsense.entity.Type;

public class BasePackageOperate extends PackageOperate {
	
	private AdminAction adminAction;
	private Language language;
	private Type type;
	private int languageId;
	private int typeId;
	
	public BasePackageOperate(AdminAction adminAction, Language language,
			Type type, int languageId, int typeId) {
		super();
		this.adminAction = adminAction;
		this.language = language;
		this.type = type;
		this.languageId = languageId;
		this.typeId = typeId;
	}

	private long getBaseFolderSize(){
		return FileUtil.getFolderSize(BasePackage.getBasePacPath(languageId, typeId));
	}
	
	private String getDBPath() {
		return BasePackage.getRelativeBasePacPath(language.getLanguageId(), type.getTypeId())
				+ File.separator + languageId + "_" + typeId + ".zip";
	}
	
	private boolean saveBaseToDB(){
		BasePackage basePackage = new BasePackage(language, type, getDBPath(), getBaseFolderSize());
		return adminAction.saveBaseToDB(basePackage);
	}

	@Override
	public boolean myExecute() {
		return saveBaseToDB();
	}
	
}
