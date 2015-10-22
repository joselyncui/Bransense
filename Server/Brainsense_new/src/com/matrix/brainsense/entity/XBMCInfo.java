package com.matrix.brainsense.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@XmlRootElement
@Entity
@Table(name = "tb_xbmc")
public class XBMCInfo {
	
	public static final int AVAILABLE = 1;
	public static final int UNAVAILABLE = 0;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "xbmcid", length = 36, unique = true, nullable = false)
	private int xbmcId;
	
	@Column(name = "packagepath", length = 200, nullable = false)
	private String packagePath;
	
	@Column(name = "size", nullable = true)
	private double size;
	
	@Column(name = "versioncode", length = 20, nullable = false)
	private String versionCode;
	
	@Column(name = "versionname", length = 20, nullable = false)
	private String versionName;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status = AVAILABLE;
	
	@Column(name = "available", length = 2, nullable = false)
	private int available;

	public int getXbmcId() {
		return xbmcId;
	}
	public void setXbmcId(int xbmcId) {
		this.xbmcId = xbmcId;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName(){
		return this.versionName;
	}
	public void setVersionName(String versionName){
		this.versionName = versionName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public XBMCInfo() {
		super();
	}
	public XBMCInfo(String packagePath, String versionCode, String versionName) {
		super();
		this.packagePath = packagePath;
		this.versionCode = versionCode;
		this.versionName = versionName;
	}
	public XBMCInfo(String packagePath, String versionCode, String versionName, int available) {
		super();
		this.packagePath = packagePath;
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.available = available;
	}
	
}
