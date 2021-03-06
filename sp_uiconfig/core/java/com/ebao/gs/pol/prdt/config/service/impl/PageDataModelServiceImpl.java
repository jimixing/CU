package com.ebao.gs.pol.prdt.config.service.impl;

import java.util.List;

import com.ebao.gs.pol.config.datamodel.DynamicFieldConfigInfo;
import com.ebao.gs.pol.config.datamodel.PageDataModel;
import com.ebao.gs.pol.config.datamodel.PageDataModelField;
import com.ebao.gs.pol.config.datamodel.PageDataModelNode;
import com.ebao.gs.pol.config.datamodel.PageDataModelNodeInfo;
import com.ebao.gs.pol.prdt.config.dao.PageNodeModelDao;
import com.ebao.gs.pol.prdt.config.dao.impl.PageNodeModelDaoImpl;
import com.ebao.gs.pol.prdt.config.service.PageDataModelService;

public class PageDataModelServiceImpl implements PageDataModelService {

	
	
	private PageNodeModelDao dao=new PageNodeModelDaoImpl();
	
	@Override
	public List<PageDataModel> getRootSkinBO() throws Exception {
		return dao.getAllDataModel();
	}

	@Override
	public List<PageDataModelField> getSkinBOFieldsBySkinID(int skinNodeId)
			throws Exception {
		
		return dao.getFieldInfoByNodeID(skinNodeId);
	}

	@Override
	public List<PageDataModelNode> getRootChildren(int dataModelId) throws Exception {
		// TODO Auto-generated method stub
		return dao.getDataNodeByPageDataModelId(dataModelId);
	}

	@Override
	public List<PageDataModelNodeInfo> getSkinboBySkinNodeID(int skinNodeId)
			throws Exception {
		
		return dao.getDataNodeByFromNodeID(skinNodeId);
	}

	@Override
	public List<DynamicFieldConfigInfo> getDynamicFieldInfo(int productId,String fieldName)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.getDynamicFieldByProductId(productId,fieldName);
	}

}
