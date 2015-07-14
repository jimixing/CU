package com.ebao.gs.pol.prdt.config.service.impl;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ebao.foundation.common.resource.ThreadBindResourceManager;
import com.ebao.foundation.commons.exceptions.ExceptionUtil;
import com.ebao.foundation.commons.exceptions.GenericException;
import com.ebao.foundation.module.global.WebConstant;
import com.ebao.gs.pol.constant.UIConfigExcelConstant;
import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.service.ConfigDynamicFieldService;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;
import com.ebao.gs.pol.prdt.config.service.ConfigUIUtilService;
import com.ebao.gs.pol.prdt.config.service.PageNameService;
import com.ebao.gs.pol.prdt.config.service.SectionRelationService;
import com.ebao.gs.pol.prdt.config.service.UIConfigImportService;
import com.ebao.gs.pol.util.ExcelUtil;
import com.ebao.gs.pol.validator.UIConfigImportValidator;
import com.ebao.pub.util.Trans;

public class UIConfigImportServiceImpl implements UIConfigImportService {
	
	private ConfigUIUtilService configService = new ConfigUIUtilServiceImpl();
	private ConfigPageService configPageService = new ConfigPageServiceImpl();
	private ConfigSectionService configSectionService = new ConfigSectionServiceImpl();
	private ConfigDynamicFieldService configDynamicFieldService = new ConfigDynamicFieldServiceImpl();
	private SectionRelationService sectionRelationService = new SectionRelationServiceImpl();
	private PageNameService pageNameService = new PageNameServiceImpl();
	
	@Override
	public void uiconfigImport(String productName, String fileName) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) ThreadBindResourceManager.getBindResource(WebConstant.REQUEST);
		InputStream inputStream = request.getInputStream();
		
		Workbook workbook = null;
		
		if (!inputStream.markSupported()) {
	        inputStream = new PushbackInputStream(inputStream, 8);
	    }
	    if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
	        workbook = new HSSFWorkbook(inputStream);
	    } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
	        workbook = new XSSFWorkbook(OPCPackage.open(inputStream));
	    } else {
	    	throw new IllegalArgumentException("Apache POI can't resolve you excel version.");
	    }
	    
		/*if (StringUtils.endsWithIgnoreCase(fileName, ".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else if (StringUtils.endsWithIgnoreCase(fileName, ".xlsx")) { 
			//workbook = new XSSFWorkbook(inputStream);
			workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
		}*/
		
		UserTransaction transaction = null;
		try {
			transaction = Trans.getUserTransaction();
			transaction.begin();
			
			UIConfigImportValidator.validate(workbook);
			
			this.handlerPageSheet(workbook, productName);
			this.handlerSectionSheet(workbook, productName);
			this.handlerFieldSheet(workbook, productName);

			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			GenericException ge = ExceptionUtil.parse(e);
			throw ge;
		}
	}
	
	protected void handlerPageSheet(Workbook workbook, String productName) throws Exception {
		Sheet sheet = workbook.getSheet(UIConfigExcelConstant.SHEET_NAME_PAGE);
		if (sheet == null) {
			return;
		}
		
		String productId = configService.getProductId(productName);
		
		List<ConfigPageInfo> configPageInfoList = new ArrayList<ConfigPageInfo>();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}
			
			ConfigPageInfo configPageInfo = new ConfigPageInfo();
			configPageInfo.setProductName(productName);
			configPageInfo.setProductId(productId);
			
			configPageInfo.setPageSequence(Double.valueOf(ExcelUtil.getValue(row.getCell(0))).intValue());
			configPageInfo.setPageName(pageNameService.findPageNameCodeByDesc(ExcelUtil.getValue(row.getCell(1))));
			//configPageInfo.setPageTitle(pageNameService.findPageNameCodeByDesc(ExcelUtil.getValue(row.getCell(1))));
			configPageInfo.setPageDescription(ExcelUtil.getValue(row.getCell(2)));
			
			configPageInfoList.add(configPageInfo);
		}
		
		// Delete all page
		List<ConfigPageInfo> configPageInfoTempList = configPageService.loadConfigPageList(productName);
		for(ConfigPageInfo configPageInfo : configPageInfoTempList){
			configPageService.delConfigPage(configPageInfo);
		}
		
		// Add all page
		for(ConfigPageInfo configPageInfo : configPageInfoList){
			configPageService.addConfigPage(configPageInfo, false, configPageInfo.getPageName());
		}
	}
	
	protected void handlerSectionSheet(Workbook workbook, String productName) throws Exception {
		Sheet sheet = workbook.getSheet(UIConfigExcelConstant.SHEET_NAME_SECTION);
		if (sheet == null) {
			return;
		}
		
		//String productId = configService.getProductId(productName);
		List<ConfigPageInfo> configPageInfoTempList = configPageService.loadConfigPageList(productName);
		
		List<ConfigSectionInfo> configSectionInfoList = new ArrayList<ConfigSectionInfo>();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}
			
			ConfigSectionInfo configSectionInfo = new ConfigSectionInfo();
			String pageName = pageNameService.findPageNameCodeByDesc(ExcelUtil.getValue(row.getCell(0)));
			for(ConfigPageInfo configPageInfo : configPageInfoTempList){
				
				if(configPageInfo.getPageName().equals(pageName)){
					configSectionInfo.setPage(configPageInfo);
					break;
				}
			}
			
			configSectionInfo.setSectionName(ExcelUtil.getValue(row.getCell(1)));
			configSectionInfo.setSubSectionName(ExcelUtil.getValue(row.getCell(2)));
			configSectionInfo.setSectionSequence(Double.valueOf(ExcelUtil.getValue(row.getCell(3))).intValue());
			configSectionInfo.setSectionTitle(ExcelUtil.getValue(row.getCell(4)));
			
			String tagName = sectionRelationService.getTagNameBySectionName(
					configSectionInfo.getSectionName(), configSectionInfo.getSubSectionName());
			configSectionInfo.setSectionType(tagName);
			
			configSectionInfo.setType(ExcelUtil.getValue(row.getCell(5)));
			configSectionInfo.setDescription(ExcelUtil.getValue(row.getCell(6)));	
			configSectionInfo.setSectionCode(configSectionInfo.getSectionName());
			
			configSectionInfoList.add(configSectionInfo);
		}
		
		// Delete All Section
		for(ConfigPageInfo configPageInfo : configPageInfoTempList){
			for(ConfigSectionInfo configSectionInfo : configPageInfo.getSectionList()){
				configSectionService.delConfigSection(configSectionInfo);
			}
		}
		
		// Add All Section
		for(ConfigSectionInfo configSectionInfo : configSectionInfoList){
			configSectionService.addConfigSection(configSectionInfo, false, configSectionInfo.getSectionTitle());
		}
	}
	
	protected void handlerFieldSheet(Workbook workbook, String productName) throws Exception {
		Sheet sheet = workbook.getSheet(UIConfigExcelConstant.SHEET_NAME_FIELD);
		if (sheet == null) {
			return;
		}
		
		List<ConfigPageInfo> configPageInfoTempList = configPageService.loadConfigPageList(productName);
				
		List<ConfigFieldInfo> configFieldInfoList = new ArrayList<ConfigFieldInfo>();
		for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}
					
			ConfigFieldInfo configFieldInfo = new ConfigFieldInfo();
			String pageName = pageNameService.findPageNameCodeByDesc(ExcelUtil.getValue(row.getCell(0)));
			for(ConfigPageInfo configPageInfo : configPageInfoTempList){
				for(ConfigSectionInfo configSectionInfo : configPageInfo.getSectionList()){
					if(configPageInfo.getPageName().equals(pageName) && configSectionInfo.getSectionName().equals(ExcelUtil.getValue(row.getCell(1)))){						
						if(configSectionInfo.getSubSectionList().size() > 0){
							for(ConfigSectionInfo configSubSectionInfo : configSectionInfo.getSubSectionList()){
								if(configSubSectionInfo.getSubSectionName().equals(ExcelUtil.getValue(row.getCell(2)))){
									configFieldInfo.setSection(configSubSectionInfo);
									break;
								}
							}
						} else {
							configFieldInfo.setSection(configSectionInfo);
							break;
						}
					}
				}
			}
			
			configFieldInfo.setSequence(Double.valueOf(ExcelUtil.getValue(row.getCell(3))).intValue());
			configFieldInfo.setFieldName(ExcelUtil.getValue(row.getCell(4)));
			configFieldInfo.setDisplayLabel(ExcelUtil.getValue(row.getCell(5)));
			configFieldInfo.setDisplayType(ExcelUtil.getValue(row.getCell(6)));
			configFieldInfo.setDefaultValue(ExcelUtil.getValue(row.getCell(7)));
			configFieldInfo.setColspan(Double.valueOf(ExcelUtil.getValue(row.getCell(8))).intValue());
			configFieldInfo.setFormat(ExcelUtil.getValue(row.getCell(9)));
			configFieldInfo.setIo(ExcelUtil.getValue(row.getCell(10)));
			configFieldInfo.setHelpText(ExcelUtil.getValue(row.getCell(11)));
			configFieldInfo.setEmptyText(ExcelUtil.getValue(row.getCell(12)));
			configFieldInfo.setInputWidth(ExcelUtil.getValue(row.getCell(13)));
			configFieldInfo.setErrorMsg(ExcelUtil.getValue(row.getCell(14)));
			configFieldInfo.setAllowDecimal(ExcelUtil.getValue(row.getCell(15)));
			configFieldInfo.setDecimalPrecision(ExcelUtil.getValue(row.getCell(16)));
			configFieldInfo.setValidationGroup(ExcelUtil.getValue(row.getCell(17)));
			configFieldInfo.setMinValue(ExcelUtil.getValue(row.getCell(18)));
			configFieldInfo.setMaxValue(ExcelUtil.getValue(row.getCell(19)));
			configFieldInfo.setMinLength(Double.valueOf(ExcelUtil.getValue(row.getCell(20))).intValue());
			configFieldInfo.setMaxLength(Double.valueOf(ExcelUtil.getValue(row.getCell(21))).intValue());
			configFieldInfo.setValueBinding(ExcelUtil.getValue(row.getCell(22)));
			
			configFieldInfo.setReadOnly(Boolean.valueOf(ExcelUtil.getValue(row.getCell(23))));
			configFieldInfo.setRequired(Boolean.valueOf(ExcelUtil.getValue(row.getCell(24))));
			configFieldInfo.setVisible(Boolean.valueOf(ExcelUtil.getValue(row.getCell(25))));
			configFieldInfo.setReadOnlyEx(ExcelUtil.getValue(row.getCell(26)));
			configFieldInfo.setRequieredEx(ExcelUtil.getValue(row.getCell(27)));
			configFieldInfo.setVisibleEx(ExcelUtil.getValue(row.getCell(28)));
			
			configFieldInfo.setTableName(ExcelUtil.getValue(row.getCell(29)));
			configFieldInfo.setCodeTabelDisplayType(ExcelUtil.getValue(row.getCell(30)));
			configFieldInfo.setWhereClause(ExcelUtil.getValue(row.getCell(31)));
			configFieldInfo.setOrderBy(ExcelUtil.getValue(row.getCell(32)));
			configFieldInfo.setChildItem(ExcelUtil.getValue(row.getCell(33)));
			configFieldInfo.setParentItem(ExcelUtil.getValue(row.getCell(34)));
			configFieldInfo.setUnitedKey(ExcelUtil.getValue(row.getCell(35)));
			configFieldInfo.setNoSelectionLoad(Boolean.valueOf(ExcelUtil.getValue(row.getCell(36))));
			
			configFieldInfo.setTextAreaRows(Double.valueOf(ExcelUtil.getValue(row.getCell(37))).intValue());
			configFieldInfo.setTextAreaCols(Double.valueOf(ExcelUtil.getValue(row.getCell(38))).intValue());
			configFieldInfo.setColumnWidth(Double.valueOf(ExcelUtil.getValue(row.getCell(39))).intValue());
			configFieldInfo.setTableTitleAlign(ExcelUtil.getValue(row.getCell(40)));
			configFieldInfo.setTableColumnAlign(ExcelUtil.getValue(row.getCell(41)));
			
			configFieldInfo.setAjaxEnabled(Boolean.valueOf(ExcelUtil.getValue(row.getCell(42))));
			configFieldInfo.setAction(ExcelUtil.getValue(row.getCell(43)));
			configFieldInfo.setActionListener(ExcelUtil.getValue(row.getCell(44)));
			configFieldInfo.setOnChangeListener(ExcelUtil.getValue(row.getCell(45)));
			configFieldInfo.setUpdateFieldIds(ExcelUtil.getValue(row.getCell(46)));
			
			configFieldInfo.setOnBlur(ExcelUtil.getValue(row.getCell(47)));
			configFieldInfo.setOnChange(ExcelUtil.getValue(row.getCell(48)));
			configFieldInfo.setOnButtonClick(ExcelUtil.getValue(row.getCell(49)));
			
			configFieldInfoList.add(configFieldInfo);
		}
		
		// Delete All Field
		/*for(ConfigPageInfo configPageInfo : configPageInfoTempList){
			for(ConfigSectionInfo configSectionInfo : configPageInfo.getSectionList()){
				for(ConfigFieldInfo configFieldInfo : configSectionInfo.getFieldList()){
					configDynamicFieldService.deleteDynamicFieldConfig(configFieldInfo);
				}
				
				for(ConfigSectionInfo configSubSectionInfo : configSectionInfo.getSubSectionList()){
					for(ConfigFieldInfo configFieldInfo : configSubSectionInfo.getFieldList()){
						configDynamicFieldService.deleteDynamicFieldConfig(configFieldInfo);
					}
				}
			}
		}*/
		
		// Add All Field
		for(ConfigFieldInfo configFieldInfo : configFieldInfoList){
			configDynamicFieldService.addDynamicFieldConfig(configFieldInfo, 0, false);
		}
	}

}

