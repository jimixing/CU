package com.ebao.gs.pol.prdt.config.service.impl;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.gs.pol.prdt.config.dao.impl.ConfigSqlExecuteDaoImpl;
import com.ebao.gs.pol.prdt.config.service.ConfigDynamicFieldService;

public class ConfigDynamicFieldServiceImpl implements ConfigDynamicFieldService{

	private ConfigSqlExecuteDao  configSqlDao=new ConfigSqlExecuteDaoImpl();
	
	@Override
	public void addDynamicFieldConfig(ConfigFieldInfo fieldInfo,int sequence,boolean isUpdate) throws Exception{
		String strId=configSqlDao.addTStringResourceTable(fieldInfo.getDisplayLabel());
//		int fieldId=configSqlDao.addGenDynamicFieldTable(fieldInfo.getSection().getPage().getPageType(),  fieldInfo.getFieldName(), 
//				Integer.parseInt(fieldInfo.getSection().getPage().getProductId()),fieldInfo.isDynamic(),
//				fieldInfo.getSection().getSectionId(),fieldInfo.getFieldCode());
		int fieldId=configSqlDao.addGenDynamicFieldTable(fieldInfo,fieldInfo.isDynamic(),isUpdate);
		fieldInfo.setFieldId(fieldId);
		  
		int fieldPrdtId=configSqlDao.addPrdtFieldListTable(fieldInfo, strId, isUpdate);
//		int fieldPrdtId=configSqlDao.addPrdtFieldListTable(Integer.parseInt(fieldInfo.getSection().getPage().getProductId()), fieldId, 
//				fieldInfo.getSection().getPage().getPageTitle(), fieldInfo.getSection().getSectionTitle(), 
//				fieldInfo.getFieldName(), strId, fieldInfo.getDisplayType(), 
//				fieldInfo.getDefaultValue(), sequence, fieldInfo.getTableName(),
//				fieldInfo.getSection().getPage().getPageType(),fieldInfo.isDynamic());
		
//		if(fieldInfo.isDynamic()){

//			configSqlDao.addGsSpFieldList(fieldId, fieldInfo.getFieldName(), fieldInfo.getSection().getPage().getPageType());
//		if(fieldInfo.isDynamic()) 
	//		configSqlDao.addPrdtDynamicField(fieldId, fieldInfo.getPrdtId(), fieldPrdtId);
//		}
		
		
	} 
 
	@Override
	public void deleteDynamicFieldConfig(ConfigFieldInfo fieldInfo) throws Exception{
//		int fieldId=configSqlDao.findFieldIdFromFieldName(fieldInfo.getFieldName());
		int fieldId=fieldInfo.getFieldId();
//		configSqlDao.delTStringResourceTable(fieldId);
		configSqlDao.updateFieldSequence(fieldId);
		configSqlDao.delPrdtFieldListTable(fieldId);
		
//		configSqlDao.delPrdtDynamicField(fieldId,fieldInfo.isDynamic());
//		configSqlDao.delPrdtDynamicField(fieldId);
		configSqlDao.delGenDynamicFieldTable(fieldId,fieldInfo.isDynamic());
//		configSqlDao.delGsSpFieldList(fieldId);
	
		
	}

	@Override
	public String getFieldValueBinding(int fieldId,String skinBo,String variable) throws Exception {
	
		return configSqlDao.getModelSeqByFieldId(fieldId,skinBo,variable);
	}

	@Override
	public String getFieldSkinbo(int fieldId) throws Exception {
		// TODO Auto-generated method stub
		return configSqlDao.getFieldSkinbo(fieldId);
	}

	@Override
	public String getRootModel() throws Exception {
		// TODO Auto-generated method stub
		return configSqlDao.getRootRunTimeModel();
	}
	public String getFieldCodeInDynamicTable(String fieldName)throws Exception{
		return configSqlDao.getTableCodeByFieldName(fieldName);
	}

	public List<String> getAllFieldDisplayType()throws Exception{
		return configSqlDao.getAllFieldType();
	}
}
