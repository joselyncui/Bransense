package com.matrix.brainsense.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.matrix.brainsense.util.TimeUtil;

@XmlRootElement
@Entity
@Table(name = "tb_device")
public class Device {
	
	public static int APPROVED = 0;
	public static int DENIED = 1;
	public static int WAITING = 2;
	public static int OVER_TIME = 3;
	public static int ONLINE = 1;
	public static int OFFLINE = 0;
	public static int KEYCODE_UNEXIST = 4;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", length = 36, unique = true, nullable = false)
	private int id;
	
	@Column(name = "deviceid", length = 50, nullable = true)
	private String deviceId;
	
	@Column(name = "macid", length = 50, nullable = true)
	private String macId;
	
	@OneToOne
	@JoinColumn(name = "codeid")
	private KeyCode keyCode;
	
	@OneToOne
	@JoinColumn(name = "userid", referencedColumnName = "userid")
	private User user;
	
	@Column(name = "online", length = 2, nullable = false)
	private int online;
	
	@Column(name = "datetime", length = 20, nullable = false)
	private String datetime;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status = 2;
	
	@Column(name = "times", length = 10, nullable = false)
	private int times = 0;
	
	@Column(name = "duration", length = 20, nullable = false)
	private String duration;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public KeyCode getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(KeyCode keyCode) {
		this.keyCode = keyCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Device() {
		super();
	}
	public Device(String deviceId, String macId, KeyCode keyCode) {
		super();
		this.deviceId = deviceId;
		this.macId = macId;
		this.keyCode = keyCode;
		this.datetime = TimeUtil.convert(new Date());
		this.status = Device.WAITING;
		this.times = 0;
		this.online = Device.ONLINE;
	}
	
}
