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
@Table(name = "tb_category")
public class Category {
	
	public static int UNAVAILABLE = 0;
	public static int AVAILABLE = 1;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "categoryid", length = 36, unique = true, nullable = false)
	private int categoryId;
	
	@Column(name = "name", length = 30, nullable = true)
	private String name;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ContentPackage> contentPackages;
	
	@Column(name = "status", length = 2, nullable = false)
	private int status = 1;

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@XmlTransient
	public List<ContentPackage> getContentPackages() {
		return contentPackages;
	}
	public void setContentPackages(List<ContentPackage> contentPackages) {
		this.contentPackages = contentPackages;
	}
	public Category() {
		super();
	}
	public Category(String name) {
		super();
		this.name = name;
	}
	
}
