package com.matrix.brainsense.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "tb_public")
public class PublicInfo {
	public static final int ID_DURATION = 1;
	public static final int ID_SESSION_TIME = 2;
	public static final String UNIT_DAY = "day";
	public static final String UNIT_MIN = "min";
	public static final String UNIT_HOUR = "hour";
	@Id
	@Column(name = "id", length = 36, unique = true, nullable = false)
	private int id;
	
	@Column(name = "value", length = 20, nullable = false)
	private String value;
	
	@Column(name = "unit", length = 20, nullable = false)
	private String unit;
	
	@Column(name = "description", length = 200, nullable = false)
	private String description;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PublicInfo() {
		super();
	}
	public PublicInfo(String value, String description) {
		super();
		this.value = value;
		this.unit = PublicInfo.UNIT_DAY;
		this.description = description;
	}
	
}
