package com.tool.entity;

import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@DynamicUpdate
public class Tool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String fileName;

	private String name;
	private String instruction1;
	private String instruction2;

	@Column(length = 1024)
	private String findPattern;

	// ------------------------------------
	@Column(length = 1024)
	private String findNotPattern;

	private String completeEnable; // yes no

	@Column(length = 1024)
	private String Include;

	@Column(length = 1024)
	private String Validation_UI;
	// --------------------------------------

	private String skipPausePlay;

	// Not persisted in the database, used for XML import/export
	@XmlTransient
	private String category;

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "Instruction1")
	public String getInstruction1() {
		return instruction1;
	}

	public void setInstruction1(String instruction1) {
		this.instruction1 = instruction1;
	}

	@XmlAttribute(name = "Instruction2")
	public String getInstruction2() {
		return instruction2;
	}

	public void setInstruction2(String instruction2) {
		this.instruction2 = instruction2;
	}

	@XmlAttribute(name = "FindPattern")
	public String getFindPattern() {
		return findPattern;
	}

	public void setFindPattern(String findPattern) {
		this.findPattern = truncate(findPattern, 255);
	}

	private String truncate(String value, int length) {
		if (value != null && value.length() > length) {
			return value.substring(0, length);
		}
		return value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlAttribute(name = "skip-pause-play")
	public String getSkipPausePlay() {
		return skipPausePlay;
	}

	public void setSkipPausePlay(String skipPausePlay) {
		this.skipPausePlay = skipPausePlay;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlAttribute(name = "FindNotPattern")
	public String getFindNotPattern() {
		return findNotPattern;
	}

	public void setFindNotPattern(String findNotPattern) {
		this.findNotPattern = truncate(findNotPattern, 255);
	}

	@XmlAttribute(name = "complete-enable")
	public String getCompleteEnable() {
		return completeEnable;
	}

	public void setCompleteEnable(String completeEnable) {
		this.completeEnable = completeEnable;
	}

	@XmlAttribute(name = "Include")
	public String getInclude() {
		return Include;
	}

	public void setInclude(String include) {
		Include = truncate(include, 255);
	}

	@XmlAttribute(name = "Validation_UI")
	public String getValidation_UI() {
		return Validation_UI;
	}

	public void setValidation_UI(String validation_UI) {
		Validation_UI = truncate(validation_UI, 255);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
