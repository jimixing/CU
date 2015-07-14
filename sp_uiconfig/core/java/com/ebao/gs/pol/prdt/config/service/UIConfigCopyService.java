package com.ebao.gs.pol.prdt.config.service;

import java.util.List;
import java.util.Map;

public interface UIConfigCopyService {
	
//	public void copyAllProduct(String productName)throws Exception;
	
	
	public void copyProduct(String productName,String newProductName,
			List<String> pageNameList,Map<String,List<String>> sectionPageNameMap)throws Exception;


}
