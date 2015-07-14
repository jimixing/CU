package com.ebao.gs.pol.prdt.config.service.impl;

import com.ebao.gs.pol.prdt.config.dao.SectionRelationDao;
import com.ebao.gs.pol.prdt.config.dao.impl.SectionRelationDaoImpl;
import com.ebao.gs.pol.prdt.config.service.SectionRelationService;

public class SectionRelationServiceImpl implements SectionRelationService {
	
	private SectionRelationDao sectionRelationDao = new SectionRelationDaoImpl();

	@Override
	public String getTagNameBySectionName(String sectionName, String subSectionName) throws Exception {
		// TODO Auto-generated method stub
		return sectionRelationDao.getTagNameBySectionName(sectionName, subSectionName);
	}

}
