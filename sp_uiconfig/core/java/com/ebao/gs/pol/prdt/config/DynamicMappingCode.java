package com.ebao.gs.pol.prdt.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DynamicMappingCode {
	
	private static List<String> dynamicLevel=new LinkedList<String>();
	private static Map<Integer,String> insurdeCateMappingDetail=new HashMap<Integer,String>();
	
	private static Map<String,String> dynamicSpDisplayTypeMapping=new HashMap<String,String>();
	
	private static DynamicMappingCode dynamicMappingCode;
	
	
	public static DynamicMappingCode getInstance(){
		if(dynamicMappingCode==null){
			dynamicMappingCode=new DynamicMappingCode();
			
		}
		return dynamicMappingCode;
		
	}
	
	private DynamicMappingCode(){
		init();
	}
	
	private void init(){
//		dynamicLevel.add("T_GEN_POLICY_INFO");
//		dynamicLevel.add("T_INSURED_LIST");
//		dynamicLevel.add("T_POLICY_CT");
//		dynamicLevel.add("T_POLICY_CT_ACCE");
		insurdeCateMappingDetail.put(1, "vehicleInsuredObjects[0]");
		insurdeCateMappingDetail.put(2, "personInsuredObjects[0]");
		insurdeCateMappingDetail.put(3, "addressInsuredObjects[0]");
		insurdeCateMappingDetail.put(8, "marineVoyageInsuredObjects[0]");
		dynamicSpDisplayTypeMapping.put("B", "CODETABLE");
		dynamicSpDisplayTypeMapping.put("C", "CODETABLE");
		dynamicSpDisplayTypeMapping.put("D", "CODETABLE");
		dynamicSpDisplayTypeMapping.put("T", "TEXT");
		dynamicSpDisplayTypeMapping.put("R", "CODETABLE");
		
	
	}
	
	public String getInsuredCateMapping(int cateId){
		return insurdeCateMappingDetail.get(cateId);
	}
	
	public String getdynSpDisplayMapping(String dynamCate){
		return dynamicSpDisplayTypeMapping.get(dynamCate);
	}
	

}
