package com.ebao.gs.pol.prdt.config.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.SectionTemplateInfo;
import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.pub.util.DBean;

public class ConfigSqlExecuteDaoImpl implements ConfigSqlExecuteDao{



	@Override
	public String addTStringResourceTable(String stringMessage) throws Exception{
		String command="SELECT MAX(TO_NUMBER(substr(STR_ID, length('MSG_GS_SP_')+1))) as maxStr_id"
				+" FROM t_String_Resource WHERE substr(STR_ID,length('MSG_GS_SP_')+1,1) IN ('0','1','2','3','4','5','6','7','8','9')"
				+" and substr(STR_ID, length('MSG_GS_SP_')+1) not like '%/_%' escape '/'"
				+" AND STR_ID LIKE substr('MSG_GS_SP_',1,length('MSG_GS_SP_')-1)||'/_%' ESCAPE '/'"
				+" AND TO_NUMBER(substr(STR_ID, length('MSG_GS_SP_')+1)) < 99999999";
		if(stringMessage.contains("'")){
			stringMessage=stringMessage.replace("'", "'||chr(39)||'");
		} 
		
		String sql="select t.str_id str_id from t_String_Resource t where t.lang_id='011' and t.str_data='"+stringMessage+"'";
	    DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String newValue=null;
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 String str_id=rs.getString("str_id");
		    	 if(str_id.startsWith("MSG_"))return str_id;
		     } 
		     ps.close();
		     rs.close();
		     ps=conn.prepareStatement(command);
			rs=ps.executeQuery();
			int maxValue=0;
			while(rs.next()){
				maxValue=rs.getInt("maxStr_id");
			}
		
			maxValue+=1;
			newValue="MSG_GS_SP_"+String.valueOf(maxValue);
			command="insert into t_string_resource Values('"+newValue+"', '011','"+stringMessage+"','T_MESSAGE',SYSDATE)";
			ps.execute(command);
			return 	newValue;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		
	
	}
	
	public int addPrdtFieldListTable(ConfigFieldInfo fieldInfo,String strId,boolean isUpdate)throws Exception{
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;  
	    int maxValue=0;
	    String sql="select max(FIELD_PRDT_ID) as maxFieldPrdtId from t_gs_sp_prdt_field_list";
	    
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=  ps.executeQuery(); 
		     while(rs.next()){
			   maxValue=rs.getInt("maxFieldPrdtId"); 
		     }
		     ps.close();
		     rs.close();
		     if(isUpdate){
		    	 sql="delete from t_gs_sp_prdt_field_list t where t.field_id="+fieldInfo.getFieldId();
		    	 ps=conn.prepareStatement(sql);
		    	 ps.execute();
		    	 ps.close();
		     } 
		     
		     maxValue++;
		     sql="insert into t_gs_sp_prdt_field_list (FIELD_PRDT_ID, PRODUCT_ID, INSURED_CATE, INTEREST_ID, FIELD_ID, DATA_TYPE, STATE, PROGRAM, FIELD_PRDT_ID_OLD, ISO_FIELD_NAME, CT_CODE, PAGE_NAME, SECTION_NAME, SUB_SECTION_NAME, FIELD_CODE, FIELD_LABEL, OBJECT_TYPE, RISK_TYPE, FIELD_NAME, FIELD_TYPE, SEQUENCE, "
		     		+ "COLSPAN, TEXT_AREA_ROWS, TEXT_AREA_COLS, RATING_REQUIRED, QUOTE_READONLY, QUOTE_READONLY_CONDITION, "
		     		+ "QUOTE_REQUIRED, QUOTE_REQUIRED_CONDITION, QUOTE_DISPLAY, QUOTE_DISPLAY_CONDITION, POLICY_READONLY, "
		     		+ "POLICY_READONLY_CONDITION, POLICY_REQUIRED, POLICY_REQUIRED_CONDITION, POLICY_DISPLAY, POLICY_DISPLAY_CONDITION,"
		     		+ " ENDO_READONLY, RENEWAL_READONLY, NEED_CLEAR_VALUE, DISPLAY_BY_ROLES_CONDITION, READONLY_BY_ROLES_CONDITION, "
		     		+ "CALCULATION_RULE, ON_CHANGE_LISTENER, VALUE_BINDING, UPDATE_FIELD_IDS, CODE_TABLE_TABLE_NAME, CODE_TABLE_DISPLAY_TYPE, "
		     		+ "CODE_TABLE_WHERE_CLAUSE, CODE_TABLE_ORDER_BY, INPUT_WIDTH, HELP_TEXT, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH,"
		     		+ " ERROR_MSG, ACTION, ACTION_LISTENER, AJAX_ENABLED, COLUMN_WIDTH, CHILD_FIELD_CODE, PARENT_FIELD_CODE, FOREIGN_KEY, "
		     		+ "IO, INST_CODE, VALIDATION_GROUP, ALLOW_DECIMALS, DECIMAL_PRECISION, ALLOW_NEGATIVE, TABLE_TITLE_ALIGN, TABLE_COLUMN_ALIGN, "
		     		+ "ENDO_READONLY_CONDITION, EMPTY_TEXT, DEFAULT_VALUE,"
		     		+ "VISIBLE,VISIBLE_EXPRESSION,READONLY,READONLY_EXPRESSION,REQUIRED,REQUIRED_EXPRESSION,NO_SELECTION_LOAD,FORMAT,ON_CREATE,ON_ACTIVE,ON_REFRESH,ON_BLUR,ON_CHANGE,ON_BUTTON_CLICK)"
		     		+"values (?, ?, null, null, ?, '', ?, '', null, '', '', ?, ?, ?, ?, ?, '', '', ?, ?, ?, ?, null, null, '', '', '', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ?, '', ?, ?, ?, ?, '', '', null, null, '', '', '', '', '', '1', '', ?, ?, ?, ?, '', '', '', null, '', '', '', '', '', ?"
		     		+",?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		     ps=conn.prepareStatement(sql);
		     int par=1;
		     ps.setInt(par++, maxValue);
		     
		     if(fieldInfo.getSection().getPage()!=null){
		    	 ps.setInt(par++, Integer.parseInt(fieldInfo.getSection().getPage().getProductId()));
			     ps.setInt(par++, fieldInfo.getFieldId());
			     ps.setInt(par++, 37);
		    	 ps.setString(par++, fieldInfo.getSection().getPage().getPageName());
		    	 ps.setString(par++,fieldInfo.getSection().getSectionName());
		    	 ps.setNull(par++, java.sql.Types.NULL);
	//	    	 ps.setString(par++,fieldInfo.getSection().getSectionTitle());
		     }else{
		    	 ps.setInt(par++, Integer.parseInt(fieldInfo.getSection().getParentSection().getPage().getProductId()));
			     ps.setInt(par++, fieldInfo.getFieldId());
			     ps.setInt(par++, 37);
		    	 ps.setString(par++, fieldInfo.getSection().getParentSection().getPage().getPageName()); 
		    	 ps.setString(par++,fieldInfo.getSection().getParentSection().getSectionName());
		    	 ps.setString(par++,fieldInfo.getSection().getSubSectionName());
		     }

		     String fieldCode=fieldInfo.getFieldName().replace(" ", "_");
		     ps.setString(par++,fieldCode);
		     ps.setString(par++,strId);
		     ps.setString(par++,fieldInfo.getFieldName());
		     String displayType=fieldInfo.getDisplayType();
		     if(fieldInfo.getDisplayType().equals("CASCADETABLE")){
		    	 displayType="UNITEDTABLE";
		     }
		     ps.setString(par++,displayType);
		     ps.setInt(par++,fieldInfo.getSequence());
		     ps.setInt(par++,fieldInfo.getColspan());
		     
		     String binding=fieldInfo.getValueBinding()==null?"":fieldInfo.getValueBinding();

		     ps.setString(par++,binding);
		     if(fieldInfo.getTableName()!=null){ 
		    	 ps.setString(par++, fieldInfo.getTableName());
		     }else{
		    	 ps.setString(par++, null); 
		     }
		     ps.setString(par++, fieldInfo.getCodeTabelDisplayType());
		     ps.setString(par++, fieldInfo.getWhereClause());
		     ps.setString(par++, fieldInfo.getOrderBy());
		     ps.setString(par++, fieldInfo.getChildItem());
		     ps.setString(par++, fieldInfo.getParentItem());
		     ps.setString(par++, fieldInfo.getUnitedKey());
		     ps.setString(par++, fieldInfo.getIo());
		     ps.setString(par++,fieldInfo.getDefaultValue());
		     
		     
		     
		     // add new column
		     ps.setBoolean(par++, fieldInfo.isVisible());
		     
		     ps.setString(par++, fieldInfo.getVisibleEx());
		     
		     ps.setBoolean(par++, fieldInfo.isReadOnly());
		     ps.setString(par++, fieldInfo.getReadOnlyEx());
		     
		     ps.setBoolean(par++, fieldInfo.isRequired());
		     ps.setString(par++, fieldInfo.getRequieredEx());
		          
		     ps.setBoolean(par++, fieldInfo.isNoSelectionLoad());
		     ps.setString(par++, fieldInfo.getFormat());
		     
		     ps.setString(par++, fieldInfo.getOnCreate());
		     ps.setString(par++, fieldInfo.getOnActive());
		     ps.setString(par++, fieldInfo.getOnRefresh());
		     ps.setString(par++, fieldInfo.getOnBlur());
		     ps.setString(par++, fieldInfo.getOnChange());
		     ps.setString(par++, fieldInfo.getOnButtonClick());
		     
		     ps.execute();

			}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		return maxValue;
		
		
		
	}
	

	@Override
	public int addPrdtFieldListTable(int productId, int fieldId,
			String pageName,String sectionName,String fieldName, String strId, 
			String fieldType, String defauleValue,int sequence,
			String tableName,String mappingLevel,boolean isDynamic) throws Exception{
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null; 
	    int maxValue=0;
	    String sql="select max(FIELD_PRDT_ID) as maxFieldPrdtId from t_gs_sp_prdt_field_list";
	    
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=  ps.executeQuery(); 
		     while(rs.next()){
			   maxValue=rs.getInt("maxFieldPrdtId"); 
		     }
		     ps.close();
		     rs.close();
		     maxValue++;
		     sql="insert into t_gs_sp_prdt_field_list (FIELD_PRDT_ID, PRODUCT_ID, INSURED_CATE, INTEREST_ID, FIELD_ID, DATA_TYPE, STATE, PROGRAM, FIELD_PRDT_ID_OLD, ISO_FIELD_NAME, CT_CODE, PAGE_NAME, SECTION_NAME, SUB_SECTION_NAME, FIELD_CODE, FIELD_LABEL, OBJECT_TYPE, RISK_TYPE, FIELD_NAME, FIELD_TYPE, SEQUENCE, "
		     		+ "COLSPAN, TEXT_AREA_ROWS, TEXT_AREA_COLS, RATING_REQUIRED, QUOTE_READONLY, QUOTE_READONLY_CONDITION, "
		     		+ "QUOTE_REQUIRED, QUOTE_REQUIRED_CONDITION, QUOTE_DISPLAY, QUOTE_DISPLAY_CONDITION, POLICY_READONLY, "
		     		+ "POLICY_READONLY_CONDITION, POLICY_REQUIRED, POLICY_REQUIRED_CONDITION, POLICY_DISPLAY, POLICY_DISPLAY_CONDITION,"
		     		+ " ENDO_READONLY, RENEWAL_READONLY, NEED_CLEAR_VALUE, DISPLAY_BY_ROLES_CONDITION, READONLY_BY_ROLES_CONDITION, "
		     		+ "CALCULATION_RULE, ON_CHANGE_LISTENER, VALUE_BINDING, UPDATE_FIELD_IDS, CODE_TABLE_TABLE_NAME, CODE_TABLE_DISPLAY_TYPE, "
		     		+ "CODE_TABLE_WHERE_CLAUSE, CODE_TABLE_ORDER_BY, INPUT_WIDTH, HELP_TEXT, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH,"
		     		+ " ERROR_MSG, ACTION, ACTION_LISTENER, AJAX_ENABLED, COLUMN_WIDTH, CHILD_FIELD_CODE, PARENT_FIELD_CODE, FOREIGN_KEY, "
		     		+ "IO, INST_CODE, VALIDATION_GROUP, ALLOW_DECIMALS, DECIMAL_PRECISION, ALLOW_NEGATIVE, TABLE_TITLE_ALIGN, TABLE_COLUMN_ALIGN, "
		     		+ "ENDO_READONLY_CONDITION, EMPTY_TEXT, DEFAULT_VALUE)"
		     		+"values (?, ?, null, null, ?, '', ?, '', null, '', '', ?, ?, ?, ?, ?, '', '', ?, ?, ?, 1, null, null, '', '', '', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ?, '', ?, '4', '', '', '', '', null, null, '', '', '', '', '', '1', '', '', '', '', '', '', '', '', null, '', '', '', '', '', ?)";
			
		     ps=conn.prepareStatement(sql);
		     ps.setInt(1, maxValue);
		     ps.setInt(2, productId);
		     ps.setInt(3, fieldId);
		     ps.setInt(4, 37);
		     ps.setString(5, pageName);
		     ps.setString(6,sectionName);
		     ps.setString(7,sectionName);
		     
		     String fieldCode=fieldName.replace(" ", "_");
		     ps.setString(8,fieldCode);
		     ps.setString(9,strId);
		     ps.setString(10,fieldName);
		     ps.setString(11,fieldType);
		     ps.setInt(12,sequence);
//		     String mappingName=null;
		     String binding="";
//		     String binding="policyModel."+fieldName;
//		     if(isDynamic){
//		    	 if(mappingLevel.equals("insured")){
//			    	 if(insuredType.equals("Vehicle Insured")){
//				    	 mappingName="vehicle";
//				     }else if(insuredType.equals("Person Insured")){
//				    	 mappingName="person";
//				     }else {
//				    	 mappingName="address";
//				     }
//			     }else if(mappingLevel.equals("policy")){
//			    	 mappingName="policyModel";
//			    	 
//			     }
//		    	 binding=mappingName+".properties."+fieldName;
//		     }
		     ps.setString(13,binding);
		     if(tableName!=null){ 
		    	 ps.setString(14, tableName);
		     }else{
		    	 ps.setString(14, "''"); 
		     }
		     ps.setString(15,defauleValue);
		     ps.execute();
		     return maxValue;
			}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	    
		
	}

	@Override
	public int addGenDynamicFieldTable(ConfigFieldInfo fieldInfo,boolean isDynamic,boolean isUpdate)throws Exception {
		 	
			if(isUpdate){
				return fieldInfo.getFieldId();
			}
		
			DBean db = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null; 
		    StringBuffer sb=new StringBuffer();
		    String unUsedField=""+fieldInfo.getFieldCode();
		    int maxFieldId=0;

			try {
				db = new DBean();
				 db.connect();
			     Connection  conn = db.getConnection();
			     
//			     if(fieldInfo.getFieldId()>0){
//			    	 maxFieldId=fieldInfo.getFieldId();
//			     }else{
			    	 sb.append("select T_FIELD_ID_SEQUENCE.nextval  as maxFieldId from t_config_field t");
				     ps=conn.prepareStatement(sb.toString());
				    rs= ps.executeQuery();
				    while(rs.next()){
				    	maxFieldId=rs.getInt("maxFieldId");
				    	break;
				    }
				    maxFieldId++;
				    ps.close();
				    rs.close();
//			     }
			     int isDymic=1;
			     if(isDynamic){
			    	 isDymic=2;
			     }

	    			    
			    String sql="insert into t_config_field (FIELD_ID, FIELD_NAME,DYNAMIC_TYPE, FIELD_CODE, INSERT_TIME, SECTION_ID)"+
			    "values ("+maxFieldId+", '"+fieldInfo.getFieldName()+"'," +isDymic+ ",'"+unUsedField.toLowerCase()+"',sysdate, "+fieldInfo.getSection().getSectionId()+")";
     
			    ps=conn.prepareStatement(sql);
			    ps.execute();
			}catch (Exception e) {
			     throw e;
			} finally {
			DBean.closeAll(rs, ps, db);
		}
		return maxFieldId;
	}

	@Override
	public void addPrdtDynamicField(int fieldId, int productId,
			int fieldPrdtId)throws Exception {
		String sb="delete from t_prdt_dynamic_field t where t.field_id="+fieldId;
		
		
		String sql="insert into t_prdt_dynamic_field (FIELD_ID, PRODUCT_ID, CT_APPLY_TO_INSURED_TYPE, FIELD_PRDT_ID, DISPLAY_IN_QUERY)"+
				"values ("+fieldId+", "+productId+", 1,"+ fieldPrdtId+", 1)";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try { 
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sb);
		     ps.execute();
		     conn.close();
		     ps.close();
		     conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
		     
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	}

	@Override
	public void addGsSpFieldList(int fieldId, String fieldName,
			String mappingLevel)throws Exception {
		String sql="insert into t_gs_sp_field_list (FIELD_ID, FIELD_CODE, FIELD_LEVEL, FIELD_DESC, DYNAMIC_FIELD, SOABO_VALUE, BO_VALUE, TABLE_FIELD, DEF_CODETABLE, DEF_WHERECLAUSE, DATA_NOTE)"+
		"values ("+fieldId+", '"+fieldName+"', '"+mappingLevel+"', '', null, '', '', '', '', '', '')";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	}


	@Override
	public void delTStringResourceTable(int fieldId) throws Exception {
		String sql="select t.field_label from t_gs_sp_prdt_field_list t where t.field_id ="+fieldId;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String fieldLabel=null;
	    try {
			db = new DBean();
			 db.connect();
			 ps = db.getConnection().prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 fieldLabel=rs.getString("field_label");
		     }
		     if(fieldLabel==null){
		    	 throw new Exception("No such recoder");
		     }
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	    sql="delete from t_string_resource t where t.str_id='"+fieldLabel+"'";
	    try {
			db = new DBean();
			 db.connect();
			 ps = db.getConnection().prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    } 
	}

	public int findFieldIdFromFieldName(String fieldName) throws Exception{
		String sql="select t.field_id from t_config_field t where t.field_name ='"+fieldName+"'";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null; 
	    try {
			db = new DBean(); 
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs= ps.executeQuery();
		     while(rs.next()){
		    	 int field_id=rs.getInt("field_id");
		    	 return field_id;
		     }
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		return 0;

	}
	
	
	@Override
	public void delGenDynamicFieldTable(int fieldId,boolean isDynamic) throws Exception {
		
//		if(isDynamic)return;
		String sql="delete from t_config_field t where t.field_id=" +fieldId;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	}

	@Override
	public void delGsSpFieldList(int fieldId) throws Exception {
		String sql="delete from t_gs_sp_field_list t where t.field_id=" +fieldId;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
	}

	@Override
	public void delPrdtDynamicField(int fieldId,boolean isDynamic) throws Exception {
		String sql=null;
		if(!isDynamic){
			sql="delete from t_prdt_dynamic_field t where t.field_id="+fieldId;
		}else{
			sql="update t_prdt_dynamic_field t set t.field_prdt_id=0";
		}
		
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	}

	@Override
	public void delPrdtFieldListTable(int fieldId) throws Exception {
		String sql="delete from t_gs_sp_prdt_field_list t where t.field_id="+fieldId;
		
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
	    	throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
	}

	@Override
	public List<String> getAllProduct() throws Exception {
		String sql="select str_data from t_string_resource  sr, "
				+ "t_gs_sp_product t where sr.str_id =t.prdt_name"; 
		String sql1="select prdt_name from t_gs_sp_product";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> productNameLabelList=new LinkedList<String>();
	    List<String> productInfoList=new LinkedList<String>();
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql1);
			rs=ps.executeQuery();
			while(rs.next()){
				productNameLabelList.add(rs.getString("prdt_name"));
	//			productInfoList.add(rs.getString("str_data"));
			}
			rs.close();ps.close();
		
			for(String strId:productNameLabelList){
				String sql2="select str_data from t_string_resource where str_id='"+strId+"'";
				 ps=conn.prepareStatement(sql2);
				 rs=ps.executeQuery();
				if(rs.next()){
					productInfoList.add(rs.getString("str_data"));
				}else{
					productInfoList.add(strId);
				}
				rs.close();
				ps.close();
				
			}
			
			return 	productInfoList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}

	}

	public String getProductCodeById(int productId) throws Exception{
		String sql="select distinct product_code from t_product_general t where t.product_id_old ="+productId;
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
				return rs.getString("product_code");
			}
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		return null;
		
	}
	
	@Override
	public String getProductId(String productName) throws Exception {
		String sql="select t.product_id id from t_gs_sp_product t, t_string_resource sr "
				+ "where sr.str_data='"+productName+"' and sr.str_id =t.prdt_name and sr.lang_id='011'";
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
				return Integer.toString(rs.getInt("id"));
			}
			rs.close();
			ps.close();	
			sql="select t.product_id id from t_gs_sp_product t where t.prdt_name='"+productName+"'";
			 ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				while(rs.next()){
					return Integer.toString(rs.getInt("id"));
				}
			
			
			return null;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}
	
	public List<String> getInsuredByProductId(String productId)throws Exception{
		String sql="select t2.INSURED_CATE_NAME from t_product_insured_cate t1, "
				+ "T_GEN_INSURED_CATEGORY  t2 where  t1.insured_cate_code= t2.insured_cate_code and t1.product_id='"+productId+"'";		
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> InsuredNameList=new LinkedList<String>();
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				InsuredNameList.add(rs.getString("INSURED_CATE_NAME"));
			}
			return InsuredNameList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		
		
	}
	public List<String> getCodeTableValue(String tableName)throws Exception{
		List<String> valueList=new LinkedList<String>();
		
		String sql="select t.id_column colunmName from t_code t where t.table_name ='"+tableName+"'";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String columnName=null;
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				columnName=	rs.getString("colunmName");
			}
			 DBean.closeAll(rs, ps, db);
			 sql="select "+columnName+" from "+tableName;
			 
			 
			 db = new DBean();
			 db.connect();
		     conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 valueList.add(rs.getString(columnName));
		     }
		     
			return valueList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}
	
	public List<String> getAllTableName()throws Exception{
		List<String> tableNameList=new LinkedList<String>();
		String sql="select table_name from t_code";
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
				tableNameList.add(rs.getString("table_name"));
			}
			return tableNameList;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		
		
		
	}

	@Override
	public int savePageInfo(ConfigPageInfo pageInfo,boolean isUpdate) throws Exception {
		String sql="insert into T_CONFIG_PAGE(PAGE_ID,PAGE_NAME,PRODUCT_ID,PAGE_TYPE,PAGE_TEMPLATE,PAGE_DESCRIPTION,PAGE_SEQUENCE)"
				+"values(?,?,?,?,?,?,?)";
		
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String pageIdsql="select T_PAGE_ID_SEQUENCE.nextval page_id from dual";
	    int page_id=0;
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     if(isUpdate){
		  //  	 pageIdsql="delete from T_CONFIG_PAGE t where t.page_id="+pageInfo.getPageId();
		    	 String pageTemplate=pageInfo.getPageTemplate()==null?"":pageInfo.getPageTemplate();
		    	 String pageType=pageInfo.getPageType()==null?"":pageInfo.getPageType();
		    	 String pageDesc=pageInfo.getPageDescription()==null?"":pageInfo.getPageDescription();
		    	 pageIdsql="update T_CONFIG_PAGE set PAGE_NAME='"+pageInfo.getPageName()+"', PAGE_TYPE='"+pageType
		    	 		+ "',PAGE_TEMPLATE='"+pageTemplate+"',PAGE_DESCRIPTION='"+pageDesc+"' where page_id="+pageInfo.getPageId(); ;
		    	 ps=conn.prepareStatement(pageIdsql);
		    	 ps.execute();
		    	 page_id=Integer.parseInt(pageInfo.getPageId());
		    	 return page_id;
		     }else{
		    	 ps=conn.prepareStatement(pageIdsql);
					rs=ps.executeQuery();
					while(rs.next()){
						page_id=rs.getInt("page_id");
					}
		     }
		    
			ps.close();
			ps=conn.prepareStatement(sql);
			int incre=1;
			ps.setInt(incre++, page_id);
			ps.setString(incre++, pageInfo.getPageName());
			ps.setInt(incre++,Integer.parseInt(pageInfo.getProductId()));
			ps.setString(incre++, pageInfo.getPageType());
			ps.setString(incre++, pageInfo.getPageTemplate());
			ps.setString(incre++, pageInfo.getPageDescription());
			ps.setInt(incre++, pageInfo.getPageSequence());
			ps.execute();
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		return page_id;
		
		
		
	}

	@Override
	public void delPageInfo(int pageId) throws Exception {
		String sql="delete from T_CONFIG_PAGE where PAGE_ID='"+pageId+"'";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
	}

	@Override
	public int saveSectionInfo(ConfigSectionInfo sectionInfo,boolean isUpdate) throws Exception {
		String sql="insert into T_CONFIG_SECTION(SECTION_ID,PAGE_ID,SECTION_TITLE,SECTION_TYPE,SECTION_SEQUENCE,SECTION_TEMPLATE,SECTION_DESCRIPTION,TYPE,SECTION_NAME,SECTION_CODE,SUB_SECTION_NAME,PARENT_SECTION_ID,DATATABLE_PROVIDER)"
				+"values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sectionIdsql="select T_SECTION_ID_SEQUENCE.nextval section_id from dual";
	    int section_id=0;
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     if(isUpdate){
		    	 sectionIdsql="delete from T_CONFIG_SECTION t where t.section_id="+sectionInfo.getSectionId();
		    	 ps=conn.prepareStatement(sectionIdsql);
		    	 ps.execute();
		    	 section_id=Integer.parseInt(sectionInfo.getSectionId());
		     }else{
		    	 ps=conn.prepareStatement(sectionIdsql);
					rs=ps.executeQuery();
					while(rs.next()){
						section_id=rs.getInt("section_id");
					}
		     }
		    
			ps.close();
			ps=conn.prepareStatement(sql);
			int incre=1;
			ps.setInt(incre++, section_id);
			if(sectionInfo.getPage()!=null&&sectionInfo.getPage().getPageId()!=null){
				ps.setInt(incre++, Integer.parseInt(sectionInfo.getPage().getPageId()));
			}else{
				ps.setInt(incre++, Integer.parseInt(sectionInfo.getParentSection().getPage().getPageId()));
			}
			
			ps.setString(incre++,sectionInfo.getSectionTitle());
			ps.setString(incre++, sectionInfo.getSectionType());
			ps.setInt(incre++, sectionInfo.getSectionSequence());
			ps.setString(incre++, sectionInfo.getSectionTemplate());
			ps.setString(incre++, sectionInfo.getDescription());
			ps.setString(incre++, sectionInfo.getType());
			String sectionCode=sectionInfo.getSectionTitle().replaceAll(" ", "");
			if(sectionInfo.getParentSection()!=null&&!sectionInfo.getParentSection().getSectionName().isEmpty()){
				ps.setString(incre++,sectionInfo.getParentSection().getSectionName()); 
			}else{
				ps.setString(incre++,sectionInfo.getSectionName());
			}
			 
			ps.setString(incre++,sectionCode);
			if(sectionInfo.getSubSectionName()!=null&&!sectionInfo.getSubSectionName().isEmpty()){
				ps.setString(incre++,sectionInfo.getSubSectionName());
			}else{
				ps.setNull(incre++,java.sql.Types.NULL); 
			}
//			else{
//				ps.setString(incre++,sectionInfo.getSectionTitle());
//			}
			
			if(sectionInfo.getParentSection()!=null){
				ps.setInt(incre++,Integer.parseInt(sectionInfo.getParentSection().getSectionId()));
			}else{
				ps.setNull(incre++,java.sql.Types.INTEGER); 
			}
			ps.setString(incre++,sectionInfo.getDataTableProvider());
			
			
			ps.execute();
			return section_id;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}

	@Override
	public void delSectionInfo(int sectionid) throws Exception {
		String sql="delete from T_CONFIG_SECTION where SECTION_ID='"+sectionid+"'";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
	}

	@Override
	public List<ConfigPageInfo> getPageInfoByProduct(String productName)
			throws Exception {

		List<ConfigPageInfo> pageList=new LinkedList<ConfigPageInfo>();
		String productId=getProductId(productName);
		
		String sql="select * from T_CONFIG_PAGE t where t.product_id="+productId +" order by page_sequence";
 
		
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
		    	 ConfigPageInfo pageInfo=new ConfigPageInfo();
		    	 String pageName=rs.getString("PAGE_NAME");
		    	 String pageType=rs.getString("PAGE_TYPE");
		    	 String pageTemplate=rs.getString("PAGE_TEMPLATE");
		    	 String pageDesc=rs.getString("PAGE_DESCRIPTION");
		    	 int pageId=rs.getInt("PAGE_ID");
		    	 int sequence=rs.getInt("PAGE_SEQUENCE");
		    	 pageInfo.setPageName(pageName);
		    	 if(pageDesc!=null){
		    		 pageInfo.setPageDescription(pageDesc);
		    	 }
		    	 pageInfo.setPageSequence(sequence);
		    	 pageInfo.setPageId(pageId+"");
		    	 pageInfo.setPageTemplate(pageTemplate);
		    	 pageInfo.setProductName(productName);
		    	 pageInfo.setProductId(productId);
		    	 pageInfo.setPageType(pageType);
		    	 pageInfo.setEdit(true);
		    	 pageList.add(pageInfo);
		     }
		     ps.close();
		     rs.close();
		      
		     for(ConfigPageInfo pageInfo:pageList){
		    	 List<ConfigSectionInfo> sectionList=new LinkedList<ConfigSectionInfo>();
		    	 sql="select * from T_CONFIG_SECTION where PAGE_ID="+pageInfo.getPageId()+" and sub_section_name is null order by section_sequence";
		    	 ps=conn.prepareStatement(sql);
			     rs=ps.executeQuery();
		    	 while(rs.next()){
		    		 String sectionTitle=rs.getString("SECTION_TITLE");
		    		 int sectionId=rs.getInt("SECTION_ID");
		    		 String sectionType=rs.getString("SECTION_TYPE");
		    		 String sectionTemp=rs.getString("SECTION_TEMPLATE");
		    		 String sectionDesc=rs.getString("SECTION_DESCRIPTION");
		    		 String sectionCode=rs.getString("SECTION_CODE");
		    		 String sectionName=rs.getString("SECTION_NAME");
		    		 String Type=rs.getString("Type");
		    		 int sequence=rs.getInt("section_sequence");
		    		 String tableProvider=rs.getString("datatable_Provider");
		    		 String subSectionName=rs.getString("sub_section_name");
		    		 ConfigSectionInfo sectionInfo=new ConfigSectionInfo();
		    		 if(sectionDesc!=null){
		    			 sectionInfo.setDescription(sectionDesc);
		    		 }
		    		
		    		 sectionInfo.setPage(pageInfo);
		    		 sectionInfo.setSectionId(sectionId+"");
		    		 sectionInfo.setSectionTemplate(sectionTemp);
		    		 sectionInfo.setSectionSequence(sequence);
		    		 sectionInfo.setSectionTitle(sectionTitle);
		    		 sectionInfo.setSectionType(sectionType);
		    		 sectionInfo.setType(Type);
		    		 sectionInfo.setEdit(true);
		    		 sectionInfo.setSubSectionName(subSectionName);
		    		 sectionInfo.setSectionName(sectionName);
		    	
		    		 sectionInfo.setDataTableProvider(tableProvider);
		    		 sectionInfo.setSectionCode(sectionCode);
		    		 getFieldInfoBySectionId(sectionInfo);
		    		 getNestSection(sectionInfo);
		    		 sectionList.add(sectionInfo);
		    	 }
		    	 pageInfo.setSectionList(sectionList);
		     }
		     
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	return pageList;
	}
	
	
	private void getNestSection(ConfigSectionInfo parentSectionInfo)throws Exception{
		
		String sql="select * from T_CONFIG_SECTION t where t.sub_section_name is not null and  t.section_name='"+parentSectionInfo.getSectionName()+"' and t.page_id="+ parentSectionInfo.getPage().getPageId()+" order by section_sequence";
		
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     List<ConfigSectionInfo> sectionList=new LinkedList<ConfigSectionInfo>();
		     while(rs.next()){
	    		 String sectionTitle=rs.getString("SECTION_TITLE");
	    		 int sectionId=rs.getInt("SECTION_ID");
	    		 String sectionType=rs.getString("SECTION_TYPE");
	    		 String sectionTemp=rs.getString("SECTION_TEMPLATE");
	    		 String sectionDesc=rs.getString("SECTION_DESCRIPTION");
	    		 String sectionCode=rs.getString("SECTION_CODE");
	    		 String sectionName=rs.getString("SECTION_NAME");
	    		 String Type=rs.getString("Type");
	    		 int sequence=rs.getInt("section_sequence");
	    		 String tableProvider=rs.getString("datatable_Provider");
	    		 String subSectionName=rs.getString("sub_section_name");
	    		 ConfigSectionInfo sectionInfo=new ConfigSectionInfo();
	    		 if(sectionDesc!=null){
	    			 sectionInfo.setDescription(sectionDesc);
	    		 }
	    		 sectionInfo.setSectionId(sectionId+"");
	    		 sectionInfo.setSectionTemplate(sectionTemp);
	    		 sectionInfo.setSectionSequence(sequence);
	    		 sectionInfo.setSectionTitle(sectionTitle);
	    		 sectionInfo.setSectionType(sectionType);
	    		 sectionInfo.setType(Type);
	    		 sectionInfo.setEdit(true);
	    		 sectionInfo.setSubSectionName(subSectionName);
	    		 sectionInfo.setSectionName(sectionName);

	    		 sectionInfo.setParentSection(parentSectionInfo);
	    		 sectionInfo.setDataTableProvider(tableProvider);
	    		 sectionInfo.setSectionCode(sectionCode);
	    		 getFieldInfoBySectionId(sectionInfo);
//	    		 getNestSection(sectionInfo);
	    		 
	    		 sectionList.add(sectionInfo);
	    	 }
		     parentSectionInfo.setSubSectionList(sectionList);   
	    }catch (Exception e) {
			     throw e;
		 } finally {
		    	DBean.closeAll(rs, ps, db);
		 }
	}
	
	
	public List<ConfigFieldInfo> getFieldInfoBySectionId(ConfigSectionInfo section) throws Exception{
		
		List<ConfigFieldInfo> fieldList=new LinkedList<ConfigFieldInfo>();
		String sql="select  t1.field_id field_id,t1.field_name field_name,t1.field_code field_code, t2.field_label field_label, t2.default_value default_value, "
				+ "t2.field_type field_type, t2.colspan colspan, t2.sequence sequence, t2.code_table_table_name table_name,t2.code_table_display_type display_type,t2.code_table_where_clause where_clause,"
				+"t2.code_table_order_by order_by,t2.child_field_code childitem,t2.parent_field_code parent_code,t2.foreign_key foregin_key, t2.visible visible, t2.visible_expression visible_expression, t2.readonly readonly, t2.readonly_expression readonly_expression,"
				+"t2.required required, t2.required_expression require_expression, t2.NO_SELECTION_LOAD noSelectionLoad,t2.format format, t2.product_id product_id,t2.value_binding value_binding,t2.io io "
				+ "from t_config_field t1 ,t_gs_sp_prdt_field_list t2 " 
				+ "where t1.field_id=t2.field_id and t1.section_id='"+section.getSectionId()+"' order by t2.sequence ";
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
		    	 ConfigFieldInfo fieldInfo=new ConfigFieldInfo();
		    	 int field_id=rs.getInt("field_id");
		    	 String field_code=rs.getString("field_code");
		    	 String field_name=rs.getString("field_name");
		    	 String default_value=rs.getString("default_value");
		    	 String displayLabel=rs.getString("field_label");
		    	 String fieldType=rs.getString("field_type");
		    	 int fieldColspan=rs.getInt("colspan");
		    	 String tableName=rs.getString("table_name");
		    	 String codeTableDisplayType=rs.getString("display_type");
		    	 String whereClause=rs.getString("where_clause");
		    	 String orderBy=rs.getString("order_by");
		    	 String visible=rs.getString("visible");
		    	 String visibleEx=rs.getString("visible_expression");
		    	 boolean readonly=rs.getBoolean("readonly");
		    	 String readonlyEx=rs.getString("readonly_expression");
		    	 boolean required=rs.getBoolean("required");
		    	 String requiredEx=rs.getString("require_expression");
		    	 boolean noLoadSec=rs.getBoolean("noSelectionLoad");
		    	 String childitem=rs.getString("childitem");
		    	 String parentitem=rs.getString("parent_code");
		    	 String foreginKey=rs.getString("foregin_key");
		    	 String format=rs.getString("format");
		    	 int sequence=rs.getInt("sequence");
		    	 int productId=rs.getInt("product_id");
		    	 String io=rs.getString("io");
		    	 String valueBind=rs.getString("value_binding");
		    	 
		    	 if(default_value!=null){
		    		 fieldInfo.setDefaultValue(default_value); 
		    	 }
		    	 fieldInfo.setEdit(true);
		    	 fieldInfo.setFieldCode(Integer.parseInt(field_code));
		    	 fieldInfo.setFieldId(field_id);
		    	 fieldInfo.setDisplayLabel(displayLabel);
		    	 fieldInfo.setPrdtId(productId);
		    	 fieldInfo.setFieldName(field_name);
		    	if(fieldType.equals("UNITEDTABLE")){
		    		fieldType="CASCADETABLE";
		    	}
		    	 fieldInfo.setDisplayType(fieldType);
		    	 fieldInfo.setColspan(fieldColspan);
		    	 fieldInfo.setValueBinding(valueBind);
		    	 fieldInfo.setSequence(sequence);
		    	 fieldInfo.setTableName(tableName);
		    	 if(codeTableDisplayType!=null){
		    		 fieldInfo.setCodeTabelDisplayType(codeTableDisplayType);
		    	 }
		    	 if(io!=null){
		    		 fieldInfo.setIo(io);
		    	 }else{
		    		 fieldInfo.setIo("in");
		    	 }
		    	
		    	 if(whereClause!=null){
		    		 fieldInfo.setWhereClause(whereClause);
		    	 }
		    	if(orderBy!=null){
		    		 fieldInfo.setOrderBy(orderBy);
		    	}
		    	if(visible==null||visible.equals("1")){
		    		 fieldInfo.setVisible(true);
		    	}else{
		    		 fieldInfo.setVisible(false);
		    	}
		    	
		    	 if(visibleEx!=null){
		    		 fieldInfo.setVisibleEx(visibleEx);
		    	 }
		    	
		    	 fieldInfo.setReadOnly(readonly);
		    	 if(readonlyEx!=null){
		    		 fieldInfo.setReadOnlyEx(readonlyEx);
		    	 }
		    	
		    	 fieldInfo.setRequired(required);
		    	 if(requiredEx!=null){
		    		 fieldInfo.setRequieredEx(requiredEx);
		    	 }
		    	
		    	 fieldInfo.setNoSelectionLoad(noLoadSec);	
		    	 if(childitem!=null){
		    		 fieldInfo.setChildItem(childitem);
		    	 }
		    	 if(format!=null){
		    		 fieldInfo.setFormat(format);
		    	 }
		    	if(foreginKey!=null){
		    		 fieldInfo.setUnitedKey(foreginKey);
		    	}
		    	if(parentitem!=null){
		    		 fieldInfo.setParentItem(parentitem);
		    	}
		    	
		    	 fieldInfo.setSection(section);
		    	 fieldList.add(fieldInfo); 
		     }
		     rs.close();
		     ps.close();
		     
		     if(fieldList.size()>0){
		    	  StringBuffer sb=new StringBuffer("select t.STR_ID str_id, t.STR_DATA str_data from t_string_resource t where t.lang_id='011' and t.str_id in ('");
				     int i=0;
				    for(;i<fieldList.size()-1;i++){
				    	sb.append(fieldList.get(i).getDisplayLabel()+"','");
				    }
				    sb.append(fieldList.get(i).getDisplayLabel()+"')");
				    
				    ps=conn.prepareStatement(sb.toString());
				    rs=ps.executeQuery();
				    Map<String,String> labelMap=new HashMap<String,String>();
				    
				    while(rs.next()){
				    	
				    	labelMap.put(rs.getString("str_id"), rs.getString("str_data"));
				    }
				    for(ConfigFieldInfo fieldInfo:fieldList){
				    	fieldInfo.setDisplayLabel(labelMap.get(fieldInfo.getDisplayLabel()));

				    }
				    section.setFieldList(fieldList);
				     
		     }
		   
		     
	    } catch (Exception e) {
			     throw e;
		} finally {
		    	DBean.closeAll(rs, ps, db);
		}
		
		
		
		return fieldList;
	}

	@Override
	public void changeSecitonPage(ConfigSectionInfo section, int pageId)
			throws Exception {
		
		String sql="update T_CONFIG_SECTION t set t.page_ID="+pageId +" where t.section_id="+section.getSectionId();
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
		
		
	}

	@Override
	public ConfigPageInfo getConfigPageInfoByPageId(int pageID)throws Exception {
		String sql="select * from T_CONFIG_PAGE t where t.page_id="+pageID;
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
		    	 ConfigPageInfo pageInfo=new ConfigPageInfo();
		    	 String pageName=rs.getString("PAGE_NAME");
		    	 String pageType=rs.getString("PAGE_TYPE");
		    	 String pageTemplate=rs.getString("PAGE_TEMPLATE");
		    	 String pageDesc=rs.getString("PAGE_DESCRIPTION");
		    	 String productName=rs.getString("PRODUCT_ID");
		    	 int pageId=rs.getInt("PAGE_ID");
		    	 pageInfo.setPageName(pageName);
		    	 pageInfo.setPageDescription(pageDesc);
		    	 pageInfo.setPageId(pageId+"");
		    	 pageInfo.setPageTemplate(pageTemplate);
		    	 pageInfo.setProductName(productName);
		    	 pageInfo.setPageType(pageType);
		    	 return pageInfo;
		     }
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	    return null;
	}

	@Override
	public ConfigSectionInfo getConfigSectionInfoBySectionId(int sectionId)
			throws Exception {
		String sql="select * from t_config_section t where t.section_id="+sectionId;
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
		    	 String sectionTitle=rs.getString("SECTION_TITLE");
	    		 int Id=rs.getInt("SECTION_ID");
	    		 String sectionType=rs.getString("SECTION_TYPE");
	    		 String sectionTemp=rs.getString("SECTION_TEMPLATE");
	    		 String sectionDesc=rs.getString("SECTION_DESCRIPTION");
	    		 String sectionCode=rs.getString("SECTION_CODE");
	    		 String sectionName=rs.getString("SECTION_NAME");
	    		 String Type=rs.getString("Type");
	    		 int sequence=rs.getInt("section_sequence");
	    		 String tableProvider=rs.getString("datatable_Provider");
	    		 String subSectionName=rs.getString("sub_section_name");
	
	    		 ConfigSectionInfo sectionInfo=new ConfigSectionInfo();
	    		 if(sectionDesc!=null){
	    			 sectionInfo.setDescription(sectionDesc);
	    		 }
	    		 sectionInfo.setSectionId(Id+"");
	    		 sectionInfo.setSectionTemplate(sectionTemp);
	    		 sectionInfo.setSectionSequence(sequence);
	    		 sectionInfo.setSectionTitle(sectionTitle);
	    		 sectionInfo.setSectionType(sectionType);
	    		 sectionInfo.setType(Type);
	    		 sectionInfo.setEdit(true);
	    		 sectionInfo.setSubSectionName(subSectionName);
	    		 sectionInfo.setSectionName(sectionName);
	    		 sectionInfo.setDataTableProvider(tableProvider);
	    		 sectionInfo.setSectionCode(sectionCode);
	    		 getFieldInfoBySectionId(sectionInfo);
	    		 getNestSection(sectionInfo);
		    	 return sectionInfo;
		     }
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	    return null;
	}

	@Override
	public void updatePrdtFieldPageName(String oldTitle,ConfigPageInfo newPageInfo)throws Exception {
		String sql=null;
	
			sql="update t_gs_sp_prdt_field_list set page_name='"+newPageInfo.getPageName()+"' where page_name='"+oldTitle+"' "
					+ "and product_id="+newPageInfo.getProductId();

		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	}
	
	public void updatePrdtFieldSectionName(String oldTitle,ConfigSectionInfo newSectionInfo,boolean isSubsection)throws Exception{
		String sql=null;
		if(isSubsection){
			sql="update t_gs_sp_prdt_field_list t set t.sub_section_name='"+newSectionInfo.getSectionTitle()+"' where t.page_name='"
					+newSectionInfo.getParentSection().getPage().getPageName()+"' and t.sub_section_name='"+oldTitle
					+"' and t.product_id="+newSectionInfo.getParentSection().getPage().getProductId();
		}else{
			if(newSectionInfo.getSubSectionList().size()==0){
				sql="update t_gs_sp_prdt_field_list t set t.sub_section_name='"+newSectionInfo.getSectionTitle()+"', t.section_name='"+newSectionInfo.getSectionTitle()+"' where t.page_name='"
						+newSectionInfo.getPage().getPageName()+"' and t.section_name='"+oldTitle
						+"' and t.product_id="+newSectionInfo.getPage().getProductId();
			}else{
				sql="update t_gs_sp_prdt_field_list t set t.section_name='"+newSectionInfo.getSectionTitle()+"' where t.page_name='"
						+newSectionInfo.getPage().getPageName()+"' and t.section_name='"+oldTitle
						+"' and t.product_id="+newSectionInfo.getPage().getProductId();
				
			}
		}
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     ps.execute();
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
		
		
	}

	@Override
	public void updateFieldSequence(int fieldID) throws Exception {
		String sql="select page_name,section_name,sequence from t_gs_sp_prdt_field_list t where t.field_id="+fieldID;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String pageName=null,sectionName=null;
	    int sequence=0;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 pageName=rs.getString("page_name");
		    	 sectionName=rs.getString("section_name");
		    	 sequence=rs.getInt("sequence");
		     }
		     ps.close();
		     rs.close();
		     if(pageName==null||sectionName==null)return;
		     sql="select field_Id ,sequence from t_gs_sp_prdt_field_list t where t.page_name='"+pageName+"' and t.section_name='"+sectionName+"' and sequence>"+sequence;
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     int field_id=0;
		     int seq=0;
		     Map<Integer,Integer> fieldSeqMap=new HashMap<Integer,Integer>();
		     while(rs.next()){
		    	 field_id=rs.getInt("field_Id");
		    	 seq=rs.getInt("sequence");
		    	 fieldSeqMap.put(field_id, seq);
		     }
		     ps.close();
		     rs.close();
		     Iterator<Integer> it=fieldSeqMap.keySet().iterator();
		   while(it.hasNext()){
			   Integer field_info=it.next();
			   Integer sequen=fieldSeqMap.get(field_info);
			   String updateSql="update t_gs_sp_prdt_field_list t set t.sequence="+(sequen-1) +" where t.field_id="+field_info;
			   ps=conn.prepareStatement(sql);
			   ps.execute(updateSql);
			   ps.close();
			   rs.close();
		   }     
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
		
	}

	@Override
	public List<String> getCustomizeSection() throws Exception {
		String sql="select distinct tag_name from T_UICONFIG_SECTION_RELATION t where t.tag_name is not null and t.tag_url is not null";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> tagNameList=new LinkedList<String>();
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 tagNameList.add(rs.getString("tag_name")); 
		     }
		 
		     
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	    return tagNameList;
	    
	    
		
	}

	@Override
	public String getModelSeqByFieldId(int fieldId,String skinBo,String variable) throws Exception {
		String sql="select field_name,node_id from t_uic_page_datamodel_field t where t.field_id="+fieldId;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
		List<String> modelSequence=new LinkedList<String>();
		List<String> tempSequence=new LinkedList<String>();
		boolean sectionMatch=false;
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     
		     Map<String,Integer> skinTypeMap=new HashMap<String,Integer>();
		     int node_id=-1;
		     int temp_nodeId=-1;
		     if(rs.next()){
		    	String fieldName=rs.getString("field_name");
		    	modelSequence.add(fieldName);
		    	node_id=rs.getInt("node_id");
		    	temp_nodeId=node_id;
		    	if(skinBo!=null&&variable!=null){
		    		while(node_id>0){
			    		ps.close();
			    		rs.close();
			    		sql="select class_name from t_uic_page_datamodel_node t where t.node_id="+node_id;
			    		ps=conn.prepareStatement(sql);
					    rs=ps.executeQuery();
			    		if(rs.next()){
			    			String className=rs.getString("class_name");
			    			if(className.equals(skinBo)){
			    				ps.close();
					    		rs.close();
					    		sectionMatch=true;
					    		break;
			    			}else{
			    				sql="select alias_name, from_node_id,relationship_type from t_uic_page_datamodel_node_info t where t.to_node_id="+node_id;
					    		ps=conn.prepareStatement(sql);
							    rs=ps.executeQuery();
					    		if(rs.next()){
					    			String skinBoName=rs.getString("alias_name");
					    			tempSequence.add(skinBoName);
					    			node_id=rs.getInt("from_node_id");
					    			Integer type=rs.getInt("relationship_type");
					    			skinTypeMap.put(skinBoName, type);
					    		}else{
					    			node_id=-1;
					    		}
			    			}
			    		}
		    		}
		    		if(sectionMatch){
		    			modelSequence.addAll(tempSequence);
		    			modelSequence.add(variable);
		    		}
		    	}
		    	
		    	
		    	if(skinBo==null||variable==null||!sectionMatch){
		    		node_id=temp_nodeId;
		    		tempSequence.clear();
			    	while(node_id>0){
			    		ps.close();
			    		rs.close();
			    		sql="select alias_name, from_node_id,relationship_type from t_uic_page_datamodel_node_info t where t.to_node_id="+node_id;
			    		ps=conn.prepareStatement(sql);
					    rs=ps.executeQuery();
			    		if(rs.next()){
			    			String skinBoName=rs.getString("alias_name");
			    			tempSequence.add(skinBoName);
			    			node_id=rs.getInt("from_node_id");
			    			Integer type=rs.getInt("relationship_type");
			    			skinTypeMap.put(skinBoName, type);
			    		}else{
			    			node_id=-1;
			    		}
			    		
			    	}
			    	
			    	
			    	ps.close();
		    		rs.close();
		    		modelSequence.addAll(tempSequence);
		    		String rootModel=getRootRunTimeModel();
		    		if(rootModel!=null){
		    			modelSequence.add(rootModel);
		    		}
		    	}
		     }
		     
		     
		     
		     
		     StringBuffer sb=new StringBuffer();
		     for(int i=modelSequence.size()-1;i>0;i--){
		    	 sb.append(modelSequence.get(i));
		    	 if(skinTypeMap.get(modelSequence.get(i))!=null&&skinTypeMap.get(modelSequence.get(i))==2){
		    		 sb.append("[0].");
		    	 }else{
		    		 sb.append(".");
		    	 }
		 }
		 sb.append(modelSequence.get(0)); 
		 return sb.toString();
	    }catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }

		
	}
	public List<String> getSubSectionByTemplateName(String templateName)throws Exception{
		String sql="select  t2.section_name subsectionName from T_UICONFIG_SECTION_RELATION t1,T_UICONFIG_SECTION_RELATION t2 "
				+ "where t1.tag_id=t2.parent_tag_id and t1.tag_name ='"+templateName+"'";
		 
		sql="select  t2.sub_section_name subsectionName from T_UICONFIG_SECTION_RELATION t1,T_UICONFIG_SECTION_RELATION t2 " 
		+"where t1.section_name=t2.section_name and t1.tag_name ='"+templateName+"'";
						
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
		List<String> subsectionList=new LinkedList<String>();
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 String subsectionName=rs.getString("subsectionName");
		    	 if(subsectionName!=null&&!subsectionName.trim().isEmpty()){
		    		 subsectionList.add(subsectionName);
		    	 }
		     }
		     return subsectionList;
		}catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
	}

	public SectionTemplateInfo getSectionTemplateByName(String name,boolean isTagName)throws Exception{
		String sql=null;
		if(isTagName){
			sql="select variable,variable_class_name,section_name from T_UICONFIG_SECTION_RELATION t where t.section_name='"+name+"'";
		}else{
			sql="select variable,variable_class_name,section_name from T_UICONFIG_SECTION_RELATION t where t.sub_section_name='"+name+"'";
		}
	
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    SectionTemplateInfo templateInfo=new SectionTemplateInfo();
		try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 templateInfo.setSectionName(rs.getString("section_name"));
		    	 templateInfo.setClassName(rs.getString("variable_class_name"));
		    	 templateInfo.setVariable(rs.getString("variable"));
		     }
		   return templateInfo;
		}catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
	}

	public String getFieldSkinbo(int fieldId) throws Exception {
		String sql="select class_name from t_uic_page_datamodel_field t1, t_uic_page_datamodel_node t2 where t1.node_id=t2.node_id and t1.field_id=2000000158";
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String skinboName=null;
	    try {
			
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
		     rs=ps.executeQuery();
		     while(rs.next()){
		    	 skinboName=rs.getString("class_name");
		     }
		   return skinboName;
		}catch (Exception e) {
		     throw e;
	    } finally {
	    	DBean.closeAll(rs, ps, db);
	    }
		
	}

	@Override
	public String getRootRunTimeModel() throws Exception {

		String sql="select variable from T_UICONFIG_SECTION_RELATION t where t.tag_name='DefaultTemplate'";
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DBean();
			 db.connect();
		    Connection  conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				return rs.getString("variable");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		return null;
	}
	public String getTableCodeByFieldName(String fieldName)throws Exception{
		String sql="select table_code from T_GEN_DYNAMIC_FIELD t where t.field_name='"+fieldName+"'";
		
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DBean();
			 db.connect();
		    Connection  conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				return rs.getString("table_code");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
		return null;
		
		
	}
	public List<String> getAllFieldType()throws Exception{

		String sql="select type_name from t_uiconfig_field_type" ;
		
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> fieldType=new LinkedList<String>();
		
		try {
			db = new DBean();
			 db.connect();
		    Connection  conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				fieldType.add(rs.getString("type_name"));
			}
			return fieldType;
		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
			
		
	}

	@Override
	public String getProductNameById(long productId) throws Exception {
		String sql="select prdt_name from t_gs_sp_product t where t.product_id="+productId;
		DBean db = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String productName="";
		try {
			db = new DBean();
			 db.connect();
		     Connection  conn = db.getConnection();
		     ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
	
				productName=rs.getString("prdt_name");
			}
			rs.close();ps.close();

			String sql2="select str_data from t_string_resource where str_id='"+productName+"'";
			ps=conn.prepareStatement(sql2);
			rs=ps.executeQuery();
				if(rs.next()){
					productName=rs.getString("str_data");
				}
				rs.close();
				ps.close();
			return 	productName;
		}catch (Exception e) {
			     throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}
}
