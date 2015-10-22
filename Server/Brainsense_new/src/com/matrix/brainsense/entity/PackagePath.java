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
@Table(name = "tb_packagepath")
public class PackagePath {
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "pathid", length = 36, unique = true, nullable = false)
	private int pathId;
	
	@Column(name = "basepath", length = 200, nullable = false)
	private String basePath;
	
	@Column(name = "contentpath", length = 200, nullable = false)
	private String contentPath;

	public int getPathId() {
		return pathId;
	}
	public void setPathId(int pathId) {
		this.pathId = pathId;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getContentPath() {
		return contentPath;
	}
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}
	public PackagePath() {
		super();
	}
	public PackagePath(String basePath, String contentPath) {
		super();
		this.basePath = basePath;
		this.contentPath = contentPath;
	}
	
}
