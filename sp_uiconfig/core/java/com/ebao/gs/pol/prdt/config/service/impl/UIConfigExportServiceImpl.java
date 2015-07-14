package com.ebao.gs.pol.prdt.config.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.ebao.gs.pol.constant.UIConfigExcelConstant;
import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.gs.pol.prdt.config.dao.impl.ConfigSqlExecuteDaoImpl;
import com.ebao.gs.pol.prdt.config.service.PageNameService;
import com.ebao.gs.pol.prdt.config.service.UIConfigExportService;

public class UIConfigExportServiceImpl implements UIConfigExportService {
	
	private ConfigSqlExecuteDao configSqlExecuteDao = new ConfigSqlExecuteDaoImpl();
	private PageNameService pageNameService = new PageNameServiceImpl();
	
	@Override
	public void uiconfigExport(String productName) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String getUIConfigExportFileName(String productName) {
		return productName + "--" + getDateTimeString() + ".xls";
	}
	
	private String getDateTimeString() {
		Date date = com.ebao.pub.framework.AppContext.getCurrentUserLocalTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return dateFormat.format(date);
	}
	
	/**
	 * Get excel file byte array out put stream.
	 * @param configPageInfoList
	 * @return
	 * @throws Exception
	 */
	@Override
	public ByteArrayOutputStream getExcelFileStream(String productName) throws Exception {
		List<ConfigPageInfo> configPageInfoList = configSqlExecuteDao.getPageInfoByProduct(productName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 1. Create Page & Section & Field Sheet
		HSSFSheet pageSheet = workbook.createSheet(UIConfigExcelConstant.SHEET_NAME_PAGE);
		HSSFSheet sectionSheet = workbook.createSheet(UIConfigExcelConstant.SHEET_NAME_SECTION);
		HSSFSheet fieldSheet = workbook.createSheet(UIConfigExcelConstant.SHEET_NAME_FIELD);
		
		pageSheet.setDefaultColumnWidth(20);
		//pageSheet.setDefaultRowHeightInPoints(20);
		sectionSheet.setDefaultColumnWidth(20);
		//sectionSheet.setDefaultRowHeightInPoints(20);
		fieldSheet.setDefaultColumnWidth(20);
		//fieldSheet.setDefaultRowHeightInPoints(20);
	    
	    // 2. Create Page & Section & Field Header
	    this.createPageRowHeader(workbook, pageSheet);
	    this.createSectionRowHeader(workbook, sectionSheet);
	    this.createFieldRowBigHeader(workbook, fieldSheet);
	    this.createFieldRowHeader(workbook, fieldSheet);
	    
	    //
	    pageSheet.createFreezePane(0, 1);
		sectionSheet.createFreezePane(0, 1);
		fieldSheet.createFreezePane(0, 2);
	    
	    int sectionIndex = 1;
	    int fieldIndex = 2;
		for(int pageIndex = 0; pageIndex < configPageInfoList.size(); pageIndex ++) {
			// 3. Create page information row content
			ConfigPageInfo configPageInfo = configPageInfoList.get(pageIndex);
			this.createPageRowContent(pageSheet, configPageInfo, pageIndex + 1);
			
			for(int sIndex = 0; sIndex < configPageInfo.getSectionList().size(); sIndex ++){
				// 4. Create section information row content
				ConfigSectionInfo configSectionInfo = configPageInfo.getSectionList().get(sIndex);
				this.createSectionRowContent(sectionSheet, configSectionInfo, sectionIndex);
				sectionIndex ++;
				
				for(int fIndex = 0; fIndex < configSectionInfo.getFieldList().size(); fIndex++){
					// 6. Create field information row content
					ConfigFieldInfo configFieldInfo = configSectionInfo.getFieldList().get(fIndex);
					this.createFieldRowContent(fieldSheet, configFieldInfo, fieldIndex);
					fieldIndex ++;
				}
				
				for(int ssIndex = 0; ssIndex<configSectionInfo.getSubSectionList().size(); ssIndex++){
					// 5. Create sub section information row content
					ConfigSectionInfo configSubSectionInfo = configSectionInfo.getSubSectionList().get(ssIndex);
					this.createSectionRowContent(sectionSheet, configSubSectionInfo, sectionIndex);
					sectionIndex ++;
					
					for(int fIndex = 0; fIndex < configSubSectionInfo.getFieldList().size(); fIndex++){
						// 6. Create field information row content
						ConfigFieldInfo configFieldInfo = configSubSectionInfo.getFieldList().get(fIndex);
						this.createFieldRowContent(fieldSheet, configFieldInfo, fieldIndex);
						fieldIndex ++;
					}
				}
			}
			
			/*if(pageIndex != (configPageInfoList.size() - 1)){
				this.createSectionRowSeparator(workbook, sectionSheet, sectionIndex);
				sectionIndex ++;
			}*/
		}
		
		workbook.write(bos);
		return bos;
	}
	
	private HSSFCellStyle getHeaderCellStyle(HSSFWorkbook workbook){
		// Set HSSF Font
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("Arial");
		font.setItalic(false);
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		// Set HSSF Cell Style
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setFont(font);
		
		// Set Cell Style Border
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	/**
	 * Create page row header.
	 * @param pageSheet
	 * @throws Exception 
	 */
	private void createPageRowHeader(HSSFWorkbook workbook, HSSFSheet pageSheet) throws Exception{
		HSSFRow pageRow = pageSheet.createRow(0);
		pageRow.setHeightInPoints((short)30);
		
		for(int i=0; i<UIConfigExcelConstant.PAGE_HEADER_ARRAY.length; i++){
			HSSFCell cell = pageRow.createCell(i);
			cell.setCellStyle(this.getHeaderCellStyle(workbook));
			cell.setCellValue(UIConfigExcelConstant.PAGE_HEADER_ARRAY[i]);
		}
		
		int maxRowNum = SpreadsheetVersion.EXCEL97.getLastRowIndex();//maxRowNum=65535
		this.setSheetExplicitListConstraint(pageSheet, this.getPageNameList(), 1, maxRowNum, 1, 1);
	}
	
	/**
	 * Create section row header.
	 * @param sectionSheet
	 */
	private void createSectionRowHeader(HSSFWorkbook workbook, HSSFSheet sectionSheet) throws Exception {
		HSSFRow sectionRow = sectionSheet.createRow(0);
		sectionRow.setHeightInPoints((short)30);
		
		for(int i=0; i<UIConfigExcelConstant.SECTION_HEADER_ARRAY.length; i++){
			HSSFCell cell = sectionRow.createCell(i);
			cell.setCellStyle(this.getHeaderCellStyle(workbook));
			cell.setCellValue(UIConfigExcelConstant.SECTION_HEADER_ARRAY[i]);
		}
		
		int maxRowNum = SpreadsheetVersion.EXCEL97.getLastRowIndex();//maxRowNum=65535
		this.setSheetExplicitListConstraint(sectionSheet, this.getPageNameList(), 1, maxRowNum, 0, 0);//2 means Page Name
		this.setSheetExplicitListConstraint(sectionSheet, UIConfigExcelConstant.SECTION_TYPE_ARRAY, 1, maxRowNum, 5, 5);//5 means Type
	}
	
	private void createFieldRowBigHeader(HSSFWorkbook workbook, HSSFSheet fieldSheet){
		HSSFRow fieldRow = fieldSheet.createRow(0);
		fieldRow.setHeightInPoints((short)20);
		
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 22));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 23, 28));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 29, 36));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 37, 38));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 39, 41));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 42, 46));
		fieldSheet.addMergedRegion(new CellRangeAddress(0, 0, 47, 49));
		
		for(int i=0; i<UIConfigExcelConstant.FIELD_BIG_DEADER_ARRAY.length; i++){
			HSSFCell cell = fieldRow.createCell(Integer.parseInt(UIConfigExcelConstant.FIELD_BIG_DEADER_ARRAY[i][0]));
			cell.setCellStyle(this.getHeaderCellStyle(workbook));
			cell.setCellValue(UIConfigExcelConstant.FIELD_BIG_DEADER_ARRAY[i][1]);
		}
	}
	
	private void createFieldRowHeader(HSSFWorkbook workbook, HSSFSheet fieldSheet) throws Exception {
		HSSFRow fieldRow = fieldSheet.createRow(1);
		fieldRow.setHeightInPoints((short)30);
		
		for(int i=0; i<UIConfigExcelConstant.FIELD_HEADER_ARRAY.length; i++){
			HSSFCell cell = fieldRow.createCell(i);
			cell.setCellStyle(this.getHeaderCellStyle(workbook));
			cell.setCellValue(UIConfigExcelConstant.FIELD_HEADER_ARRAY[i]);
		}
		
		int maxRowNum = SpreadsheetVersion.EXCEL97.getLastRowIndex();//maxRowNum=65535
		this.setSheetExplicitListConstraint(fieldSheet, this.getPageNameList(), 2, maxRowNum, 0, 0);// firstCol = 0, endCol = 0 means pageName
		this.setSheetExplicitListConstraint(fieldSheet, this.getFieldTypeList(), 2, maxRowNum, 6, 6);//5 means Display Type
		this.setSheetExplicitListConstraint(fieldSheet, UIConfigExcelConstant.FIELD_IO_ARRAY, 2, maxRowNum, 10, 10);//10 means IO
		
		this.setSheetExplicitListConstraint(fieldSheet, UIConfigExcelConstant.TRUE_FALSE_ARRAY, 1, maxRowNum, 23, 23);
		this.setSheetExplicitListConstraint(fieldSheet, UIConfigExcelConstant.TRUE_FALSE_ARRAY, 1, maxRowNum, 24, 24);
		this.setSheetExplicitListConstraint(fieldSheet, UIConfigExcelConstant.TRUE_FALSE_ARRAY, 1, maxRowNum, 25, 25);
		this.setSheetExplicitListConstraint(fieldSheet, UIConfigExcelConstant.TRUE_FALSE_ARRAY, 1, maxRowNum, 36, 36);
		this.setSheetExplicitListConstraint(fieldSheet, UIConfigExcelConstant.TRUE_FALSE_ARRAY, 1, maxRowNum, 42, 42);
	}
	
	private void createPageRowContent(HSSFSheet pageSheet, ConfigPageInfo configPageInfo, Integer index) throws Exception {
		HSSFRow pageRow = pageSheet.createRow(index);
		
		pageRow.createCell(0).setCellValue(configPageInfo.getPageSequence());
		pageRow.createCell(1).setCellValue(pageNameService.findPageNameDescByCode(configPageInfo.getPageName()));
		pageRow.createCell(2).setCellValue(configPageInfo.getPageDescription());
	}
	
	protected static HSSFSheet setSheetExplicitListConstraint(HSSFSheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {  
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        HSSFDataValidation dataValidationList = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidationList);
        
        return sheet;
    } 
	
	private void createSectionRowContent(HSSFSheet sectionSheet, ConfigSectionInfo configSectionInfo, Integer index) throws Exception {
		HSSFRow sectionRow = sectionSheet.createRow(index);
		
		String pageName = null;
		if(configSectionInfo.getPage() != null){
			pageName = configSectionInfo.getPage().getPageName();
		} else {
			if(configSectionInfo.getParentSection() != null){
				pageName = configSectionInfo.getParentSection().getPage().getPageName();
			}
		}
		
		sectionRow.createCell(0).setCellValue(pageNameService.findPageNameDescByCode(pageName));// Page Name
		sectionRow.createCell(1).setCellValue(configSectionInfo.getSectionName());
		sectionRow.createCell(2).setCellValue(configSectionInfo.getSubSectionName());
		sectionRow.createCell(3).setCellValue(configSectionInfo.getSectionSequence());
		sectionRow.createCell(4).setCellValue(configSectionInfo.getSectionTitle());
		//sectionRow.createCell(5).setCellValue(configSectionInfo.getSectionCode());
		//sectionRow.createCell(5).setCellValue(configSectionInfo.getSectionType());
		sectionRow.createCell(5).setCellValue(configSectionInfo.getType());
		//sectionRow.createCell(6).setCellValue(configSectionInfo.getSectionTemplate());
		sectionRow.createCell(6).setCellValue(configSectionInfo.getDescription());
		//sectionRow.createCell(10).setCellValue(configSectionInfo.getDataTableProvider());
	}
	
	/**
	 * Create field row content.
	 * @param fieldSheet
	 * @param configFieldInfo
	 * @param index
	 */
	private void createFieldRowContent(HSSFSheet fieldSheet, ConfigFieldInfo configFieldInfo, Integer index) throws Exception {
		HSSFRow fieldRow = fieldSheet.createRow(index);
		
		// page & section & sub section attribute
		String pageName = null;
		if(configFieldInfo.getSection().getPage() != null){
			pageName = configFieldInfo.getSection().getPage().getPageName();
		} else {
			if(configFieldInfo.getSection().getParentSection() != null){
				pageName = configFieldInfo.getSection().getParentSection().getPage().getPageName();
			}
		}
		
		fieldRow.createCell(0).setCellValue(pageNameService.findPageNameDescByCode(pageName));
		fieldRow.createCell(1).setCellValue(configFieldInfo.getSection().getSectionName());
		fieldRow.createCell(2).setCellValue(configFieldInfo.getSection().getSubSectionName());
		
		// field attribute
		fieldRow.createCell(3).setCellValue(configFieldInfo.getSequence());
		fieldRow.createCell(4).setCellValue(configFieldInfo.getFieldName());
		fieldRow.createCell(5).setCellValue(configFieldInfo.getDisplayLabel());
		fieldRow.createCell(6).setCellValue(configFieldInfo.getDisplayType());
		fieldRow.createCell(7).setCellValue(configFieldInfo.getDefaultValue());
		fieldRow.createCell(8).setCellValue(configFieldInfo.getColspan());
		fieldRow.createCell(9).setCellValue(configFieldInfo.getFormat());
		fieldRow.createCell(10).setCellValue(configFieldInfo.getIo());
		
		fieldRow.createCell(11).setCellValue(configFieldInfo.getHelpText());
		fieldRow.createCell(12).setCellValue(configFieldInfo.getEmptyText());
		fieldRow.createCell(13).setCellValue(configFieldInfo.getInputWidth());
		fieldRow.createCell(14).setCellValue(configFieldInfo.getErrorMsg());
		fieldRow.createCell(15).setCellValue(configFieldInfo.getAllowDecimal());
		fieldRow.createCell(16).setCellValue(configFieldInfo.getDecimalPrecision());
		fieldRow.createCell(17).setCellValue(configFieldInfo.getValidationGroup());
		fieldRow.createCell(18).setCellValue(configFieldInfo.getMinValue());
		fieldRow.createCell(19).setCellValue(configFieldInfo.getMaxValue());
		fieldRow.createCell(20).setCellValue(configFieldInfo.getMinLength());
		fieldRow.createCell(21).setCellValue(configFieldInfo.getMaxLength());
		fieldRow.createCell(22).setCellValue(configFieldInfo.getValueBinding());
		
		// Readonly & Required & Display
		fieldRow.createCell(23).setCellValue(configFieldInfo.isReadOnly());
		fieldRow.createCell(24).setCellValue(configFieldInfo.isRequired());
		fieldRow.createCell(25).setCellValue(configFieldInfo.isVisible());
		fieldRow.createCell(26).setCellValue(configFieldInfo.getReadOnlyEx());
		fieldRow.createCell(27).setCellValue(configFieldInfo.getRequieredEx());
		fieldRow.createCell(28).setCellValue(configFieldInfo.getVisibleEx());
				
		// Quote -- Readonly & Required & Display
		/*fieldRow.createCell(32).setCellValue(configFieldInfo.isQuoteReadonly());
		fieldRow.createCell(33).setCellValue(configFieldInfo.isQuoteRequired());
		fieldRow.createCell(34).setCellValue(configFieldInfo.isQuoteDisplay());
		fieldRow.createCell(35).setCellValue(configFieldInfo.getQuoteReadonlyCondition());
		fieldRow.createCell(36).setCellValue(configFieldInfo.getQuoteRequiredCondition());
		fieldRow.createCell(37).setCellValue(configFieldInfo.getQuoteDisplayCondition());*/
				
		// Policy -- Readonly & Required & Display
		/*fieldRow.createCell(38).setCellValue(configFieldInfo.isPolicyReadonly());
		fieldRow.createCell(39).setCellValue(configFieldInfo.isPolicyRequired());
		fieldRow.createCell(40).setCellValue(configFieldInfo.isPolicyDisplay());
		fieldRow.createCell(41).setCellValue(configFieldInfo.getPolicyReadonlyCondition());
		fieldRow.createCell(42).setCellValue(configFieldInfo.getPolicyRequiredCondition());
		fieldRow.createCell(43).setCellValue(configFieldInfo.getPolicyDisplayCondition());*/
				
		// Endorsement -- Readonly & Required & Display
		/*fieldRow.createCell(44).setCellValue(configFieldInfo.isEndoReadonly());
		fieldRow.createCell(45).setCellValue(configFieldInfo.isEndoRequired());
		fieldRow.createCell(46).setCellValue(configFieldInfo.isEndoDisplay());
		fieldRow.createCell(47).setCellValue(configFieldInfo.getEndoReadonlyCondition());
		fieldRow.createCell(48).setCellValue(configFieldInfo.getEndoRequiredCondition());
		fieldRow.createCell(49).setCellValue(configFieldInfo.getEndoDisplayCondition());*/
				
		// Renewal -- Readonly & Required & Display
		/*fieldRow.createCell(50).setCellValue(configFieldInfo.isRenewReadonly());
		fieldRow.createCell(51).setCellValue(configFieldInfo.isRenewRequired());
		fieldRow.createCell(52).setCellValue(configFieldInfo.isRenewDisplay());
		fieldRow.createCell(53).setCellValue(configFieldInfo.getRenewReadonlyCondition());
		fieldRow.createCell(54).setCellValue(configFieldInfo.getRenewRequiredCondition());
		fieldRow.createCell(55).setCellValue(configFieldInfo.getRenewDisplayCondition());*/
				
		// Code Table component attribute
		fieldRow.createCell(29).setCellValue(configFieldInfo.getTableName());
		fieldRow.createCell(30).setCellValue(configFieldInfo.getCodeTabelDisplayType());
		fieldRow.createCell(31).setCellValue(configFieldInfo.getWhereClause());
		fieldRow.createCell(32).setCellValue(configFieldInfo.getOrderBy());
		fieldRow.createCell(33).setCellValue(configFieldInfo.getChildItem());
		fieldRow.createCell(34).setCellValue(configFieldInfo.getParentItem());
		fieldRow.createCell(35).setCellValue(configFieldInfo.getUnitedKey());
		fieldRow.createCell(36).setCellValue(configFieldInfo.isNoSelectionLoad());
				
		// Text Area component attribute
		fieldRow.createCell(37).setCellValue(configFieldInfo.getTextAreaRows());
		fieldRow.createCell(38).setCellValue(configFieldInfo.getTextAreaCols());
		
		// Data Table attribute
		fieldRow.createCell(39).setCellValue(configFieldInfo.getColumnWidth());
		fieldRow.createCell(40).setCellValue(configFieldInfo.getTableTitleAlign());
		fieldRow.createCell(41).setCellValue(configFieldInfo.getTableColumnAlign());
				
		// UIC2 Component JSF event attribute
		fieldRow.createCell(42).setCellValue(configFieldInfo.isAjaxEnabled());
		fieldRow.createCell(43).setCellValue(configFieldInfo.getAction());
		fieldRow.createCell(44).setCellValue(configFieldInfo.getActionListener());
		fieldRow.createCell(45).setCellValue(configFieldInfo.getOnChangeListener());
		fieldRow.createCell(46).setCellValue(configFieldInfo.getUpdateFieldIds());
		
		// UIC2 Component JavaScript event attribute
		fieldRow.createCell(47).setCellValue(configFieldInfo.getOnBlur());
		fieldRow.createCell(48).setCellValue(configFieldInfo.getOnChange());
		fieldRow.createCell(49).setCellValue(configFieldInfo.getOnButtonClick());
	}
	
	private void createSectionRowSeparator(HSSFWorkbook workbook, HSSFSheet sectionSheet, Integer index){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		HSSFRow sectionRow = sectionSheet.createRow(index);
		//SECTION_COLUMN_COUNT
		for(int i=0; i<UIConfigExcelConstant.SECTION_HEADER_ARRAY.length; i++){
			HSSFCell cell = sectionRow.createCell(i);
			cell.setCellStyle(cellStyle);
		}
	}
	
	private String[] getPageNameList() throws Exception {
		List<String> pageNameList = pageNameService.findAllPageNameDesc();
		String[] pageNameArray = (String[])pageNameList.toArray(new String[pageNameList.size()]);
		
		return pageNameArray;
	}
	
	private String[] getFieldTypeList() throws Exception {
		List<String> fieldTypeList = configSqlExecuteDao.getAllFieldType();
		String[] fieldTypeArray = (String[])fieldTypeList.toArray(new String[fieldTypeList.size()]);
		
		return fieldTypeArray;
	}

}

