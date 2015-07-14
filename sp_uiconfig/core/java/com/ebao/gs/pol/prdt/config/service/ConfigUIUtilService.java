package com.ebao.gs.pol.prdt.config.service;

import java.util.List;

public interface ConfigUIUtilService {
	public List<String> getAllProductInfo()throws Exception;
	
	public String getProductId(String productName) throws Exception;
	
	public List<String> getInsuredTypeByProduct(String productName) throws Exception;
	
	public List<String> getCodeTableValueList(String tableName)throws Exception;
	public List<String> getAllCodeTableName() throws Exception;

}
