package com.ebao.gs.pol.prdt.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JDBCDataSourceConnect {
	
	
	   static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";  
	   static final String DB_URL = "jdbc:oracle:thin:@172.25.14.10:1521:g10u0";

	   //  Database credentials
	   static final String USER = "GS_39x_DEV";
	   static final String PASS = "GS_39x_DEVpwd";
	   static int tStringResourceCount=0;
	   static boolean isFirstVisitInNodeInfo=false;
	   static boolean isFirstVisitTagTable=false;
	   static int maxTagId;
	   static int maxnodeInfoCount=0;
	   private static JDBCDataSourceConnect connection=null;
	   static boolean isFirstVisit=false;
	   private  static Connection conn = null;
	   private  Statement stmt = null;
	   private ResultSet rs=null;
	   private JDBCDataSourceConnect(){
		   try{

			      Class.forName(JDBC_DRIVER);
			      
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		   }catch(SQLException se){
			      //Handle errors for JDBC
			     
			   }catch(Exception e){
			      //Handle errors for Class.forName
			     
			      try{
				         if(stmt!=null)
				            stmt.close();
				      }catch(SQLException se2){
				      }// nothing we can do
				      try{
				         if(conn!=null)
				            conn.close();
				      }catch(SQLException se){
				      
				      }
			   }finally{
			      //finally block used to close resources
			      //end finally try
			   }//
		   
	   }
	   
	   public int getMaxTagId(){
		   String sql="select T_UIC2_CORE_TAG_SEQUENCE.nextval  tag_id from dual ";
		   try {
			   stmt=conn.createStatement();
			   if(!isFirstVisitTagTable){
				   rs=stmt.executeQuery(sql);
				   while(rs.next()){  
					   maxTagId=rs.getInt("tag_id");
				   }
				   isFirstVisitTagTable=true;
			   }else{
				   maxTagId++;
			   }
		   }catch (Exception e) {
			  
		   }finally{
			   try{
				   if(stmt!=null)
					   stmt.close();
			   }catch(SQLException se2){
				  
	       }
		}
		   return maxTagId;
		   
	   }
	   
	   
	   
	   public int getMaxDataNodeInfoId(){
		   String sql="select max(DATA_MODEL_NODE_INFO_ID) maxId from t_uic_page_datamodel_node_info";
		   int maxValue=0;
		   try {
			   stmt=conn.createStatement();
			   if(!isFirstVisitInNodeInfo){
				   rs=stmt.executeQuery(sql);
				   
				   while(rs.next()){  
					   maxValue=rs.getInt("maxId");
				   }
				   maxnodeInfoCount=maxValue+1;
				   isFirstVisitInNodeInfo=true;
			   }else{
				   maxnodeInfoCount++;
			   }
			  
			   

		   }catch (Exception e) {
				 
			}finally{
				   try{
					   if(stmt!=null)
						   stmt.close();
				   }catch(SQLException se2){
					   
		       }
			}
		   return maxnodeInfoCount;
		   
	   }
	   
	   
	   public Map<String,Boolean> getTStringResource(String fieldDisplayName){
		   Map<String,Boolean> stringResourceMap=new HashMap<String,Boolean>(); 
		   String command="SELECT MAX(TO_NUMBER(substr(STR_ID, length('MSG_GS_SP_')+1))) as maxStr_id"
					+" FROM t_String_Resource WHERE substr(STR_ID,length('MSG_GS_SP_')+1,1) IN ('0','1','2','3','4','5','6','7','8','9')"
					+" and substr(STR_ID, length('MSG_GS_SP_')+1) not like '%/_%' escape '/'"
					+" AND STR_ID LIKE substr('MSG_GS_SP_',1,length('MSG_GS_SP_')-1)||'/_%' ESCAPE '/'"
					+" AND TO_NUMBER(substr(STR_ID, length('MSG_GS_SP_')+1)) < 99999999";
			String sql="select t.str_id str_id from t_String_Resource t where t.lang_id='011' and t.str_data='"+fieldDisplayName+"'";

		    String newValue=null;
			try {
				stmt=conn.createStatement();
//    		     rs=stmt.executeQuery(sql);
//			     while(rs.next()){
//			    	 String str_id=rs.getString("str_id");
//			    	 if(str_id.startsWith("MSG_")){
//			    		 stmt.close();
//			    		 stringResourceMap.put(str_id, true);
//			    		 return stringResourceMap;
//			    	 }
//			     } 
				 rs=stmt.executeQuery(command);
				int maxValue=0;
				while(rs.next()){
					maxValue=rs.getInt("maxStr_id");
				}
			
				if(!isFirstVisit){
					tStringResourceCount=maxValue;
					isFirstVisit=true;
				}
				
		
				newValue="MSG_GS_SP_"+String.valueOf(++tStringResourceCount);
				stringResourceMap.put(newValue, false);
				return 	stringResourceMap;
			}catch (Exception e) {
				  
			}finally{
				try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			    	
			      }
			}
		   return null;
		   
	   }
	   
	   
	   
	   
	   public void closeAll(){
		  
			try {
				 if(conn!=null)
					 conn.close();
				 if(stmt!=null)
			            stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			
			}
		   
		   
	   }
	   
	   
	   public static JDBCDataSourceConnect getInstance(){
		   if(connection==null){
			   connection=new JDBCDataSourceConnect();
		   }
		   return connection;
	   }
	   

}
