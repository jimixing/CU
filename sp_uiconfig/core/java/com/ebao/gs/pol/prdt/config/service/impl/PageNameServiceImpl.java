package com.ebao.gs.pol.prdt.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ebao.foundation.module.cache.api.Cache;
import com.ebao.gs.pol.prdt.config.dao.PageNameDao;
import com.ebao.gs.pol.prdt.config.dao.impl.PageNameDaoImpl;
import com.ebao.gs.pol.prdt.config.service.PageNameService;

public class PageNameServiceImpl implements PageNameService {
	
	private PageNameDao pageNameDao = new PageNameDaoImpl();
	
	private static final String REGION_GSUICONFIG_PAGE_NAME = "GSUICONFIG_PAGE_NAME";
	
	private static final String PAGE_NAME_CODE = "PageNameCode";
	private static final String PAGE_NAME_DESC = "PageNameDesc";
	private static final String PAGE_NAME_LIST = "PageNameList";
	
	@Override
	public String findPageNameDescByCode(String pageNameCode) throws Exception {
		// TODO Auto-generated method stub
		String pageNameDesc = null;
		String key = this.getPageNameKey(pageNameCode, PAGE_NAME_CODE);
		if(Cache.containsKey(REGION_GSUICONFIG_PAGE_NAME, key)){
			pageNameDesc = (String)Cache.get(REGION_GSUICONFIG_PAGE_NAME, key);
		} else {
			pageNameDesc = pageNameDao.findPageNameDescByCode(pageNameCode);
			Cache.put(REGION_GSUICONFIG_PAGE_NAME, key, pageNameDesc);
		}
		
		return pageNameDesc;
	}

	@Override
	public String findPageNameCodeByDesc(String pageNameDesc) throws Exception {
		// TODO Auto-generated method stub
		String pageNameCode = null;
		String key = this.getPageNameKey(pageNameDesc, PAGE_NAME_DESC);
		
		if(Cache.containsKey(REGION_GSUICONFIG_PAGE_NAME, key)){
			pageNameCode = (String)Cache.get(REGION_GSUICONFIG_PAGE_NAME, key);
		} else {
			pageNameCode = pageNameDao.findPageNameCodeByDesc(pageNameDesc);
			Cache.put(REGION_GSUICONFIG_PAGE_NAME, key, pageNameCode);
		}
		
		return pageNameCode;
	}

	@Override
	public List<String> findAllPageNameDesc() throws Exception {
		// TODO Auto-generated method stub
		List<String> pageNameList = new ArrayList<String>();
		String key = this.getAllPageNameDescKey();
		
		if(Cache.containsKey(REGION_GSUICONFIG_PAGE_NAME, key)){
			pageNameList = (List<String>)Cache.get(REGION_GSUICONFIG_PAGE_NAME, key);
		} else {
			pageNameList = pageNameDao.findAllPageNameDesc();
			Cache.put(REGION_GSUICONFIG_PAGE_NAME, key, pageNameList);
		}
		
		return pageNameList;
	}
	
	private String getPageNameKey(String pageName, String flag){
		if(PAGE_NAME_CODE.equals(flag)){
			return PAGE_NAME_CODE + "_" + pageName;
		} else if(PAGE_NAME_DESC.equals(flag)) {
			return PAGE_NAME_DESC + "_" + pageName;
		}
		return null;
	}
	
	private String getAllPageNameDescKey(){
		return PAGE_NAME_LIST;
	}

}
