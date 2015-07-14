package com.ebao.gs.pol.prdt.config.service.impl;

import java.util.List;

import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.gs.pol.prdt.config.dao.impl.ConfigSqlExecuteDaoImpl;
import com.ebao.gs.pol.prdt.config.service.ConfigUIUtilService;

public class ConfigUIUtilServiceImpl implements ConfigUIUtilService {
	private ConfigSqlExecuteDao configDao=new ConfigSqlExecuteDaoImpl();
	
	public List<String> getAllProductInfo()throws Exception{
		
		return configDao.getAllProduct(); 
	} 

	@Override
	public String getProductId(String productName) throws Exception {
		return configDao.getProductId(productName);
	}
	
	public List<String> getInsuredTypeByProduct(String productId) throws Exception{
		return configDao.getInsuredByProductId(productId);
		
	}
	
	public List<String> getCodeTableValueList(String tableName)throws Exception{
		return configDao.getCodeTableValue(tableName);
	}
	public List<String> getAllCodeTableName() throws Exception{
		return configDao.getAllTableName();
	}
	
	

}
