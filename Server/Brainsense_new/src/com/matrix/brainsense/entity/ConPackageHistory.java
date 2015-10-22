package com.matrix.brainsense.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@XmlRootElement
@Entity
@Table(name = "tb_conpackagehistory")
public class ConPackageHistory {
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "dispackageid", length = 36, unique = true, nullable = false)
	private int dispackageId;
	
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
	
	@OneToOne
	@JoinColumn(name = "countryid")
	private Country country;
	
	@OneToOne
	@JoinColumn(name = "categoryid")
	private Category category;
	
	@Column(name = "size", nullable = true)
	private double size;
	
	@Column(name = "status", length = 2, nullable = true)
	private int status;

	public int getDispackageId() {
		return dispackageId;
	}
	public void setDispackageId(int dispackageId) {
		this.dispackageId = dispackageId;
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
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ConPackageHistory() {
		super();
	}
	
}
