package com.ebao.gs.pol.prdt.config.service;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.SectionTemplateInfo;

public interface ConfigSectionService {
	
	public void addConfigSection(ConfigSectionInfo sectionInfo,boolean isUpdate,String sectionTitle)throws Exception;
	public void delConfigSection(ConfigSectionInfo sectionInfo)throws Exception;
	public void changeSecitonToPage(ConfigSectionInfo sectionInfo,int pageId)throws Exception;
	public ConfigSectionInfo getSectionInfoBySectionId(int sectionId) throws Exception;

	public List<String> getCustSectionName()throws Exception;
	public List<String> getSubSectionListByCustSectionName(String sectionName)throws Exception;
	public SectionTemplateInfo getSectionNameByTag(String tagName,boolean isTagName)throws Exception;

}
 