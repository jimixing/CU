package com.ebao.gs.sp.uiconfig;

import java.util.List;

import com.ebao.gs.pol.prdt.config.service.ConfigUIUtilService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigUIUtilServiceImpl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class ProductSelectionUI extends CustomComponent implements View {


	private GridLayout mainLayout;

	private Button button_3;
	
	private NativeSelect nativeSelect_3;
	
	private Window fieldWindow;

	
	public ProductSelectionUI(Window fieldWindow){
		this.fieldWindow=fieldWindow;
		init();
	}
	
	private void init(){
		
		buildMainLayout();
		addProductInfo();
		setCompositionRoot(mainLayout);
		
	}
	
	
	private void addProductInfo(){
		ConfigUIUtilService service=new ConfigUIUtilServiceImpl();
		try {
			List<String> allProduct=service.getAllProductInfo();
			nativeSelect_3.addItems(allProduct);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
	
		}
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	private GridLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new GridLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("500px");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		mainLayout.setRows(2);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// nativeSelect_3
		nativeSelect_3 = new NativeSelect("Product List");
		nativeSelect_3.setStyleName("h3");
		nativeSelect_3.setImmediate(false);
		nativeSelect_3.setWidth("227px");
		nativeSelect_3.setHeight("40px");
		mainLayout.addComponent(nativeSelect_3, 0, 0);
		mainLayout.setComponentAlignment(nativeSelect_3, new Alignment(48));
		
		// button_3
		button_3 = new Button();
		button_3.setCaption("OK");
		button_3.setImmediate(false);
		button_3.setWidth("106px");
		button_3.setHeight("-1px");
		button_3.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String productName=(String)nativeSelect_3.getValue();
				ProductMain productMain=new ProductMain(productName);
				
				UI.getCurrent().getNavigator().addView(PageDisplay.PAGE_DISPLAY, productMain);
				UI.getCurrent().getNavigator().navigateTo(PageDisplay.PAGE_DISPLAY);
				fieldWindow.close();
			}
		});
		mainLayout.addComponent(button_3, 0, 1);
		mainLayout.setComponentAlignment(button_3, new Alignment(20));
		
		return mainLayout;
	}
	
	
	

}
