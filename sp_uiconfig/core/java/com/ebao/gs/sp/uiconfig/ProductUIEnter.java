package com.ebao.gs.sp.uiconfig;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ProductUIEnter extends CustomComponent implements View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1208426994368261319L;

	public ProductUIEnter(){
		init();
		
	}
	
	private void init(){
		
		final Window fieldWindow = new Window(null);
		fieldWindow.setWidth("650px");
		fieldWindow.setHeight("500px");
		VerticalLayout subContent=new VerticalLayout();
		subContent.setMargin(true);
		fieldWindow.setContent(subContent);
		ProductSelectionUI pui=new ProductSelectionUI(fieldWindow);
		subContent.setSizeFull();
		subContent.addComponent(pui);
		
		fieldWindow.center();
		UI.getCurrent().addWindow(fieldWindow);
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
