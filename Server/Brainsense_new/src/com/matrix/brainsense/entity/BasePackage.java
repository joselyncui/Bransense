package com.matrix.brainsense.entity;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.matrix.brainsense.util.DataStore;

@Entity
@Table(name = "tb_basepackage")
@XmlRootElement
public class BasePackage {
	
	public static int STATUS_UNAVAILABLE = 0;
	public static int STATUS_AVAILABLE = 1;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "baseid", length = 36, unique = true, nullable = false)
	private int baseId;
	
	@ManyToOne
	@JoinColumn(name = "languageid")
	private Language language;
	
	@ManyToOne
	@JoinColumn(name = "typeid")
	private Type type;
	
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	
	@Column(name = "packagepath", length = 200, nullable = false)
	private String packagePath;
	
	@Column(name = "size", nullable = false)
	private long size;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status = STATUS_AVAILABLE;
	
	public int getBaseId() {
		return baseId;
	}
	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BasePackage() {
		super();
	}
	public BasePackage(Language language, Type type, String packagePath,
			long size) {
		super();
		this.language = language;
		this.type = type;
		this.name = language.getName() + "_" + type.getName() + "_Package";
		this.packagePath = packagePath;
		this.size = size;
	}
	
	public static String getRelativeBasePacPath(int languageId, int typeId){
		return File.separator + "Base Package" + File.separator + languageId + "_" + typeId;
	}
	
	public static String getBasePacPath(int languageId, int typeId){
		return DataStore.getFtpPath() + getRelativeBasePacPath(languageId, typeId);
	}
	
}
