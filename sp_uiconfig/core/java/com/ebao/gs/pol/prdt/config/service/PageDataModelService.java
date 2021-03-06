package com.ebao.gs.pol.prdt.config.service;

import java.util.List;

import com.ebao.gs.pol.config.datamodel.DynamicFieldConfigInfo;
import com.ebao.gs.pol.config.datamodel.PageDataModel;
import com.ebao.gs.pol.config.datamodel.PageDataModelField;
import com.ebao.gs.pol.config.datamodel.PageDataModelNode;
import com.ebao.gs.pol.config.datamodel.PageDataModelNodeInfo;

public interface PageDataModelService {
	
	public List<PageDataModel> getRootSkinBO() throws Exception;
	
	public List<PageDataModelField> getSkinBOFieldsBySkinID(int skinNodeId)throws Exception;
	
	public List<PageDataModelNode> getRootChildren(int dataModelId)throws Exception;
	
	
	public List<PageDataModelNodeInfo> getSkinboBySkinNodeID(int skinNodeId)throws Exception;
	
	public List<DynamicFieldConfigInfo> getDynamicFieldInfo(int productId,String fieldName)throws Exception;
	

}
