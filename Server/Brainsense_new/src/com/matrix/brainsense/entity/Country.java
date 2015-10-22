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
@Table(name = "tb_country")
public class Country {
	
	public static int UNAVAILABLE = 0;
	public static int AVAILABLE = 1;
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "countryid", length = 36, unique = true, nullable = false)
	private int countryId;
	
	@Column(name = "name", length = 30, nullable = true)
	private String name;
	
	@Column(name = "code", length = 10, nullable = false)
	private String code;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ContentPackage> contentPackages;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status = 1;
	
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@XmlTransient
	public List<ContentPackage> getContentPackages() {
		return contentPackages;
	}
	public void setContentPackages(List<ContentPackage> contentPackages) {
		this.contentPackages = contentPackages;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Country() {
		super();
	}
	public Country(String name) {
		super();
		this.name = name;
		this.code = "0001";
	}
	
}
