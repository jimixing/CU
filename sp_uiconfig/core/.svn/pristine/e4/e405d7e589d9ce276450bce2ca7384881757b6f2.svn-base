package com.ebao.gs.pol.prdt.config.service.impl;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.gs.pol.prdt.config.dao.impl.ConfigSqlExecuteDaoImpl;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;

public class ConfigPageServiceImpl implements ConfigPageService {

	private ConfigSectionService sectionService = new ConfigSectionServiceImpl();
	private ConfigSqlExecuteDao sqlDao = new ConfigSqlExecuteDaoImpl();

	@Override
	public void addConfigPage(ConfigPageInfo pageInfo, boolean isUpdate, String pageTitle) throws Exception {
		saveConfigPage(pageInfo, isUpdate, pageTitle);
		// for(ConfigSectionInfo sectionInfo:pageInfo.getSectionList()){
		// sectionService.addConfigSection(sectionInfo);
		//
		// }
	}

	@Override
	public void delConfigPage(ConfigPageInfo pageInfo) throws Exception {
		for (ConfigSectionInfo sectionInfo : pageInfo.getSectionList()) {
			sectionService.delConfigSection(sectionInfo);
		}
		deletePageInfo(Integer.parseInt(pageInfo.getPageId()));
	}

	public void saveConfigPage(ConfigPageInfo pageInfo, boolean isUpdate, String pageTitle) throws Exception {
		int pageid = sqlDao.savePageInfo(pageInfo, isUpdate);
		pageInfo.setPageId(String.valueOf(pageid));
		if (pageTitle != null && isUpdate) {
			sqlDao.updatePrdtFieldPageName(pageTitle, pageInfo);
		}

	}

	public void deletePageInfo(int pageid) throws Exception {
		sqlDao.delPageInfo(pageid);
	}

	@Override
	public List<ConfigPageInfo> loadConfigPageList(String productName) throws Exception {
		return sqlDao.getPageInfoByProduct(productName);
	}

	@Override
	public ConfigPageInfo getConfigPageInfoById(int pageID) throws Exception {
		// TODO Auto-generated method stub
		return sqlDao.getConfigPageInfoByPageId(pageID);
	}

}
