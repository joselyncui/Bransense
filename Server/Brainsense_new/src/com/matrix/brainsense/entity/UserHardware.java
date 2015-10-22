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
@Table(name = "tb_userhardware")
public class UserHardware {
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", length = 36, unique = true, nullable = false)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "userid")
	private User user;
	
	@Column(name = "macadd", length = 50, nullable = true)
	private String macAdd;
	
	@Column(name = "cpu", length = 10, nullable = true)
	private String cpu;
	
	@Column(name = "os", length = 10, nullable = true)
	private String os;
	
	@Column(name = "memory", length = 10, nullable = true)
	private String memory;
	
	@Column(name = "sdcard", length = 10, nullable = true)
	private String sdcard;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMacAdd() {
		return macAdd;
	}
	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getSdcard() {
		return sdcard;
	}
	public void setSdcard(String sdcard) {
		this.sdcard = sdcard;
	}
	public UserHardware() {
		super();
	}
	public UserHardware(String macAdd, String cpu, String os, String memory,
			String sdcard) {
		super();
		this.macAdd = macAdd;
		this.cpu = cpu;
		this.os = os;
		this.memory = memory;
		this.sdcard = sdcard;
	}
	public UserHardware(User user, String macAdd, String cpu, String os,
			String memory, String sdcard) {
		super();
		this.user = user;
		this.macAdd = macAdd;
		this.cpu = cpu;
		this.os = os;
		this.memory = memory;
		this.sdcard = sdcard;
	}
	
}
