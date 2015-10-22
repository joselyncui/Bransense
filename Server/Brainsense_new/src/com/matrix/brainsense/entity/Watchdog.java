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
@Table(name = "tb_watchdog")
public class Watchdog {
	public static int AVAILABLE = 1;
	public static int UNAVAILABLE = 0;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "watchdogid", length = 36, unique = true, nullable = false)
	private int watchdogId;
	
	@Column(name = "packagepath", length = 200, nullable = true)
	private String packagePath;
	
	@Column(name = "size", nullable = true)
	private long size;
	
	@Column(name = "versioncode", length = 20, nullable = true)
	private String versionCode;
	
	@Column(name = "versionname", length = 20, nullable = true)
	private String versionName;
	
	@Column(name = "status", length = 2, nullable = true)
	private int status = AVAILABLE;
	
	@Column(name = "available", length = 2, nullable = false)
	private int available;
	
	public int getWatchdogId() {
		return watchdogId;
	}
	public void setWatchdogId(int watchdogId) {
		this.watchdogId = watchdogId;
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
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
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
	public Watchdog() {
		super();
	}
	public Watchdog(String packagePath, String versionCode, String versionName, int available) {
		super();
		this.packagePath = packagePath;
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.available = available;
	}
}
