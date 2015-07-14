package com.ebao.gs.pol.prdt.config;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ConfigSectionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4525887491359009888L;
	private String sectionTitle="";
	private String sectionId;
	private String sectionType;
	private String type="Form";
	private String sectionCode;
	private String sectionTemplate;
	private  String description="";
	private boolean isEdit;
	private int sectionSequence;
	private ConfigPageInfo page;
	//Block
	private String sectionName;
	private List<ConfigSectionInfo> subSectionList=new LinkedList<ConfigSectionInfo>();
	
	//Section
	private String subSectionName;
	private ConfigSectionInfo parentSection;
	private List<ConfigFieldInfo> fieldList=new LinkedList<ConfigFieldInfo>();
	
	private String dataTableProvider;
	
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	public List<ConfigFieldInfo> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<ConfigFieldInfo> fieldList) {
		this.fieldList = fieldList;
	}
	public ConfigPageInfo getPage() {
		return page;
	}  
	public void setPage(ConfigPageInfo page) {
		this.page = page;
	}
	public String getSectionType() {
		return sectionType;
	}
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
	public String getSectionTemplate() {
		return sectionTemplate;
	}
	public void setSectionTemplate(String sectionTemplate) {
		this.sectionTemplate = sectionTemplate;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public int getSectionSequence() {
		return sectionSequence;
	}
	public void setSectionSequence(int sectionSequence) {
		this.sectionSequence = sectionSequence;
	}
	public String getSubSectionName() {
		return subSectionName;
	}
	public void setSubSectionName(String subSectionName) {
		this.subSectionName = subSectionName;
	}
	
	public ConfigSectionInfo getParentSection() {
		return parentSection;
	}
	public void setParentSection(ConfigSectionInfo parentSection) {
		this.parentSection = parentSection;
	}
	public List<ConfigSectionInfo> getSubSectionList() {
		return subSectionList;
	}
	public void setSubSectionList(List<ConfigSectionInfo> subSectionList) {
		this.subSectionList = subSectionList;
	}
	public String getDataTableProvider() {
		return dataTableProvider;
	}
	public void setDataTableProvider(String dataTableProvider) {
		this.dataTableProvider = dataTableProvider;
	}
	public boolean isEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	

}