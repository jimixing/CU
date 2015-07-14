package com.ebao.gs.pol.prdt.config.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.ebao.gs.pol.config.datamodel.DynamicFieldConfigInfo;
import com.ebao.gs.pol.config.datamodel.PageDataModel;
import com.ebao.gs.pol.config.datamodel.PageDataModelField;
import com.ebao.gs.pol.config.datamodel.PageDataModelNode;
import com.ebao.gs.pol.config.datamodel.PageDataModelNodeInfo;
import com.ebao.gs.pol.prdt.config.dao.PageNodeModelDao;
import com.ebao.pub.util.DBean;

public class PageNodeModelDaoImpl implements PageNodeModelDao {

	@Override
	public List<PageDataModel> getAllDataModel() throws Exception {
		List<PageDataModel> dataModelList=new LinkedList<PageDataModel>();
		String sql="select data_model_id,data_model_name from t_uic_page_datamodel";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				PageDataModel pageDataModel=new PageDataModel();
				int dataModelId=rs.getInt("data_model_id");
				String dataModelName=rs.getString("data_model_name");
				pageDataModel.setDataModelId(dataModelId);
				pageDataModel.setDataModelName(dataModelName);
				dataModelList.add(pageDataModel);
			}
			return dataModelList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}

	@Override
	public List<PageDataModelNode> getDataNodeByPageDataModelId(int dataModelId)
			throws Exception {
		List<PageDataModelNode> dataModelNodeList=new LinkedList<PageDataModelNode>();
		
		String sql="select node_id,class_name from t_uic_page_datamodel_node where page_model_id="+dataModelId;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				PageDataModelNode pageDataModelNode=new PageDataModelNode();
				int dataModelNodeId=rs.getInt("node_id");
				String dataModelName=rs.getString("class_name");
				pageDataModelNode.setNodeId(dataModelNodeId);
				pageDataModelNode.setClassName(dataModelName);
				dataModelNodeList.add(pageDataModelNode);
			}
			return dataModelNodeList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}

	@Override
	public List<PageDataModelNodeInfo> getDataNodeByFromNodeID(int fromNodeID)
			throws Exception {
		List<PageDataModelNodeInfo> dataModelNodeInfoList=new LinkedList<PageDataModelNodeInfo>();
		
		String sql="select to_node_id,display_name from t_uic_page_datamodel_node_info where from_node_id="+fromNodeID;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				PageDataModelNodeInfo pageDataModelNodeInfo=new PageDataModelNodeInfo();
				int dataModelNodeId=rs.getInt("to_node_id");
				String dataModelName=rs.getString("display_name");
				pageDataModelNodeInfo.setFromNode(fromNodeID);
				pageDataModelNodeInfo.setToNode(dataModelNodeId);
				pageDataModelNodeInfo.setDisplayName(dataModelName);
				dataModelNodeInfoList.add(pageDataModelNodeInfo);
			}
			return dataModelNodeInfoList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		
		
		
		
	}

	@Override
	public List<PageDataModelField> getFieldInfoByNodeID(int nodeID)
			throws Exception {
		List<PageDataModelField> dataModelNodeInfoList=new LinkedList<PageDataModelField>();
		
		String sql="select field_id,field_display_name,table_name,where_clause from t_uic_page_datamodel_field where node_id="+nodeID;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				PageDataModelField pageDataModelNodeInfo=new PageDataModelField();
				int fieldId=rs.getInt("field_id");
				String dataModelName=rs.getString("field_display_name");
				String tableName=rs.getString("table_name");
				String whereClause=rs.getString("where_clause");
				pageDataModelNodeInfo.setFieldDisplayName(dataModelName);
				pageDataModelNodeInfo.setFieldId(fieldId);
				pageDataModelNodeInfo.setTableName(tableName);
				pageDataModelNodeInfo.setWhereClause(whereClause);
				
				pageDataModelNodeInfo.setNodeId(nodeID);
				dataModelNodeInfoList.add(pageDataModelNodeInfo);
			}
			return dataModelNodeInfoList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		
	}

	@Override
	public List<DynamicFieldConfigInfo> getDynamicFieldByProductId(int productId,String fieldName)
			throws Exception {
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sql=null;
	    if(fieldName==null||fieldName.isEmpty()){
	    	  sql="select * from T_GEN_DYNAMIC_FIELD t1, t_prdt_dynamic_field t2 where t1.field_id=t2.field_id and t1.table_code is not null and t2.product_id="+productId;
	    }else{
	    	sql="select * from T_GEN_DYNAMIC_FIELD t1, t_prdt_dynamic_field t2 where t1.field_id=t2.field_id and t1.table_code is not null and t2.product_id="+productId +" and t1.field_name='"+fieldName+"'";
	    }
	 
	    List<DynamicFieldConfigInfo> dynamicFieldList=new LinkedList<DynamicFieldConfigInfo>();
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				DynamicFieldConfigInfo field=new DynamicFieldConfigInfo();
				String field_Name=rs.getString("FIELD_NAME");
				String tableCode=rs.getString("TABLE_CODE");
				String fieldCode=rs.getString("FIELD_CODE");
				int insuredCateId=rs.getInt("INSURED_CATE_CODE");
				int productid=rs.getInt("PRODUCT_ID");
				int fieldID=rs.getInt("FIELD_ID");
				String displayType=rs.getString("disp_type");
				String parentTableCode=rs.getString("parent_table_code");
				String whereClause=rs.getString("where_Clause");
				field.setFieldName(field_Name);
				field.setFieldId(fieldID);
				field.setProductId(productid);
				field.setFieldMappingName(fieldCode);
				field.setInsuredcateID(insuredCateId);
				field.setDispType(displayType);
				field.setParentTableCode(parentTableCode);
				field.setWhereClause(whereClause);
				
				field.setTableCode(tableCode);
				if(tableCode.equals("T_GEN_POLICY_INFO")){
					field.setConfigLevel("Policy Information");
					
				}else if(tableCode.equals("T_INSURED_LIST")){
					field.setConfigLevel("Insured Information");
				}else if(tableCode.equals("T_POLICY_CT")){
					field.setConfigLevel("CT Information");
					
				}else if(tableCode.equals("T_POLICY_CT_ACCE")){
					field.setConfigLevel("Benefit Information");
				}
				dynamicFieldList.add(field);
				
				
			}
			
			
		}catch (Exception e) {
		     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		return dynamicFieldList;
	}

}
