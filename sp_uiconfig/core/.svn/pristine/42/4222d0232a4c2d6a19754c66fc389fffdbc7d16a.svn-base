package com.ebao.gs.sp.uiconfig;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UIEnterConfig1 extends CustomComponent implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1024964383497997809L;
	private VerticalLayout mainLayout;
	private Panel panel_1;
	private VerticalLayout verticalLayout_2;
	private GridLayout searchPanel;
	private Label productNameLabel;
	private Label pageTitleLabel;
	private NativeSelect nativeSelect_1;
	private TextField textField_1;
	private Label pageTypeLabel;
	private Button button_1;
	private NativeSelect nativeSelect_2;
	private Panel searchResult;
	private Table searchTabel;
	private GridLayout operationPanel;
	private Button delBtn;
	
	private Button editBtn;
	private Button addBtn;
	public final static String ENTER_PAGE="enterPage";
	public UIEnterConfig1(){
		init();
		
	}
	
	public void init(){
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("80%");
		mainLayout.setHeight("80%");
		mainLayout.setMargin(false);
		createSearchPanel();
		createSearchResult();
		createOperaPanel();

	
		
		setCompositionRoot(mainLayout);
//		createSearchResult();
//		
//		createOperaPanel();
		
	}
	
	public void createSearchPanel(){
		buildPanel_1();
		mainLayout.addComponent(panel_1);
		mainLayout.setComponentAlignment(panel_1, new Alignment(20));
//		VerticalLayout vLayout=new VerticalLayout();
//		
//		HorizontalLayout hLayout=new HorizontalLayout();
//		
//		NativeSelect productName = new NativeSelect("Product Name");
//		productName.addItems("New Private Car", "New Homeowner", "New Happy Life PA");
//		
//		TextField pageTitle=new TextField("Page Title");
//		
//		NativeSelect pageType = new NativeSelect("Page Type");
//		pageType.addItems("Policy Page","Insured Page");
//		hLayout.addComponent(productName);
//		hLayout.addComponent(pageType);
//		hLayout.addComponent(pageTitle);
//		vLayout.addComponent(hLayout);
//		
//		Button searchBtn=new NativeButton("Search");
//		vLayout.addComponent(searchBtn);
//		addComponent(vLayout);
	}
	
	private Panel buildPanel_1() {
		// common part: create layout
		panel_1 = ComponentFactory.createDefaultPanel("Search  Criteria");//new Panel();
//		panel_1.setCaption("Search  Criteria");
		panel_1.setImmediate(false);
		panel_1.setWidth("90.0%");
		panel_1.setHeight("220px");
		
		// verticalLayout_2
		verticalLayout_2 = buildVerticalLayout_2();
		panel_1.setContent(verticalLayout_2);
		
		return panel_1;
	}
	
	private VerticalLayout buildVerticalLayout_2() {
		// common part: create layout
		verticalLayout_2 = new VerticalLayout();
		verticalLayout_2.setImmediate(false);
		verticalLayout_2.setWidth("100.0%");
		verticalLayout_2.setHeight("100.0%");
		verticalLayout_2.setMargin(false);
		
		// searchPanel
		searchPanel = buildSearchPanel();
		verticalLayout_2.addComponent(searchPanel);
		
		return verticalLayout_2;
	}
	private GridLayout buildSearchPanel() {
		// common part: create layout
		searchPanel = new GridLayout();
		searchPanel.setImmediate(false);
		searchPanel.setWidth("90%");
		searchPanel.setHeight("160px");
		searchPanel.setMargin(false);
		searchPanel.setColumns(4);
		searchPanel.setRows(3);
		
		// productNameLabel
		productNameLabel = ComponentFactory.createDefaultLabel("Product Name:");//new Label();
		productNameLabel.setImmediate(false);
		productNameLabel.setWidth("138px");
		productNameLabel.setHeight("-1px");
//		productNameLabel.setValue("Product Name:");
		searchPanel.addComponent(productNameLabel, 0, 0);
		searchPanel.setComponentAlignment(productNameLabel, new Alignment(34));
		
		// nativeSelect_1
		nativeSelect_1 = new NativeSelect();
		nativeSelect_1.addItems("New Private Car", "New Homeowner", "New Happy Life PA");
		nativeSelect_1.setImmediate(false);
		nativeSelect_1.setWidth("100%");
		nativeSelect_1.setHeight("-1px");
		searchPanel.addComponent(nativeSelect_1, 1, 0);
		searchPanel.setComponentAlignment(nativeSelect_1, new Alignment(33));
		
		// pageTitleLabel
		pageTitleLabel = new Label();
		pageTitleLabel.setImmediate(false);
		pageTitleLabel.setWidth("100%");
		pageTitleLabel.setHeight("-1px");
		pageTitleLabel.setValue("Page Title:");
		searchPanel.addComponent(pageTitleLabel, 2, 0);
		searchPanel.setComponentAlignment(pageTitleLabel, new Alignment(34));
		
		// textField_1
		textField_1 = new TextField();
		textField_1.setImmediate(false);
		textField_1.setWidth("100%");
		textField_1.setHeight("-1px");
		searchPanel.addComponent(textField_1, 3, 0);
		searchPanel.setComponentAlignment(textField_1, new Alignment(33));
		
		// pageTypeLabel
		pageTypeLabel = new Label();
		pageTypeLabel.setImmediate(false);
		pageTypeLabel.setWidth("141px");
		pageTypeLabel.setHeight("-1px");
		pageTypeLabel.setValue("Page Type:");
		searchPanel.addComponent(pageTypeLabel, 0, 1);
		searchPanel.setComponentAlignment(pageTypeLabel, new Alignment(34));
		
		// nativeSelect_2
		nativeSelect_2 = new NativeSelect();
		nativeSelect_2.addItems("Policy Page","Insured Page");
		nativeSelect_2.setImmediate(false);
		nativeSelect_2.setWidth("100%");
		nativeSelect_2.setHeight("-1px");
		searchPanel.addComponent(nativeSelect_2, 1, 1);
		searchPanel.setComponentAlignment(nativeSelect_2, new Alignment(33));
		
		// button_1
		button_1 =  ComponentFactory.createDefaultButton("Search");//new Button();
//		button_1.setCaption("Search");
		button_1.setImmediate(true);
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		searchPanel.addComponent(button_1, 3, 1);
		searchPanel.setComponentAlignment(button_1, new Alignment(9));
		
		return searchPanel;
	}
	
	private Panel buildSearchResult() {
		// common part: create layout
		searchResult = ComponentFactory.createDefaultPanel("Search Result");//new Panel("Search Result");
		searchResult.setImmediate(false);
		searchResult.setWidth("90.0%");
		searchResult.setHeight("-1px");
	
		
		// searchTabel
		searchTabel = new Table();
		searchTabel.addContainerProperty("",CheckBox.class,null);
		searchTabel.addContainerProperty("Page ID",String.class,null);
		searchTabel.addContainerProperty("Page Title",String.class,null);
		searchTabel.addContainerProperty("Page Type",String.class,null);
		searchTabel.addContainerProperty("Insured Type",String.class,null);
		searchTabel.setImmediate(false);
		searchTabel.setWidth("100.0%");
		searchTabel.setHeight("226px");
		searchResult.setContent(searchTabel);
		
		return searchResult;
	}
	
	
	
	public void createSearchResult(){
		searchResult = buildSearchResult();
		mainLayout.addComponent(searchResult);
		mainLayout.setComponentAlignment(searchResult, new Alignment(20));
//		Panel panel=new Panel();
//		Table table = new Table();
//		table.addContainerProperty("",CheckBox.class,null);
//		table.addContainerProperty("Page ID",String.class,null);
//		table.addContainerProperty("Page Title",String.class,null);
//		table.addContainerProperty("Page Type",String.class,null);
//		table.addContainerProperty("Insured Type",String.class,null);
//		table.setHeight("500px");
//		panel.setContent(table);
	//	addComponent(panel);
		
	}
	
	public void createOperaPanel(){
		
		operationPanel = buildOperationPanel();
		mainLayout.addComponent(operationPanel);
		mainLayout.setComponentAlignment(operationPanel, new Alignment(20));
//		HorizontalLayout hLayout=new HorizontalLayout();
//		Button addBtn=new NativeButton("Add");
//		addBtn.addClickListener(new Button.ClickListener() {
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				UI.getCurrent().getNavigator().navigateTo(UIMainConfig1.CONFIG_PAGE);
//				
//			}
//		});
//		
//		Button editBtn=new NativeButton("Edit");
//		Button delBtn=new NativeButton("Delete");
//		hLayout.setSpacing(true);
//		hLayout.addComponent(addBtn);
//		hLayout.addComponent(editBtn);
//		hLayout.addComponent(delBtn);
	//	addComponent(hLayout);
		
		
	}
	private GridLayout buildOperationPanel() {
		// common part: create layout
		operationPanel = new GridLayout();
		operationPanel.setImmediate(false);
		operationPanel.setWidth("680px");
		operationPanel.setHeight("-1px");
		operationPanel.setMargin(true);
		operationPanel.setColumns(6);
		
		// addBtn
		addBtn = ComponentFactory.createDefaultButton("Add");//new Button();
	//	addBtn.setCaption("Add");
		addBtn.setImmediate(true);
		addBtn.setWidth("100px");
		addBtn.setHeight("-1px");
		addBtn.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(UIMainConfig1.CONFIG_PAGE);
			}

			
		});
		operationPanel.addComponent(addBtn, 1, 0);
		operationPanel.setComponentAlignment(addBtn, new Alignment(48));
		
		// editBtn
		editBtn = ComponentFactory.createDefaultButton("Edit");//new Button();
//		editBtn.setCaption("Edit");
		editBtn.setImmediate(true);
		editBtn.setWidth("100px");
		editBtn.setHeight("-1px");
		operationPanel.addComponent(editBtn, 2, 0);
		operationPanel.setComponentAlignment(editBtn, new Alignment(48));
		
		// delBtn
		delBtn = ComponentFactory.createDefaultButton("Delete");//new Button();
//		delBtn.setCaption("Delete");
		delBtn.setImmediate(true);
		delBtn.setWidth("100px");
		delBtn.setHeight("-1px");
		operationPanel.addComponent(delBtn, 3, 0);
		operationPanel.setComponentAlignment(delBtn, new Alignment(48));
		
		return operationPanel;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}


}
