package com.ebao.gs.pol.prdt.config.service;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;

public interface ConfigDynamicFieldService {

	public void addDynamicFieldConfig(ConfigFieldInfo fieldInfo,int sequence,boolean isUpdate)throws Exception;
	public void deleteDynamicFieldConfig(ConfigFieldInfo fieldInfo)throws Exception;
	
	public String getFieldValueBinding(int fieldId,String skinBo,String variable)throws Exception;
	public String getFieldSkinbo(int fieldId)throws Exception;
	public String getRootModel()throws Exception;
	public String getFieldCodeInDynamicTable(String fieldName)throws Exception;
	public List<String> getAllFieldDisplayType()throws Exception;
}
  