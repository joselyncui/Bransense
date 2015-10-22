package com.matrix.brainsense.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.matrix.brainsense.util.TimeUtil;

@XmlRootElement
@Entity
@Table(name = "tb_user")
public class User {
	
	public static int NO_MATCH = 5;
	public static int NEW_USER = 0;
	public static int WITHOUT_BASIC = 1;
	public static int WITHOUT_XBMC = 2;
	public static int WITHOUT_CONTENT = 3;
	public static int INSTALLED_ALL = 4;
	public static int UNINSTALL = 0;
	public static int INSTALLED = 1;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "userid", length = 36, unique = true, nullable = false)
	private int userId;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Device device;
	
	@Column(name = "name", length = 20, nullable = true)
	private String name;
	
	@Column(name = "email", length = 30, nullable = true)
	private String email;
	
	@Column(name = "datetime", length = 20, nullable = true)
	private String dateTime;
	
	@Column(name = "statuswithbase", length = 2, nullable = false)
	private int statusWithBase = 0;
	
	@Column(name = "statuswithxbmc", length = 2, nullable = false)
	private int statusWithXbmc = 0;
	
	@Column(name = "statuswithcontent", length = 2, nullable = false)
	private int statusWithContent = 0;
	
	@Column(name = "localpathbase", length = 200, nullable = false)
	private String localPathBase;
	
	@Column(name = "localpathcontent", length = 200, nullable = false)
	private String localPathContent;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserHardware userHardware;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@XmlTransient
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public int getStatusWithBase() {
		return statusWithBase;
	}
	public void setStatusWithBase(int statusWithBase) {
		this.statusWithBase = statusWithBase;
	}
	public int getStatusWithXbmc() {
		return statusWithXbmc;
	}
	public void setStatusWithXbmc(int statusWithXbmc) {
		this.statusWithXbmc = statusWithXbmc;
	}
	public int getStatusWithContent() {
		return statusWithContent;
	}
	public void setStatusWithContent(int statusWithContent) {
		this.statusWithContent = statusWithContent;
	}
	public String getLocalPathBase() {
		return localPathBase;
	}
	public void setLocalPathBase(String localPathBase) {
		this.localPathBase = localPathBase;
	}
	public String getLocalPathContent() {
		return localPathContent;
	}
	public void setLocalPathContent(String localPathContent) {
		this.localPathContent = localPathContent;
	}
	@XmlTransient
	public UserHardware getUserHardware() {
		return userHardware;
	}
	public void setUserHardware(UserHardware userHardware) {
		this.userHardware = userHardware;
	}
	public User() {
		super();
	}
	public User(Device device, String name, String email) {
		super();
		this.device = device;
		this.name = name;
		this.email = email;
		this.dateTime = TimeUtil.convert(new Date());
		this.statusWithBase = User.UNINSTALL;
		this.statusWithContent = User.UNINSTALL;
		this.statusWithXbmc = User.UNINSTALL;
		this.localPathBase = "";
		this.localPathContent = "";
	}
	
}
