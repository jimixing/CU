package com.ebao.gs.pol.prdt.config;

import java.io.Serializable;

public class ConfigFieldInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String displayLabel = "";
	private String fieldName = "";
	private String displayType;
	private int prdtId;
	
	private boolean isDynamic;
	private boolean isEdit;
	private ConfigSectionInfo section;
	private int fieldCode;
	private int fieldId;
	private int sequence;
	
	private String valueBinding;
	private String defaultValue = "";
	private int colspan = 1;
	private String format = "";
	
	private String io="in";
	private String helpText;
	private String emptyText;
	private String inputWidth;
	private String errorMsg;
	private String allowDecimal;
	private String decimalPrecision;
	private String validationGroup;
	private String minValue;
	private String maxValue;
	private int minLength;
	private int maxLength;
	
	// Readonly & Required & Display
	private boolean readOnly;
	private boolean required;
	private boolean visible = true;
	private String readOnlyEx = "";
	private String requieredEx = "";
	private String visibleEx = "";
	
	// Quote -- Readonly & Required & Display
	private boolean quoteReadonly;
	private boolean quoteRequired;
	private boolean quoteDisplay;
	private String quoteReadonlyCondition;
	private String quoteRequiredCondition;
	private String quoteDisplayCondition;
	
	// Policy -- Readonly & Required & Display
	private boolean policyReadonly;
	private boolean policyRequired;
	private boolean policyDisplay;
	private String policyReadonlyCondition;
	private String policyRequiredCondition;
	private String policyDisplayCondition;
		
	// Endorsement -- Readonly & Required & Display
	private boolean endoReadonly;
	private boolean endoRequired;
	private boolean endoDisplay;
	private String endoReadonlyCondition;
	private String endoRequiredCondition;
	private String endoDisplayCondition;
		
	// Renewal -- Readonly & Required & Display
	private boolean renewReadonly;
	private boolean renewRequired;
	private boolean renewDisplay;
	private String renewReadonlyCondition;
	private String renewRequiredCondition;
	private String renewDisplayCondition;
	
	// Code Table component attribute
	private String tableName;
	private String codeTabelDisplayType;
	private String whereClause = "";
	private String orderBy = "";
	private String childItem;
	private String parentItem;
	private String unitedKey = "";
	private boolean noSelectionLoad;
	
	// Text Area component attribute
	private int textAreaRows;
	private int textAreaCols;
	
	// Data Table attribute
	private int columnWidth;
	private String tableTitleAlign;
	private String tableColumnAlign;
					
	// UIC2 Component JSF event attribute
	private boolean ajaxEnabled;
	private String action;
	private String actionListener;
	private String onChangeListener;
	private String updateFieldIds;
	
	// UIC2 Component JavaScript event attribute
	private String onCreate = "";
	private String onActive = "";
	private String onRefresh = "";
	private String onBlur = "";
	private String onChange = "";
	private String onButtonClick = "";

	private String skinboName;

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public ConfigSectionInfo getSection() {
		return section;
	}

	public void setSection(ConfigSectionInfo section) {
		this.section = section;
	}

	public int getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(int prdtId) {
		this.prdtId = prdtId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isDynamic() {
		return isDynamic;
	}

	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public String getSkinboName() {
		return skinboName;
	}

	public void setSkinboName(String skinboName) {
		this.skinboName = skinboName;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(int fieldCode) {
		this.fieldCode = fieldCode;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getVisibleEx() {
		return visibleEx;
	}

	public void setVisibleEx(String visibleEx) {
		this.visibleEx = visibleEx;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequieredEx() {
		return requieredEx;
	}

	public void setRequieredEx(String requieredEx) {
		this.requieredEx = requieredEx;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getReadOnlyEx() {
		return readOnlyEx;
	}

	public void setReadOnlyEx(String readOnlyEx) {
		this.readOnlyEx = readOnlyEx;
	}

	public String getCodeTabelDisplayType() {
		return codeTabelDisplayType;
	}

	public void setCodeTabelDisplayType(String codeTabelDisplayType) {
		this.codeTabelDisplayType = codeTabelDisplayType;
	}

	public String getChildItem() {
		return childItem;
	}

	public void setChildItem(String childItem) {
		this.childItem = childItem;
	}

	public String getUnitedKey() {
		return unitedKey;
	}

	public void setUnitedKey(String unitedKey) {
		this.unitedKey = unitedKey;
	}

	public boolean isNoSelectionLoad() {
		return noSelectionLoad;
	}

	public void setNoSelectionLoad(boolean noSelectionLoad) {
		this.noSelectionLoad = noSelectionLoad;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getOnCreate() {
		return onCreate;
	}

	public void setOnCreate(String onCreate) {
		this.onCreate = onCreate;
	}

	public String getOnActive() {
		return onActive;
	}

	public void setOnActive(String onActive) {
		this.onActive = onActive;
	}

	public String getOnRefresh() {
		return onRefresh;
	}

	public void setOnRefresh(String onRefresh) {
		this.onRefresh = onRefresh;
	}

	public String getOnBlur() {
		return onBlur;
	}

	public void setOnBlur(String onBlur) {
		this.onBlur = onBlur;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getOnButtonClick() {
		return onButtonClick;
	}

	public void setOnButtonClick(String onButtonClick) {
		this.onButtonClick = onButtonClick;
	}

	public String getParentItem() {
		return parentItem;
	}

	public void setParentItem(String parentItem) {
		this.parentItem = parentItem;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getValueBinding() {
		return valueBinding;
	}

	public void setValueBinding(String valueBinding) {
		this.valueBinding = valueBinding;
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	public String getInputWidth() {
		return inputWidth;
	}

	public void setInputWidth(String inputWidth) {
		this.inputWidth = inputWidth;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getAllowDecimal() {
		return allowDecimal;
	}

	public void setAllowDecimal(String allowDecimal) {
		this.allowDecimal = allowDecimal;
	}

	public String getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(String decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	public String getValidationGroup() {
		return validationGroup;
	}

	public void setValidationGroup(String validationGroup) {
		this.validationGroup = validationGroup;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isQuoteReadonly() {
		return quoteReadonly;
	}

	public void setQuoteReadonly(boolean quoteReadonly) {
		this.quoteReadonly = quoteReadonly;
	}

	public boolean isQuoteRequired() {
		return quoteRequired;
	}

	public void setQuoteRequired(boolean quoteRequired) {
		this.quoteRequired = quoteRequired;
	}

	public boolean isQuoteDisplay() {
		return quoteDisplay;
	}

	public void setQuoteDisplay(boolean quoteDisplay) {
		this.quoteDisplay = quoteDisplay;
	}

	public String getQuoteReadonlyCondition() {
		return quoteReadonlyCondition;
	}

	public void setQuoteReadonlyCondition(String quoteReadonlyCondition) {
		this.quoteReadonlyCondition = quoteReadonlyCondition;
	}

	public String getQuoteRequiredCondition() {
		return quoteRequiredCondition;
	}

	public void setQuoteRequiredCondition(String quoteRequiredCondition) {
		this.quoteRequiredCondition = quoteRequiredCondition;
	}

	public String getQuoteDisplayCondition() {
		return quoteDisplayCondition;
	}

	public void setQuoteDisplayCondition(String quoteDisplayCondition) {
		this.quoteDisplayCondition = quoteDisplayCondition;
	}

	public boolean isPolicyReadonly() {
		return policyReadonly;
	}

	public void setPolicyReadonly(boolean policyReadonly) {
		this.policyReadonly = policyReadonly;
	}

	public boolean isPolicyRequired() {
		return policyRequired;
	}

	public void setPolicyRequired(boolean policyRequired) {
		this.policyRequired = policyRequired;
	}

	public boolean isPolicyDisplay() {
		return policyDisplay;
	}

	public void setPolicyDisplay(boolean policyDisplay) {
		this.policyDisplay = policyDisplay;
	}

	public String getPolicyReadonlyCondition() {
		return policyReadonlyCondition;
	}

	public void setPolicyReadonlyCondition(String policyReadonlyCondition) {
		this.policyReadonlyCondition = policyReadonlyCondition;
	}

	public String getPolicyRequiredCondition() {
		return policyRequiredCondition;
	}

	public void setPolicyRequiredCondition(String policyRequiredCondition) {
		this.policyRequiredCondition = policyRequiredCondition;
	}

	public String getPolicyDisplayCondition() {
		return policyDisplayCondition;
	}

	public void setPolicyDisplayCondition(String policyDisplayCondition) {
		this.policyDisplayCondition = policyDisplayCondition;
	}

	public boolean isEndoReadonly() {
		return endoReadonly;
	}

	public void setEndoReadonly(boolean endoReadonly) {
		this.endoReadonly = endoReadonly;
	}

	public boolean isEndoRequired() {
		return endoRequired;
	}

	public void setEndoRequired(boolean endoRequired) {
		this.endoRequired = endoRequired;
	}

	public boolean isEndoDisplay() {
		return endoDisplay;
	}

	public void setEndoDisplay(boolean endoDisplay) {
		this.endoDisplay = endoDisplay;
	}

	public String getEndoReadonlyCondition() {
		return endoReadonlyCondition;
	}

	public void setEndoReadonlyCondition(String endoReadonlyCondition) {
		this.endoReadonlyCondition = endoReadonlyCondition;
	}

	public String getEndoRequiredCondition() {
		return endoRequiredCondition;
	}

	public void setEndoRequiredCondition(String endoRequiredCondition) {
		this.endoRequiredCondition = endoRequiredCondition;
	}

	public String getEndoDisplayCondition() {
		return endoDisplayCondition;
	}

	public void setEndoDisplayCondition(String endoDisplayCondition) {
		this.endoDisplayCondition = endoDisplayCondition;
	}

	public boolean isRenewReadonly() {
		return renewReadonly;
	}

	public void setRenewReadonly(boolean renewReadonly) {
		this.renewReadonly = renewReadonly;
	}

	public boolean isRenewRequired() {
		return renewRequired;
	}

	public void setRenewRequired(boolean renewRequired) {
		this.renewRequired = renewRequired;
	}

	public boolean isRenewDisplay() {
		return renewDisplay;
	}

	public void setRenewDisplay(boolean renewDisplay) {
		this.renewDisplay = renewDisplay;
	}

	public String getRenewReadonlyCondition() {
		return renewReadonlyCondition;
	}

	public void setRenewReadonlyCondition(String renewReadonlyCondition) {
		this.renewReadonlyCondition = renewReadonlyCondition;
	}

	public String getRenewRequiredCondition() {
		return renewRequiredCondition;
	}

	public void setRenewRequiredCondition(String renewRequiredCondition) {
		this.renewRequiredCondition = renewRequiredCondition;
	}

	public String getRenewDisplayCondition() {
		return renewDisplayCondition;
	}

	public void setRenewDisplayCondition(String renewDisplayCondition) {
		this.renewDisplayCondition = renewDisplayCondition;
	}

	public int getTextAreaRows() {
		return textAreaRows;
	}

	public void setTextAreaRows(int textAreaRows) {
		this.textAreaRows = textAreaRows;
	}

	public int getTextAreaCols() {
		return textAreaCols;
	}

	public void setTextAreaCols(int textAreaCols) {
		this.textAreaCols = textAreaCols;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	public String getTableTitleAlign() {
		return tableTitleAlign;
	}

	public void setTableTitleAlign(String tableTitleAlign) {
		this.tableTitleAlign = tableTitleAlign;
	}

	public String getTableColumnAlign() {
		return tableColumnAlign;
	}

	public void setTableColumnAlign(String tableColumnAlign) {
		this.tableColumnAlign = tableColumnAlign;
	}

	public boolean isAjaxEnabled() {
		return ajaxEnabled;
	}

	public void setAjaxEnabled(boolean ajaxEnabled) {
		this.ajaxEnabled = ajaxEnabled;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionListener() {
		return actionListener;
	}

	public void setActionListener(String actionListener) {
		this.actionListener = actionListener;
	}

	public String getOnChangeListener() {
		return onChangeListener;
	}

	public void setOnChangeListener(String onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

	public String getUpdateFieldIds() {
		return updateFieldIds;
	}

	public void setUpdateFieldIds(String updateFieldIds) {
		this.updateFieldIds = updateFieldIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
