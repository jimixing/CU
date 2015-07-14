package com.ebao.gs.pol.prdt.config.service;

import java.util.List;

public interface PageNameService {
	
	public String findPageNameDescByCode(String pageNameCode) throws Exception;
	
	public String findPageNameCodeByDesc(String pageNameDesc) throws Exception;
	
	public List<String> findAllPageNameDesc() throws Exception;

}
