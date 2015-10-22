package com.matrix.brainsense.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

@XmlRootElement
@Entity
@Table(name = "tb_type")
public class Type {
	public static int STATUS_AVAILABLE = 1;
	public static int STATUS_UNAVAILABLE = 0;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "typeid", length = 36, unique = true, nullable = false)
	private int typeId;
	
	@Column(name = "name", length = 20, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "type", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BasePackage> basePackages;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status;

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public List<BasePackage> getBasePackages() {
		return basePackages;
	}
	public void setBasePackages(List<BasePackage> basePackages) {
		this.basePackages = basePackages;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Type() {
		super();
	}
	public Type(String name) {
		super();
		this.name = name;
		this.status = STATUS_AVAILABLE;
	}
}
