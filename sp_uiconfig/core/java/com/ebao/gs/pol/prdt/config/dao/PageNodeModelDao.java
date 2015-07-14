package com.ebao.gs.pol.prdt.config.dao;

import java.util.List;

import com.ebao.gs.pol.config.datamodel.DynamicFieldConfigInfo;
import com.ebao.gs.pol.config.datamodel.PageDataModel;
import com.ebao.gs.pol.config.datamodel.PageDataModelField;
import com.ebao.gs.pol.config.datamodel.PageDataModelNode;
import com.ebao.gs.pol.config.datamodel.PageDataModelNodeInfo;

public interface PageNodeModelDao {
	
	public List<PageDataModel> getAllDataModel()throws Exception;
	
	public List<PageDataModelNode> getDataNodeByPageDataModelId(int dataModelId)throws Exception;
	
	public List<PageDataModelNodeInfo> getDataNodeByFromNodeID(int fromNodeID) throws Exception;
	
	
	public List<PageDataModelField> getFieldInfoByNodeID(int nodeID)throws Exception;
	
	public List<DynamicFieldConfigInfo> getDynamicFieldByProductId(int productId,String fieldName)throws Exception;

}
