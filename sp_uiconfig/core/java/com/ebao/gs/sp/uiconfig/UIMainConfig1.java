package com.ebao.gs.sp.uiconfig;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.tests.themes.valo.Trees;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UIMainConfig1 extends CustomComponent implements View{
	
	public final static String CONFIG_PAGE="configPage";
//	private ComponentContainer parent;
	private ConfigField viewTitle;
	private TextArea viewDesc;
	private NativeSelect viewTemplate; 
	private Button saveConfigBtn;
	private Button deleteConfigBtn;
	private Button previewUIConfigBtn;
//	private Button addSectionBtn;
	private HorizontalLayout operBtnGroup;
	private VerticalLayout sectionPanel;
    private ConfigPageInfo pageInfo;
    private ConfigPageService pageService;
    
    
    
	private VerticalLayout mainLayout;
    private TextArea textArea_4;
    private Panel panel_5;
	private VerticalLayout verticalLayout_9;
	private GridLayout gridLayout_3;
	private Label label_19;
	private TextField textField_4;
	private Label label_20;
	private Label label_21;
	private NativeSelect nativeSelect_7;
	private NativeSelect nativeSelect_10;
	private NativeSelect nativeSelect_11;
	private NativeSelect nativeSelect_12;
	private Label label_22;
	private Label label_23;
	private Label label_24;
	
	
	
	private VerticalLayout sectionLayout;
	private Panel panel_1;
	private VerticalLayout verticalLayout_2;
	private Panel panel_3;
	private GridLayout gridLayout_1;
	private Label label_1;
	private TextField textField_1;
	private Label label_2;
	private TextField textField_2;
	private Label label_3;
	private NativeSelect nativeSelect_1;
	private Label label_4;
	private NativeSelect nativeSelect_2;
	private Label label_5;
	private TextArea textArea_1;
	private Button button_2;
	private Button button_1;
	private VerticalLayout verticalLayout_4;
	
	
	private Panel panel_7;
	private VerticalLayout verticalLayout_11;
	private GridLayout gridLayout_5;
	private Button backBtn;
	private Button previewUIBtn;
	private Button savePageBtn;
	private Button addSectionBtn;
	
	
	
	private BeanFieldGroup pageBinder=new BeanFieldGroup<ConfigPageInfo>(ConfigPageInfo.class);
	private List<BeanFieldGroup> sectionBinderList=new LinkedList<BeanFieldGroup>();
	
//	private BeanFieldGroup sectionBinder;
	
	public UIMainConfig1() {
	//	parent=component;
	//	pageService=new ConfigPageServiceImpl();
		pageInfo=new ConfigPageInfo();
		init();
		anotherInfo();
		beanBinding();
	}
	public void beanBinding(){
		pageBeanBinding();
	}
	
	public void sectionBinding(TextField title,TextField id, NativeSelect type, NativeSelect template,
			TextArea desc,BeanFieldGroup<ConfigSectionInfo>	sectionBinder,ConfigSectionInfo sectionInfo){

		sectionBinder.bind(title, "sectionTitle");
		sectionBinder.bind(id, "sectionId");
		sectionBinder.bind(type, "sectionType");
		sectionBinder.bind(template, "sectionTemplate");
		sectionBinder.bind(desc, "description");
		sectionBinderList.add(sectionBinder);
		pageInfo.getSectionList().add(sectionInfo);
	}
	
	public void pageBeanBinding(){
		
		pageBinder.setItemDataSource(pageInfo);
		pageBinder.bind(textField_4, "pageTitle");
		pageBinder.bind(nativeSelect_7, "pageType");
		pageBinder.bind(nativeSelect_10, "productName");
		pageBinder.bind(nativeSelect_11, "insuredType");
		pageBinder.bind(nativeSelect_12, "pageTemplate");
		pageBinder.bind(textArea_4, "pageDescription");
		pageBinder.bind(textField_4, "pageTitle");
	}
	
	
	
	public void init(){
		// common part: create layout
				mainLayout = new VerticalLayout();
				mainLayout.setImmediate(false);
				mainLayout.setWidth("100%");
				mainLayout.setMargin(false);
				
				// top-level component properties
				setWidth("100.0%");
				setHeight("-1px");
				
				// panel_5
				panel_5 = buildPanel_5();
				mainLayout.addComponent(panel_5);
				mainLayout.setComponentAlignment(panel_5, new Alignment(20));
		
				sectionLayout=new VerticalLayout();
				panel_1 = buildPanel_1();
				sectionLayout.addComponent(panel_1);
				sectionLayout.setComponentAlignment(panel_1, new Alignment(20));
				mainLayout.addComponent(sectionLayout);
				mainLayout.setComponentAlignment(sectionLayout, new Alignment(20));
				
				panel_7 = buildPanel_7();
				mainLayout.addComponent(panel_7);
				mainLayout.setComponentAlignment(panel_7, new Alignment(20));
				
				
				
				setCompositionRoot(mainLayout);
		//		createViewPanel();
//		if(sectionPanel==null)sectionPanel=new VerticalLayout();
//		sectionPanel.addComponent(createSectionPanel());
//		addComponent(sectionPanel);
//		createSectionAddPanel();
//		createOperationBtn();
	
	}

	
	public void anotherInfo(){
		
		addSectionBtn.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				panel_1 = buildPanel_1();
				sectionLayout.addComponent(panel_1);
				sectionLayout.setComponentAlignment(panel_1, new Alignment(20));
				
				
			}});
		
		savePageBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try{
					pageBinder.commit();
					for(BeanFieldGroup<ConfigSectionInfo> bf:sectionBinderList){
						bf.commit();
					}
	//				pageInfo.getInsuredType();
				}catch (CommitException e) {
					// TODO Auto-generated catch block
				
				}

				
			}
		});
		
	}
	
	
	
	private Panel buildPanel_7() {
		// common part: create layout
		panel_7 = new Panel();
		panel_7.setImmediate(false);
		panel_7.setWidth("90.0%");
		panel_7.setHeight("72px");
		
		// verticalLayout_11
		verticalLayout_11 = buildVerticalLayout_11();
		panel_7.setContent(verticalLayout_11);
		
		return panel_7;
	}
	
	private VerticalLayout buildVerticalLayout_11() {
		// common part: create layout
		verticalLayout_11 = new VerticalLayout();
		verticalLayout_11.setImmediate(false);
		verticalLayout_11.setWidth("100.0%");
		verticalLayout_11.setHeight("100.0%");
		verticalLayout_11.setMargin(false);
		
		// gridLayout_5
		gridLayout_5 = buildGridLayout_5();
		verticalLayout_11.addComponent(gridLayout_5);
		
		return verticalLayout_11;
	}
	private GridLayout buildGridLayout_5() {
		// common part: create layout
		gridLayout_5 = new GridLayout();
		gridLayout_5.setImmediate(false);
		gridLayout_5.setWidth("100.0%");
		gridLayout_5.setHeight("52px");
		gridLayout_5.setMargin(false);
		gridLayout_5.setColumns(6);
		
		// addSectionBtn
		addSectionBtn = ComponentFactory.createDefaultButton("Add Section");
	//	addSectionBtn.setCaption("Add Section");
		addSectionBtn.setImmediate(true);
		addSectionBtn.setWidth("-1px");
		addSectionBtn.setHeight("-1px");
		gridLayout_5.addComponent(addSectionBtn, 1, 0);
		gridLayout_5.setComponentAlignment(addSectionBtn, new Alignment(48));
		
		// savePageBtn
		savePageBtn = ComponentFactory.createDefaultButton("Save Page");
//		savePageBtn.setCaption("Save Page");
		savePageBtn.setImmediate(true);
		savePageBtn.setWidth("-1px");
		savePageBtn.setHeight("-1px");
		gridLayout_5.addComponent(savePageBtn, 2, 0);
		gridLayout_5.setComponentAlignment(savePageBtn, new Alignment(48));
		
		// previewUIBtn
		previewUIBtn = ComponentFactory.createDefaultButton("Preview UI");
	//	previewUIBtn.setCaption("Preview UI");
		previewUIBtn.setImmediate(true);
		previewUIBtn.setWidth("-1px");
		previewUIBtn.setHeight("-1px");
		gridLayout_5.addComponent(previewUIBtn, 3, 0);
		gridLayout_5.setComponentAlignment(previewUIBtn, new Alignment(48));
		
		// backBtn
		backBtn = ComponentFactory.createDefaultButton("Back");
		backBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("");
				
			}
		});
		
//		backBtn.setCaption("Back");
		backBtn.setImmediate(true);
		backBtn.setWidth("-1px");
		backBtn.setHeight("-1px");
		gridLayout_5.addComponent(backBtn, 4, 0);
		gridLayout_5.setComponentAlignment(backBtn, new Alignment(48));
		
		return gridLayout_5;
	}
	
	private Panel buildPanel_1() {
		// common part: create layout
		
		final Panel panel_1 = new Panel("Section Configuration");
		panel_1.setImmediate(false);
		panel_1.setWidth("90%");
		panel_1.setHeight("-1px");
		
		// verticalLayout_2
		verticalLayout_2 = buildVerticalLayout_2(panel_1);
		panel_1.setContent(verticalLayout_2);

		return panel_1;
	}
	private VerticalLayout buildVerticalLayout_2(Panel panel_1) {
		// common part: create layout
		verticalLayout_2 = new VerticalLayout();
		verticalLayout_2.setImmediate(false);
		verticalLayout_2.setWidth("100%");
		verticalLayout_2.setHeight("-1px");
		verticalLayout_2.setMargin(false);
		
//		panel_3 = buildPanel_3();
		// gridLayout_1
		gridLayout_1 = buildGridLayout_1(panel_1);
		verticalLayout_2.addComponent(gridLayout_1);
		
		// panel_3
		
		verticalLayout_2.addComponent(panel_3);
		
		return verticalLayout_2;
	}
	
	private GridLayout buildGridLayout_1(final Panel panel_1) {
		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("100.0%");
		gridLayout_1.setHeight("200px");
		gridLayout_1.setMargin(false);
		gridLayout_1.setColumns(4);
		gridLayout_1.setRows(4);
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Section Title:");
		gridLayout_1.addComponent(label_1, 0, 0);
		gridLayout_1.setComponentAlignment(label_1, new Alignment(48));
		// textField_1
		TextField	textField_1 = new TextField();
		textField_1.setImmediate(false);
		textField_1.setWidth("157px");
		textField_1.setHeight("-1px");
		gridLayout_1.addComponent(textField_1, 1, 0);
		gridLayout_1.setComponentAlignment(textField_1, new Alignment(33));
		// label_2
		label_2 = new Label();
		label_2.setImmediate(false);
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("Section Id:");
		gridLayout_1.addComponent(label_2, 2, 0);
		gridLayout_1.setComponentAlignment(label_2, new Alignment(48));
		// textField_2
		TextField	textField_2 = new TextField();
		textField_2.setImmediate(false);
		textField_2.setWidth("145px");
		textField_2.setHeight("-1px");
		gridLayout_1.addComponent(textField_2, 3, 0);
		gridLayout_1.setComponentAlignment(textField_2, new Alignment(33));
		
		// label_3
		label_3 = new Label();
		label_3.setImmediate(false);
		label_3.setWidth("-1px");
		label_3.setHeight("-1px");
		label_3.setValue("Section Type:");
		gridLayout_1.addComponent(label_3, 0, 1);
		gridLayout_1.setComponentAlignment(label_3, new Alignment(48));
		// nativeSelect_1
		NativeSelect	nativeSelect_1 = new NativeSelect();
		nativeSelect_1.setImmediate(false);
		nativeSelect_1.setWidth("157px");
		nativeSelect_1.setHeight("-1px");
		gridLayout_1.addComponent(nativeSelect_1, 1, 1);
		gridLayout_1.setComponentAlignment(nativeSelect_1, new Alignment(33));
		
		// label_4
		label_4 = new Label();
		label_4.setImmediate(false);
		label_4.setWidth("-1px");
		label_4.setHeight("-1px");
		label_4.setValue("Section Template:");
		gridLayout_1.addComponent(label_4, 2, 1);
		gridLayout_1.setComponentAlignment(label_4, new Alignment(48));
		// nativeSelect_2
		NativeSelect nativeSelect_2 = new NativeSelect();
		nativeSelect_2.setImmediate(false);
		nativeSelect_2.setWidth("145px");
		nativeSelect_2.setHeight("-1px");
		gridLayout_1.addComponent(nativeSelect_2, 3, 1);
		gridLayout_1.setComponentAlignment(nativeSelect_2, new Alignment(33));
		
		// label_5
		label_5 = new Label();
		label_5.setImmediate(false);
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue("Section Description:");
		gridLayout_1.addComponent(label_5, 0, 2);
		gridLayout_1.setComponentAlignment(label_5, new Alignment(48));
		// textArea_1
		TextArea textArea_1 = new TextArea();
		textArea_1.setImmediate(false);
		textArea_1.setWidth("180px");
		textArea_1.setHeight("50px");
		gridLayout_1.addComponent(textArea_1, 1, 2);
		gridLayout_1.setComponentAlignment(textArea_1, new Alignment(33));
		
		final ConfigSectionInfo sectionInfo=new ConfigSectionInfo();
		final BeanFieldGroup<ConfigSectionInfo>	sectionBinder=new BeanFieldGroup<ConfigSectionInfo>(ConfigSectionInfo.class);
		sectionBinder.setItemDataSource(sectionInfo);
		
		sectionBinding(textField_1,textField_2,nativeSelect_1,
				nativeSelect_2,textArea_1,sectionBinder,
				sectionInfo);
		
		// button_1
		button_1 = ComponentFactory.createDefaultButton("Add Field");
		button_1.setImmediate(true);
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		
		

		gridLayout_1.addComponent(button_1, 0, 3);
		gridLayout_1.setComponentAlignment(button_1, new Alignment(48));
		
		// button_2
		button_2 = ComponentFactory.createDefaultButton("Delete Section");
	//	button_2.setCaption("Delete Section");
//		button_2.addStyleName("default-button");
		button_2.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				sectionLayout.removeComponent(panel_1);
				pageInfo.getSectionList().remove(sectionInfo);
				sectionBinderList.remove(sectionBinder);
			}});
		button_2.setImmediate(true);
		button_2.setWidth("-1px");
		button_2.setHeight("-1px");
		gridLayout_1.addComponent(button_2, 1, 3);
		gridLayout_1.setComponentAlignment(button_2, new Alignment(5));
		
		panel_3 = new Panel();
		panel_3.setCaption("Field List");
		panel_3.setStyleName("field-config");
		panel_3.setImmediate(false);
		panel_3.setWidth("100.0%");
		panel_3.setHeight("-1px");
		
		// verticalLayout_4
		final  VerticalLayout verticalLayout_4 = new VerticalLayout();
		verticalLayout_4.setImmediate(false);
		verticalLayout_4.setWidth("100.0%");
		verticalLayout_4.setHeight("100.0%");
		verticalLayout_4.setMargin(false);
		verticalLayout_4.setSpacing(true);
		
		panel_3.setContent(verticalLayout_4);
		button_1.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					pageBinder.commit();
					
					
//					String va=pageInfo.getPageTitle();
					
				} catch (CommitException e) {
					// TODO Auto-generated catch block
				
				}
				
				
				
				final Window fieldWindow = new Window(null);
				fieldWindow.setWidth("950px");
				fieldWindow.setHeight("700px");
				HorizontalLayout subContent=new HorizontalLayout();
				subContent.setMargin(true);
				fieldWindow.setContent(subContent);

//				subContent.addComponent(new Trees());
//				subContent.addComponent(new Forms());
				
				fieldWindow.center();
				UI.getCurrent().addWindow(fieldWindow);

				
			}
		});
		
		return gridLayout_1;
	}
	
	private Component buildFieldLayout(final  VerticalLayout verticalLayout_4){
		final CssLayout csslayout=new CssLayout();
		csslayout.setWidth("100%");
		Label display=new Label();
		display.setWidth("500px");
		
		
		Button editFieldBtn=ComponentFactory.createDefaultButton("Edit");//new Button("Edit");
	//	editFieldBtn.setIcon(new ThemeResource("icons/editField.gif"));
		editFieldBtn.setWidth("100px");
		Button delFieldBtn=ComponentFactory.createDefaultButton("Delete");//new Button("Edit");
	//	delFieldBtn.setIcon(new ThemeResource("icons/delete.gif"));
		delFieldBtn.setWidth("100px");
		Label space=new Label();
		space.setWidth("50px");
		delFieldBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				verticalLayout_4.removeComponent(csslayout);
				
				
			}
		});
		csslayout.addComponent(display);
		csslayout.addComponent(editFieldBtn);
		csslayout.addComponent(space);
		csslayout.addComponent(delFieldBtn);
		return csslayout;
	}
	
	
	private Panel buildPanel_3() {
		// common part: create layout
		panel_3 = new Panel();
		panel_3.setCaption("Field List");
		panel_3.setImmediate(false);
		panel_3.setWidth("100.0%");
		panel_3.setHeight("-1px");
		
		// verticalLayout_4
		verticalLayout_4 = new VerticalLayout();
		verticalLayout_4.setImmediate(false);
		verticalLayout_4.setWidth("100.0%");
		verticalLayout_4.setHeight("100.0%");
		verticalLayout_4.setMargin(false);
		verticalLayout_4.setSpacing(true);
		panel_3.setContent(verticalLayout_4);
		
		return panel_3;
	}
	
	
	
	private Panel buildPanel_5() {
		// common part: create layout
		panel_5 = new Panel();
		panel_5.setCaption("Page Configuration");
		panel_5.setStyleName("page-config");
		panel_5.setImmediate(false);
		panel_5.setWidth("90.0%");
		panel_5.setHeight("176px");
		
		// verticalLayout_9
		verticalLayout_9 = buildVerticalLayout_9();
		panel_5.setContent(verticalLayout_9);
		
		return panel_5;
	}
	
	private VerticalLayout buildVerticalLayout_9() {
		// common part: create layout
		verticalLayout_9 = new VerticalLayout();
		verticalLayout_9.setImmediate(false);
		verticalLayout_9.setWidth("100.0%");
		verticalLayout_9.setHeight("100.0%");
		verticalLayout_9.setMargin(false);
		
		// gridLayout_3
		gridLayout_3 = buildGridLayout_3();
		verticalLayout_9.addComponent(gridLayout_3);
		verticalLayout_9.setComponentAlignment(gridLayout_3, new Alignment(20));
		
		return verticalLayout_9;
	}
	
	private GridLayout buildGridLayout_3() {
		// common part: create layout
		gridLayout_3 = new GridLayout();
		gridLayout_3.setImmediate(false);
		gridLayout_3.setWidth("100.0%");
		gridLayout_3.setHeight("137px");
		gridLayout_3.setMargin(false);
		gridLayout_3.setColumns(4);
		gridLayout_3.setRows(3);
		
		// label_19
		label_19 = new Label();
		label_19.setImmediate(false);
		label_19.setWidth("108px");
		label_19.setHeight("-1px");
		label_19.setValue("Page Title:");
		gridLayout_3.addComponent(label_19, 0, 0);
		gridLayout_3.setComponentAlignment(label_19, new Alignment(48));
		
		// textField_4
		textField_4 = new TextField();
		textField_4.setImmediate(false);
		textField_4.setWidth("158px");
		textField_4.setHeight("-1px");
		gridLayout_3.addComponent(textField_4, 1, 0);
		gridLayout_3.setComponentAlignment(textField_4, new Alignment(33));
		// label_20
		label_20 = new Label();
		label_20.setImmediate(false);
		label_20.setWidth("115px");
		label_20.setHeight("-1px");
		label_20.setValue("Page Type:");
		gridLayout_3.addComponent(label_20, 2, 0);
		gridLayout_3.setComponentAlignment(label_20, new Alignment(48));
		
		// nativeSelect_7
		nativeSelect_7 = new NativeSelect();
		nativeSelect_7.setImmediate(false);
		nativeSelect_7.setWidth("166px");
		nativeSelect_7.setHeight("-1px");
		gridLayout_3.addComponent(nativeSelect_7, 3, 0);
		gridLayout_3.setComponentAlignment(nativeSelect_7, new Alignment(33));
		
		// label_21
		label_21 = new Label();
		label_21.setImmediate(false);
		label_21.setWidth("122px");
		label_21.setHeight("-1px");
		label_21.setValue("Product Name:");
		gridLayout_3.addComponent(label_21, 0, 1);
		gridLayout_3.setComponentAlignment(label_21, new Alignment(48));
		
		// nativeSelect_10
		nativeSelect_10 = new NativeSelect();
		nativeSelect_10.setImmediate(false);
		nativeSelect_10.setWidth("158px");
		nativeSelect_10.setHeight("-1px");
		gridLayout_3.addComponent(nativeSelect_10, 1, 1);
		gridLayout_3.setComponentAlignment(nativeSelect_10, new Alignment(33));
		
		// label_22
		label_22 = new Label();
		label_22.setImmediate(false);
		label_22.setWidth("116px");
		label_22.setHeight("-1px");
		label_22.setValue("Insured Type:");
		gridLayout_3.addComponent(label_22, 2, 1);
		gridLayout_3.setComponentAlignment(label_22, new Alignment(48));
		
		// nativeSelect_11
		nativeSelect_11 = new NativeSelect();
		nativeSelect_11.setImmediate(false);
		nativeSelect_11.setWidth("166px");
		nativeSelect_11.setHeight("-1px");
		gridLayout_3.addComponent(nativeSelect_11, 3, 1);
		gridLayout_3.setComponentAlignment(nativeSelect_11, new Alignment(33));
		
		// label_23
		label_23 = new Label();
		label_23.setImmediate(false);
		label_23.setWidth("128px");
		label_23.setHeight("-1px");
		label_23.setValue("Page Template:");
		gridLayout_3.addComponent(label_23, 0, 2);
		gridLayout_3.setComponentAlignment(label_23, new Alignment(48));
		
		// nativeSelect_12
		nativeSelect_12 = new NativeSelect();
		nativeSelect_12.setImmediate(false);
		nativeSelect_12.setWidth("158px");
		nativeSelect_12.setHeight("-1px");
		gridLayout_3.addComponent(nativeSelect_12, 1, 2);
		gridLayout_3.setComponentAlignment(nativeSelect_12, new Alignment(33));
		
		// label_24
		label_24 = new Label();
		label_24.setImmediate(false);
		label_24.setWidth("135px");
		label_24.setHeight("-1px");
		label_24.setValue("Page Description:");
		gridLayout_3.addComponent(label_24, 2, 2);
		gridLayout_3.setComponentAlignment(label_24, new Alignment(48));
		
		// textArea_4
		textArea_4 = new TextArea();
		textArea_4.setImmediate(false);
		textArea_4.setWidth("166px");
		textArea_4.setHeight("35px");
		gridLayout_3.addComponent(textArea_4, 3, 2);
		gridLayout_3.setComponentAlignment(textArea_4, new Alignment(33));
		
		return gridLayout_3;
	}
	
	public void createViewPanel(){
		Panel panelView = new Panel("Page Configuration");
		 
		viewTitle=new ConfigField("Page Title");
		viewTemplate=new NativeSelect("Page Template");
		viewTemplate.addItems("","Default Page");
		viewTemplate.setWidth("200px");
		viewDesc=new TextArea("Page Description");
		viewDesc.setWordwrap(true);
		viewDesc.setHeight("50px");
		Button saveViewBtn=new Button("Save View");
		VerticalLayout layout = new VerticalLayout();
		
		layout.addComponent(viewTitle);
		layout.addComponent(viewTemplate);
		layout.addComponent(viewDesc);
		layout.addComponent(saveViewBtn);
		
		panelView.setContent(layout);
	//	addComponent(panelView);
	}
	public Component createSectionPanel(){

	
		final GridLayout gridLayout=new GridLayout(2,4);
		TextField sectionTitle=new TextField("Section Title:");
		TextField sectionCode=new TextField("Section Code:");
		sectionCode.setValue("section code");
		
		NativeSelect selectionType=new NativeSelect("Section Type");
		
		selectionType.addItems("Default Type", "Hide Section Title", "Readonly Section");
		
		NativeSelect selectionTemplate=new NativeSelect("Section Template");
		selectionTemplate.addItems("Default Section","Readonly Section","Repeateable Section","Table Section");
		
		TextArea sectionDescrition=new TextArea("Section Description");
		
		HorizontalLayout btnGroup=new HorizontalLayout();
		Button addFieldBtn=new NativeButton("Add Field");
		
		addFieldBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final Window fieldWindow = new Window(null);
				fieldWindow.setWidth("600px");
				fieldWindow.setHeight("500px");
				VerticalLayout subContent = new VerticalLayout();
				subContent.setMargin(true);
				fieldWindow.setContent(subContent);

				// Put some components in it
				subContent.addComponent(new Label("Meatball sub"));
				subContent.addComponent(new Button("Awlright"));

				// Center it in the browser window
				fieldWindow.center();

				// Open it in the UI
				UI.getCurrent().addWindow(fieldWindow);

			}
		});
		
		
		Button saveSectionBtn=new NativeButton("Save Section");
		Button deleteSectionBtn=new NativeButton("Delete Section");
		
		deleteSectionBtn.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				sectionPanel.removeComponent(gridLayout);

			}});
		
		
		btnGroup.addComponent(addFieldBtn);
		btnGroup.addComponent(saveSectionBtn);
		btnGroup.addComponent(deleteSectionBtn);
		
		gridLayout.addComponent(sectionTitle);
		gridLayout.addComponent(sectionCode);
		gridLayout.addComponent(selectionType);
		gridLayout.addComponent(selectionTemplate);
		
		gridLayout.addComponent(sectionDescrition, 0, 2, 1, 2);
		gridLayout.addComponent(btnGroup,0,3,1,3);
		return gridLayout;
		

	}
	
	public void createSectionAddPanel(){
		addSectionBtn=new NativeButton("Add Section");
		addSectionBtn.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				sectionPanel.addComponent(createSectionPanel());
			}});
//		addComponent(addSectionBtn);

	}
	
	public void createOperationBtn(){

		operBtnGroup = new HorizontalLayout();
		saveConfigBtn=new NativeButton("Save Configuration");
		saveConfigBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				saveConfiguration();
				
			}
		});
		
		deleteConfigBtn=new NativeButton("Delete Configuration");
		previewUIConfigBtn=new NativeButton("Preview UI");
		Button backBtn=new NativeButton("Back");
		backBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("");
				
			}
		});
		operBtnGroup.addComponent(saveConfigBtn);
		operBtnGroup.addComponent(deleteConfigBtn);
		operBtnGroup.addComponent(previewUIConfigBtn);
		operBtnGroup.addComponent(backBtn);
		
//		addComponent(operBtnGroup);
	}
	
	
	private void saveConfiguration(){/*
		pageInfo=new ConfigPageInfo();
		pageInfo.setPageDescription(viewDesc.getValue());
		pageInfo.setPageTemplate((String)viewTemplate.getValue());
		pageInfo.setPageTitle(viewTitle.getTextFieldValue());
		List<ConfigSectionInfo> sectionList=new LinkedList<ConfigSectionInfo>();
		if(sectionPanel!=null){
			Iterator<Component> it=  sectionPanel.getComponentIterator();
			while(it.hasNext()){
				ConfigSectionInfo sectionInfo=new ConfigSectionInfo();
				Component com=it.next();
				if(com instanceof GridLayout){
					GridLayout gl=(GridLayout)com;
					TextField title= (TextField)gl.getComponent(0, 0);
					sectionInfo.setSectionTitle(title.getValue());
					TextField sectionCode=(TextField)gl.getComponent(1, 0);
					sectionInfo.setSectionId(sectionCode.getValue());
					NativeSelect secType=(NativeSelect)gl.getComponent(0, 1);
					sectionInfo.setSectionType((String)secType.getValue());
					TextArea sectionDesc=(TextArea)gl.getComponent(0, 2);
					sectionInfo.setDescription(sectionDesc.getValue().trim());
					sectionList.add(sectionInfo);
				}
				
				
			}
			
			
		}
		pageInfo.setSectionList(sectionList);
		try {
			pageService.addConfigPage(pageInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
	*/}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
