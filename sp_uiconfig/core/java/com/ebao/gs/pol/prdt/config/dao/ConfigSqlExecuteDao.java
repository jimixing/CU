package com.ebao.gs.pol.prdt.config.dao;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.SectionTemplateInfo;


public interface ConfigSqlExecuteDao {
	
	
	public String addTStringResourceTable(String stringMessage)throws Exception;
	
	public int addPrdtFieldListTable(ConfigFieldInfo fieldInfo,String strId,boolean isUpdate)throws Exception;
	
	
	public int addPrdtFieldListTable(int productId, int fieldId,
			String pageName,String sectionName,String fieldName, String strId, 
			String fieldType, String defauleValue,int sequence,String tableName,
			String mappingLevel,boolean isDynamic)throws Exception;
	   
	public int addGenDynamicFieldTable(ConfigFieldInfo fieldInfo,boolean isDynamic,boolean isUpdate)throws Exception;
	
	public void addPrdtDynamicField(int fieldId,int productId,int fieldPrdtId)throws Exception; 
	
	public void addGsSpFieldList(int fieldId,String fieldName,String mappingLevel)throws Exception;
	 
	
	public void delTStringResourceTable(int fieldId)throws Exception;
	public void  delGenDynamicFieldTable(int fieldId,boolean isDynamic) throws Exception;
	public void delGsSpFieldList(int fieldId) throws Exception;
	public void delPrdtDynamicField(int fieldId,boolean isDynamic)throws Exception;
	public void delPrdtFieldListTable(int fieldId) throws Exception;
	
	public int findFieldIdFromFieldName(String fieldName) throws Exception; 
	
	public List<String> getAllProduct()throws Exception;
	public String getProductNameById(long productId)throws Exception;
	
	public String getProductId(String productName)throws Exception;
	public List<String> getInsuredByProductId(String productId)throws Exception;
	
	public List<String> getCodeTableValue(String tableName)throws Exception;
	
	public List<String> getAllTableName()throws Exception;
	public int savePageInfo(ConfigPageInfo pageInfo,boolean isUpdate) throws Exception;
	public void delPageInfo(int pageId)throws Exception;
	public int saveSectionInfo(ConfigSectionInfo sectionInfo,boolean isUpdate)throws Exception;
	public void delSectionInfo(int sectionid)throws Exception;
	public List<ConfigPageInfo> getPageInfoByProduct(String productName) throws Exception;
	
	public List<ConfigFieldInfo> getFieldInfoBySectionId(ConfigSectionInfo section) throws Exception;
	
	public void changeSecitonPage(ConfigSectionInfo section,int pageId)throws Exception;
	
	public ConfigPageInfo getConfigPageInfoByPageId(int pageID)throws Exception;
	public ConfigSectionInfo getConfigSectionInfoBySectionId(int sectionId) throws Exception;
	
	public void updatePrdtFieldPageName(String oldTitle,ConfigPageInfo newPageInfo)throws Exception;
	public void updatePrdtFieldSectionName(String oldTitle,ConfigSectionInfo newSectionInfo,boolean isSubsection)throws Exception;
	
	public void updateFieldSequence(int fieldID)throws Exception;
	
	public List<String> getCustomizeSection()throws Exception;
	
	public String getModelSeqByFieldId(int fieldId,String skinBo,String variable)throws Exception;
	
	public List<String> getSubSectionByTemplateName(String templateName)throws Exception;
	public SectionTemplateInfo getSectionTemplateByName(String name,boolean isTagName)throws Exception;
	public String getFieldSkinbo(int fieldId) throws Exception ;
	public String getRootRunTimeModel()throws Exception;
	public String getTableCodeByFieldName(String fieldName)throws Exception;
	
	public List<String> getAllFieldType()throws Exception;


	
 
}
