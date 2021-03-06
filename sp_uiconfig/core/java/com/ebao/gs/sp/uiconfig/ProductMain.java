package com.ebao.gs.sp.uiconfig;

import java.util.LinkedList;
import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;

public class ProductMain extends HorizontalSplitPanel implements View{
	public static final String PRODUCT_MAIN="product_main";
	private String productName;
//	private List<ConfigPageInfo> pageInfoList=new LinkedList<ConfigPageInfo>();
	
	public ProductMain(String productName){
		this.productName=productName;
		init();
	}
	
	
	private void init(){
		setSplitPosition(32, Sizeable.UNITS_PERCENTAGE);
		setSizeFull();
//		this.setSizeUndefined();
		addComponent(new ProductUIShow(productName,this));
//		addComponent(new PageAdd(productName));
//		addComponent(new PageDisplay());	
//		addComponent(new SectionAddUI());
	}
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
