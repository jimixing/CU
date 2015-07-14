package com.ebao.gs.sp.uiconfig;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class ConfigField extends HorizontalLayout {
	protected TextField textField ;
	protected Label label;
	
	public ConfigField(String title){
		label=new Label(title+":");
		label.setStyleName("config-field-style");
		addComponent(label);
		textField=new TextField();
		addComponent(textField);
		setWidth("300px");
	}
	
	public String getTextFieldValue(){
		return textField.getValue();
	}

}
