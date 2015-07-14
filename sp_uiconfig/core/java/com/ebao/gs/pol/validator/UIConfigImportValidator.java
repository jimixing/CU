package com.ebao.gs.pol.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ebao.foundation.commons.exceptions.AppException;
import com.ebao.gs.pol.constant.UIConfigExcelConstant;
import com.ebao.gs.pol.util.ExcelUtil;
import com.vaadin.ui.Notification;

public class UIConfigImportValidator {
	
	public static void validate(Workbook workbook) throws Exception {
		StringBuffer config = new StringBuffer();

		config.append(validatePageSheet(workbook));
		config.append(validateSectionSheet(workbook));
		config.append(validateFieldSheet(workbook));

		if (config.length() > 0) {
			Notification.show("Warning Message", config.toString(), Notification.Type.WARNING_MESSAGE);
			
			AppException e = new AppException(-1);
			e.setLocalizedErrorMessage(config.toString());
			throw e;
		}
	}

	public static StringBuffer validatePageSheet(Workbook workbook) throws Exception {
		StringBuffer config = new StringBuffer();
		Sheet sheet = workbook.getSheet(UIConfigExcelConstant.SHEET_NAME_PAGE);
		if (sheet == null) {
			return null;
		}

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			// Check Page Name
			String pageSequence = ExcelUtil.getValue(row.getCell(0));
			if (StringUtils.isEmpty(pageSequence)) {
				config.append("Page Sheet: " + (rowNum + 1) + "- A.\n");
			}

			// Check Page Name
			String pageName = ExcelUtil.getValue(row.getCell(1));
			if (StringUtils.isEmpty(pageName)) {
				config.append("Page Sheet: " + (rowNum + 1) + "- B.\n");
			}
		}
		
		return config;
	}

	public static StringBuffer validateSectionSheet(Workbook workbook) throws Exception {
		StringBuffer config = new StringBuffer();
		Sheet sheet = workbook.getSheet(UIConfigExcelConstant.SHEET_NAME_SECTION);
		if (sheet == null) {
			return null;
		}

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			// Check Page Name
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(0)))) {
				config.append("Section Sheet: Page Name " + (rowNum + 1) + "- A.\n");
			}

			// Check Section Name
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(1)))) {
				config.append("Section Sheet: Section Name " + (rowNum + 1) + "- B.\n");
			}

			// Check Section Sequence
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(3)))) {
				config.append("Section Sheet: Section Sequence " + (rowNum + 1) + "- D.\n");
			}

			// Check Section Title
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(4)))) {
				config.append("Section Sheet: Section Title " + (rowNum + 1) + "- E.\n");
			}
		}
		
		return config;
	}

	public static StringBuffer validateFieldSheet(Workbook workbook) throws Exception {
		StringBuffer config = new StringBuffer();
		Sheet sheet = workbook.getSheet(UIConfigExcelConstant.SHEET_NAME_FIELD);
		if (sheet == null) {
			return null;
		}

		for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			// Check Page Name
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(0)))) {
				config.append("Field Sheet: Page Name " + (rowNum + 1) + "- A.\n");
			}

			// Check Section Name
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(1)))) {
				config.append("Field Sheet: Section Name " + (rowNum + 1)
						+ "- B.\n");
			}

			// Check Field Sequence
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(3)))) {
				config.append("Field Sheet: Field Sequence " + (rowNum + 1) + "- D.\n");
			}

			// Check Field Name
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(4)))) {
				config.append("Field Sheet: Field Name " + (rowNum + 1) + "- E.\n");
			}

			// Check Field Label
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(5)))) {
				config.append("Field Sheet: Field Label " + (rowNum + 1) + "- F.\n");
			}

			// Check Display Type
			if (StringUtils.isEmpty(ExcelUtil.getValue(row.getCell(6)))) {
				config.append("Field Sheet: Display Type " + (rowNum + 1) + "- G.\n");
			}
		}
		
		return config;
	}

}
