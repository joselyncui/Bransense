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

@XmlRootElement
@Entity
@Table(name = "tb_contentpackage")
public class ContentPackage {
	
	public static int AVAILABLE = 1;
	public static int UNAVAILABLE = 0;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "dispackageid", length = 36, unique = true, nullable = false)
	private int disPackageId;
	
	@Column(name = "name", length = 20, nullable = false)
	private String name;
	
	@Column(name = "packagePath", length = 200, nullable = false)
	private String packagePath;
	
	@Column(name = "iconpath", length = 200, nullable = false)
	private String iconPath;
	
	@Column(name = "description", length = 200, nullable = false)
	private String description;
	
	@Column(name = "version", length = 10, nullable = true)
	private String version;
	
	@ManyToOne
	@JoinColumn(name = "countryid")
	private Country country;
	
	@ManyToOne
	@JoinColumn(name = "categoryid")
	private Category category;
	
	@Column(name = "size", nullable = true)
	private long size;
	
	@Column(name = "status", length = 2, nullable = true)
	private int status = AVAILABLE;

	public int getDisPackageId() {
		return disPackageId;
	}
	public void setDisPackageId(int disPackageId) {
		this.disPackageId = disPackageId;
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
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	public ContentPackage() {
		super();
	}
	public ContentPackage(String name, String packagePath, String iconPath,
			String description, Country country, Category category, long size) {
		super();
		this.name = name;
		this.packagePath = packagePath;
		this.iconPath = iconPath;
		this.description = description;
		this.country = country;
		this.category = category;
		this.size = size;
		this.version = "1.0";
	}
	
	public static String getRelativeContentPacPath(String packageName, int countryId, int categoryId){
		return File.separator + "Content Package" + File.separator + 
				countryId + "-" + categoryId + "-" + packageName;
	}
	
	public static String getContentPackagePath(String packageName, int countryId, int categoryId){
		return DataStore.getFtpPath() + getRelativeContentPacPath(packageName, countryId, categoryId);
	}
	
	public static String getContentPacDBPath(String packageName, int countryId, int categoryId, String addonName) {
		return getRelativeContentPacPath(packageName, countryId, categoryId) + File.separator + addonName;
	}
	
	public static String getRelativeIconPath(String packageName, int countryId, int categoryId){
		return File.separator + "Brainsense_Data" + getRelativeContentPacPath(packageName, countryId, categoryId)
				+ File.separator + "ICON";
	}
	
	public static String getIconDBPath(String packageName, int countryId, int categoryId, String iconFileName) {
		String iconSaveName = "icon" + iconFileName.substring(iconFileName.lastIndexOf("."));
		return getRelativeIconPath(packageName, countryId,
				categoryId) + File.separator + iconSaveName;
	}
	
}
