package com.ebao.gs.pol.prdt.config.service.impl;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.SectionTemplateInfo;
import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.gs.pol.prdt.config.dao.impl.ConfigSqlExecuteDaoImpl;
import com.ebao.gs.pol.prdt.config.service.ConfigDynamicFieldService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;

public class ConfigSectionServiceImpl implements ConfigSectionService {

	private ConfigDynamicFieldService dynamicFieldService=new ConfigDynamicFieldServiceImpl();
	private ConfigSqlExecuteDao sqlDao=new ConfigSqlExecuteDaoImpl();
	@Override
	public void addConfigSection(ConfigSectionInfo sectionInfo,boolean isUpdate,String sectionTitle)
			throws Exception {
		saveConfigSectionInfo(sectionInfo,isUpdate,sectionTitle);
	} 
	
	

	@Override
	public void delConfigSection(ConfigSectionInfo sectionInfo)
			throws Exception {
		for(ConfigSectionInfo subSection:sectionInfo.getSubSectionList()){
			delConfigSection(subSection);
		}
		
		for(ConfigFieldInfo configFieldInfo:sectionInfo.getFieldList()){
			dynamicFieldService.deleteDynamicFieldConfig(configFieldInfo);
		}
		delConfigSectionInfo(Integer.parseInt(sectionInfo.getSectionId()));

	}
	
	public void saveConfigSectionInfo(ConfigSectionInfo sectionInfo,boolean isUpdate,String sectionTitle)throws Exception{
		int sectionid=sqlDao.saveSectionInfo(sectionInfo,isUpdate);
		sectionInfo.setSectionId(String.valueOf(sectionid));
		if(sectionTitle!=null&&isUpdate){
			if(sectionInfo.getParentSection()==null){
				sqlDao.updatePrdtFieldSectionName(sectionTitle, sectionInfo, false);
			}else{
				sqlDao.updatePrdtFieldSectionName(sectionTitle, sectionInfo, true);
			}
		
		}
		
	}
	
	public void delConfigSectionInfo(int sectionId)throws Exception{
		sqlDao.delSectionInfo(sectionId);
	}



	@Override
	public void changeSecitonToPage(ConfigSectionInfo sectionInfo, int pageId)
			throws Exception {
		sqlDao.changeSecitonPage(sectionInfo, pageId);
		
	}



	@Override
	public ConfigSectionInfo getSectionInfoBySectionId(int sectionId) throws Exception {
		return sqlDao.getConfigSectionInfoBySectionId(sectionId);
		
	}
	
	public List<String> getCustSectionName()throws Exception{
		
		return sqlDao.getCustomizeSection();
	}

	public List<String> getSubSectionListByCustSectionName(String sectionName)throws Exception{
		return sqlDao.getSubSectionByTemplateName(sectionName);
		
	}

	public SectionTemplateInfo getSectionNameByTag(String name,boolean isTagName)throws Exception{
		
		return sqlDao.getSectionTemplateByName(name,isTagName);
	}

}
