package com.matrix.brainsense.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@XmlRootElement
@Entity
@Table(name = "tb_keycode")
public class KeyCode {
	
	public static int KEYCODE_UNEXIST = 0;
	public static int KEYCODE_BE_USED = 1;
	public static int KEYCODE_UNMATCH = 2;
	public static int SUCCESS = 3;
	public static int ACTIVE = 1;
	public static int DEACTIVE = 0;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", length = 36, unique = true, nullable = false)
	private int id;
	
	@OneToOne(mappedBy = "keyCode")
	private Device device;
	
	@Column(name = "keycodeid", length = 50, nullable = true)
	private String keyCodeId;
	
	@Column(name = "active", length = 2, nullable = false)
	private int active = ACTIVE;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getKeyCodeId() {
		return keyCodeId;
	}
	public void setKeyCodeId(String keyCodeId) {
		this.keyCodeId = keyCodeId;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public KeyCode() {
		super();
	}
	public KeyCode(String keyCodeId) {
		super();
		this.keyCodeId = keyCodeId;
	}
	
}
