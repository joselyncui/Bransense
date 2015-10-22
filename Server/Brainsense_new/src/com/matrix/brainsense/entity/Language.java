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

@Entity
@Table(name = "tb_language")
@XmlRootElement
public class Language {
	
	public static int UNAVAILABLE = 0;
	public static int AVAILABLE = 1;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "languageid", length = 36, unique = true, nullable = false)
	private int languageId;
	
	@Column(name = "name", length = 20, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "language", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BasePackage> basePackages;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status = 1;

	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
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
	public Language() {
		super();
	}
	public Language(String name) {
		super();
		this.name = name;
	}
	
}	
