package com.ebao.gs.pol.prdt.config.service;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;

public interface ConfigPageService {
	
	public void addConfigPage(ConfigPageInfo pageInfo,boolean isUpdate,String pageTitle) throws Exception;
	  
	public void delConfigPage(ConfigPageInfo pageInfo)throws Exception;
	
	public List<ConfigPageInfo> loadConfigPageList(String productName)throws Exception;
	
	public ConfigPageInfo getConfigPageInfoById(int pageID)throws Exception;
 
	
}
