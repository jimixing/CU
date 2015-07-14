package com.ebao.gs.sp.uiconfig;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigUIUtilService;
import com.ebao.gs.pol.prdt.config.service.PageNameService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigUIUtilServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.PageNameServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class PageDisplay extends VerticalLayout implements View {

	public final static String PAGE_DISPLAY="pageDisplay";
	public final static int MAX_NAME_LENGTH=30;
	private VerticalLayout mainLayout;
	private  Panel panel_5;
	private VerticalLayout verticalLayout_9;
	private GridLayout gridLayout_3;
	private Label label_19;
	private ComboBox textField_4;
	private Label label_20;
	private Label label_21;
	private ComboBox nativeSelect_7;
	private NativeSelect nativeSelect_10;
	private NativeSelect nativeSelect_11;
	private ComboBox nativeSelect_12;
	private Label label_22;
	private Label label_23;
	private Label label_24;
	private TextArea textArea_4;
	private Button addSectionBtn;
	private Button delPageBtn;
	private Button savePageBtn;
	
	private ProductMain pm; 
	private Tree tree;
	private String productName;
	private String parent;
	private ConfigPageInfo pageInfo;
	private List<ConfigPageInfo> pageList;
	private BeanFieldGroup pageBinder=new BeanFieldGroup<ConfigPageInfo>(ConfigPageInfo.class);
	private ConfigUIUtilService configService=new ConfigUIUtilServiceImpl();
	private ConfigPageService configPageService=new ConfigPageServiceImpl();
	private PageNameService pageNameService=new PageNameServiceImpl();
	
	public PageDisplay(String productName,ProductMain pm,Tree tree,String pageItemId,
			ConfigPageInfo pageInfo,List<ConfigPageInfo> pageList){
		this.productName=productName;
		this.pm=pm;
		this.tree=tree;
		this.parent=pageItemId;
		this.pageInfo=pageInfo;
		this.pageList=pageList;
		init1();
		pageBinding();
	}

		private void init1(){

		     Label title = new Label("Page Configuration");
		     addComponent(title);
		
		     final FormLayout form = new FormLayout();
		     form.setWidth("600px");
		     form.setSizeUndefined();

		     addComponent(form);
		     
		     List<String> pageNameDesc=null;
		     String name="";
		     try {
				pageNameDesc= pageNameService.findAllPageNameDesc();
				if(pageInfo.getPageName()!=null&&!pageInfo.getPageName().isEmpty()){
					name=pageNameService.findPageNameDescByCode(pageInfo.getPageName());
				}
			} catch (Exception e1) {
				
			}
		     
		     textField_4 = new ComboBox("Page Name:");
		     textField_4.setRequired(true);
		     textField_4.setHeight("40px");
		     textField_4.setWidth("300px");
		     if(pageNameDesc!=null&&pageNameDesc.size()>0){
		    	 textField_4.addItems(pageNameDesc);
		     }
		     textField_4.setValue(name);
		     textField_4.addBlurListener(new BlurListener(){

				@Override
				public void blur(BlurEvent event) {		 
					String oldName=pageInfo.getPageName(); 
					try {
						String description=pageNameService.findPageNameDescByCode(oldName);
						String newName=pageNameService.findPageNameCodeByDesc(((String)textField_4.getValue()).trim());
						if(description.equals(((String)textField_4.getValue()).trim()))return;
						for(ConfigPageInfo pageInfo:pageList){
							if(pageInfo.getPageName().equals(newName)){
								Notification.show("page name error","duplicate name for page", Notification.Type.ERROR_MESSAGE);
								textField_4.focus();
								return;
							}
						}
					} catch (Exception e) {
						
					}
//					if(((String)textField_4.getValue()).length()>MAX_NAME_LENGTH){
//						Notification.show("Page Title Problem:","Title of page is too long", Notification.Type.WARNING_MESSAGE);
//						textField_4.focus();   
//						return;
//					}
					
				
					

				}});
		     form.addComponent(textField_4);
		     

			nativeSelect_7 = new ComboBox("Page Type:");
			nativeSelect_7.setHeight("40px");;
			nativeSelect_7.setWidth("300px");
			nativeSelect_7.setImmediate(false);
			nativeSelect_7.addItems("policy","insured");
//			 form.addComponent(nativeSelect_7);
			
			 

				
			// nativeSelect_12
			nativeSelect_12 = new ComboBox("Page Template:");
			nativeSelect_12.setHeight("40px");
			nativeSelect_12.setWidth("300px");
			nativeSelect_12.addItems("default template");

//			form.addComponent(nativeSelect_12);
			
			
			// textArea_4
			textArea_4 = new TextArea("Page Description:");
			textArea_4.setImmediate(false);

			textArea_4.setWidth("300px");
			textArea_4.setHeight("100px");
			form.addComponent(textArea_4);
			
			HorizontalLayout horizontal=new HorizontalLayout();
			horizontal.setSpacing(true);
			horizontal.setWidth("500px");
			addSectionBtn = new Button("Add Section");
			addSectionBtn.setImmediate(true);
			addSectionBtn.setWidth("100px");
			addSectionBtn.setStyleName("primary");
			horizontal.addComponent(addSectionBtn);
			horizontal.setExpandRatio(addSectionBtn, 1);

			addSectionBtn.addClickListener(new Button.ClickListener() {
					 
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					@Override
					public void buttonClick(ClickEvent event) {
						
						
						try {
							pageBinder.commit();
							pageInfo.setPageName(pageNameService.findPageNameCodeByDesc(((String)textField_4.getValue()).trim()));
						
						} catch (CommitException e) {
							// TODO Auto-generated catch block
							Notification.show("page save error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
						} catch (Exception e) {
							// TODO Auto-generated catch block	
						}
						HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
						container.getItem(parent).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue((String)textField_4.getValue());
						ConfigSectionInfo sectionInfo=new ConfigSectionInfo();

						String sectionId = "section"+container.size();
						String sectionItemId=pageInfo.getPageName()+"-"+sectionId;
						sectionInfo.setSectionTitle(sectionId);
						Item section = container.addItem(sectionItemId);
						container.setChildrenAllowed(sectionItemId, false);
						section.getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionId);
						section.getItemProperty(PageSectionTreeGenerator.NODE_TYPE).setValue("SECTION");
						pageInfo.getSectionList().add(sectionInfo);
						sectionInfo.setPage(pageInfo);
						SectionAddUI sectionAdd=new SectionAddUI(sectionInfo,pageInfo.getSectionList(),tree,sectionItemId,PageDisplay.this,pm,false);
						 
						if (pm.getComponentCount() <= 1) {
							pm.addComponent(sectionAdd);
						} else {
							Component last = pm.getSecondComponent();//.getComponent(1);
							pm.replaceComponent(last, sectionAdd);
						}  
						
						container.setParent(sectionItemId, parent);
					}
				});	
			
			
			
			delPageBtn = new Button("Delete Page");
			delPageBtn.setImmediate(true);
			delPageBtn.setWidth("100px");
			delPageBtn.setStyleName("primary");
			
			delPageBtn.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 475722506792175132L;

				@Override
				public void buttonClick(ClickEvent event) {
	
					ConfirmDialog dialog=ConfirmDialog.show(getUI(), "Reminder Message",
					        "Are you sure to delete page?", "Yes", "No",
					        new ConfirmDialog.Listener() {

					            public void onClose(ConfirmDialog dialog) {
					                if (dialog.isConfirmed()) {
					                    // Confirmed to continue
										
										HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
										container.removeItemRecursively(parent);
										if(pageList.contains(pageInfo)){
											pageList.remove(pageInfo);
										}									
										
										pm.removeComponent(PageDisplay.this);
										if(pageInfo.getPageId()==null||pageInfo.getPageId().isEmpty()){
											return ;
										}
										try {
											configPageService.delConfigPage(pageInfo);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											Notification.show("page delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
										}
					                } 
					            }
					        });
					  dialog.getCancelButton().setStyleName("primary");
				}
			});
			
			
			
			horizontal.addComponent(delPageBtn);
			horizontal.setExpandRatio(delPageBtn, 1);
			
			savePageBtn =new Button("Save Page");
			savePageBtn.setImmediate(true);
			savePageBtn.setWidth("100px");
			savePageBtn.setStyleName("primary");
			
			savePageBtn.addClickListener(new Button.ClickListener() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						if(textField_4.getValue()==null||((String)textField_4.getValue()).isEmpty()){
							Notification.show("page save error", "Please check the requierment item", Notification.Type.ERROR_MESSAGE);
							return ;
						} 
						String name=pageNameService.findPageNameCodeByDesc((String)textField_4.getValue());
						pageInfo.setPageName(name);
						
						if(pageInfo.getPageId()==null){ 
							pageBinder.commit(); 
							pageInfo.setPageSequence(pageList.size()-1);
							pageInfo.setEdit(true);
							configPageService.addConfigPage(pageInfo,false,null);
						}else{
							String oldPageTitle=pageInfo.getPageName();
							pageBinder.commit();
							pageInfo.setPageName(name);
							configPageService.addConfigPage(pageInfo,true,oldPageTitle);
						}
						HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
						
						container.getItem(parent).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue((String)textField_4.getValue());

					} catch (CommitException e) {
						// TODO Auto-generated catch block
		
					} catch (Exception e) {
						Notification.show("page save error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					}
					
				}
			});
			
			
			
			horizontal.addComponent(savePageBtn);
			horizontal.setExpandRatio(savePageBtn, 1);
			addComponent(horizontal);
			
		}

	
	private void init(){
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("500px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100%");
		
		// panel_5
		panel_5 = buildPanel_5();
		mainLayout.addComponent(panel_5);
		mainLayout.setComponentAlignment(panel_5, new Alignment(33));
		addComponent(mainLayout);
//		setCompositionRoot(mainLayout);
		
		pageBinding();
	}
	
	private void pageBinding(){

		pageBinder.setItemDataSource(pageInfo);
//		pageBinder.bind(textField_4, "pageName");
		pageBinder.bind(nativeSelect_7, "pageType");
		pageBinder.bind(nativeSelect_12, "pageTemplate");
		pageBinder.bind(textArea_4, "pageDescription");
	
		
	}
	
	
	
	
	
	private Panel buildPanel_5() {
		// common part: create layout
		panel_5 = new Panel();
		panel_5.setCaption("Page Configuration");
		panel_5.setStyleName("page-config");
		panel_5.setImmediate(false);
		panel_5.setWidth("90.0%");
		panel_5.setHeight("900px");
		
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
		verticalLayout_9.setHeight("-1px");
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
		gridLayout_3.setHeight("100.0%");
		gridLayout_3.setMargin(false);
		gridLayout_3.setColumns(4);
		gridLayout_3.setRows(4);
		
		// label_19
		label_19 = new Label();
		label_19.setImmediate(false);
		label_19.setWidth("100.0%");
		label_19.setHeight("40px");
		label_19.setValue("Page Title:");
		gridLayout_3.addComponent(label_19, 0, 0);
		gridLayout_3.setComponentAlignment(label_19, new Alignment(33));
		
		// textField_4
	//	textField_4 = new TextField();
		textField_4.setImmediate(false);
		textField_4.setWidth("100.0%");
		textField_4.setHeight("40px");
		gridLayout_3.addComponent(textField_4, 1, 0);
		gridLayout_3.setComponentAlignment(textField_4, new Alignment(33));
		// label_20
		label_20 = new Label();
		label_20.setImmediate(false);
		label_20.setWidth("100.0%");
		label_20.setHeight("40px");
		label_20.setValue("Page Type:");
		gridLayout_3.addComponent(label_20, 2, 0);
		gridLayout_3.setComponentAlignment(label_20, new Alignment(33));
		
		// nativeSelect_7
//		nativeSelect_7 = new NativeSelect();
//		nativeSelect_7.setImmediate(true);
//		nativeSelect_7.setWidth("100.0%");
//		nativeSelect_7.setHeight("40%");
//		nativeSelect_7.setValue("policy");
//		nativeSelect_7.addItems("policy","insured");
		   
//		nativeSelect_7.addValueChangeListener(new  ValueChangeListener(){
//
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//
//				if(nativeSelect_7.getValue().equals("policy")){
//					nativeSelect_11.removeAllItems();	
//				}else{
//					String productId;
//					try {
//						productId = configService.getProductId(productName);
//						pageInfo.setProductId(productId);
//						List<String> insuredList=configService.getInsuredTypeByProduct(productId);
//						if(insuredList!=null){
//							nativeSelect_11.addItems(insuredList);
//						}
//					
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//
//					}	
//				}
//			}});
		
		gridLayout_3.addComponent(nativeSelect_7, 3, 0);
		gridLayout_3.setComponentAlignment(nativeSelect_7, new Alignment(33));
		
		// label_21
//		label_21 = new Label();
//		label_21.setImmediate(false);
//		label_21.setWidth("100.0%");
//		label_21.setHeight("100.0%");
//		label_21.setValue("Product Name:");
//		gridLayout_3.addComponent(label_21, 0, 1);
//		gridLayout_3.setComponentAlignment(label_21, new Alignment(48));
		
		// nativeSelect_10
//		nativeSelect_10 = new NativeSelect();
//		nativeSelect_10.setImmediate(false);
//		nativeSelect_10.setWidth("100.0%");
//		nativeSelect_10.setHeight("100.0%");
//		gridLayout_3.addComponent(nativeSelect_10, 1, 1);
//		gridLayout_3.setComponentAlignment(nativeSelect_10, new Alignment(33));
		
		// label_22
//		label_22 = new Label();
//		label_22.setImmediate(false);
//		label_22.setWidth("100.0%");
//		label_22.setHeight("100.0%");
//		label_22.setValue("Insured Type:");
//		gridLayout_3.addComponent(label_22, 0, 1);
//		gridLayout_3.setComponentAlignment(label_22, new Alignment(33));
		
		// nativeSelect_11
//		nativeSelect_11 = new NativeSelect();
//		nativeSelect_11.setImmediate(true);
//		nativeSelect_11.setWidth("100.0%");
//		nativeSelect_11.setHeight("100.0%");
//		gridLayout_3.addComponent(nativeSelect_11, 1, 1);
//		gridLayout_3.setComponentAlignment(nativeSelect_11, new Alignment(33));
		
		// label_23
		label_23 = new Label();
		label_23.setImmediate(false);
		label_23.setWidth("100.0%");
		label_23.setHeight("100.0%");
		label_23.setValue("Page Template:");
		gridLayout_3.addComponent(label_23, 0, 1);
		gridLayout_3.setComponentAlignment(label_23, new Alignment(48));
		
		// nativeSelect_12
//		nativeSelect_12 = new NativeSelect();
//		nativeSelect_12.addItems("default template");
//		nativeSelect_12.setImmediate(false);
//		nativeSelect_12.setWidth("100.0%");
//		nativeSelect_12.setHeight("100.0%");
//		gridLayout_3.addComponent(nativeSelect_12, 1, 1);
//		gridLayout_3.setComponentAlignment(nativeSelect_12, new Alignment(33));
		
		// label_24
		label_24 = new Label();
		label_24.setImmediate(false);
		label_24.setWidth("100.0%");
		label_24.setHeight("100.0%");
		label_24.setValue("Page Description:");
		gridLayout_3.addComponent(label_24, 2, 1);
		gridLayout_3.setComponentAlignment(label_24, new Alignment(48));
		
		// textArea_4
		textArea_4 = new TextArea();
		textArea_4.setImmediate(false);
		textArea_4.setWidth("100.0%");
		textArea_4.setHeight("100.0%");
		gridLayout_3.addComponent(textArea_4, 3, 1);
		gridLayout_3.setComponentAlignment(textArea_4, new Alignment(33));
		
		
		addSectionBtn = ComponentFactory.createDefaultButton("Add Section");
		//	addSectionBtn.setCaption("Add Section");
			addSectionBtn.setImmediate(true);
			addSectionBtn.setWidth("-1px");
			addSectionBtn.setHeight("-1px");
			gridLayout_3.addComponent(addSectionBtn, 0, 3);
			gridLayout_3.setComponentAlignment(addSectionBtn, new Alignment(33));
			
			addSectionBtn.addClickListener(new Button.ClickListener() {
				 
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {
					
					
					try {
						pageBinder.commit();
					} catch (CommitException e) {
						// TODO Auto-generated catch block
						Notification.show("page save error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
					}
					HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
//					container.getItem(parent).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(pageInfo.getPageTitle());
					ConfigSectionInfo sectionInfo=new ConfigSectionInfo();
  
					String sectionId = "section"+container.size();
					sectionInfo.setSectionTitle(sectionId);
					sectionInfo.setSectionSequence(pageInfo.getSectionList().size());
					Item section = container.addItem(sectionId);
					container.setChildrenAllowed(sectionId, false);
					section.getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionId);
					section.getItemProperty(PageSectionTreeGenerator.NODE_TYPE).setValue("SECTION");
					pageInfo.getSectionList().add(sectionInfo);
					sectionInfo.setPage(pageInfo);
					SectionAddUI sectionAdd=new SectionAddUI(sectionInfo,pageInfo.getSectionList(),tree,sectionId,PageDisplay.this,pm,false);
					 
					if (pm.getComponentCount() <= 1) {
						pm.addComponent(sectionAdd);
					} else {
						Component last = pm.getSecondComponent();//.getComponent(1);
						pm.replaceComponent(last, sectionAdd);
					}
					 
					container.setParent(sectionId, parent);
				}
			});
			
			
			delPageBtn = ComponentFactory.createDefaultButton("Delete Page");
			//	addSectionBtn.setCaption("Add Section");
			delPageBtn.setImmediate(true);
			delPageBtn.setWidth("-1px");
			delPageBtn.setHeight("-1px");
			gridLayout_3.addComponent(delPageBtn, 1, 3);
			gridLayout_3.setComponentAlignment(delPageBtn, new Alignment(33));
			delPageBtn.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 475722506792175132L;

				@Override
				public void buttonClick(ClickEvent event) {
					HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
					Collection children= container.getChildren(parent);
					if(children!=null&&children.size()>0){
						Object[] ob=children.toArray();
						for(int i=0;i<ob.length;i++){
							container.removeItem(ob[i]);
						}
					}
					
					container.removeItem(parent);
					pageList.remove(pageInfo);
					
					pm.removeComponent(PageDisplay.this);
					if(pageInfo.getPageId()==null||pageInfo.getPageId().isEmpty()){
						return ;
					}
					try {
						configPageService.delConfigPage(pageInfo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Notification.show("page delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					}
				}
			});
			

			savePageBtn = ComponentFactory.createDefaultButton("Save Page");
			//	addSectionBtn.setCaption("Add Section");
			savePageBtn.setImmediate(true);
			savePageBtn.setWidth("-1px");
			savePageBtn.setHeight("-1px");
			savePageBtn.addClickListener(new Button.ClickListener() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						
						
						
						if(pageInfo.getPageId()==null){ 
							pageBinder.commit(); 
							configPageService.addConfigPage(pageInfo,false,null);
						}else{
//							String oldPageTitle=pageInfo.getPageTitle();
							pageBinder.commit();
//							configPageService.addConfigPage(pageInfo,true,oldPageTitle);
						}
						HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
//						container.getItem(parent).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(pageInfo.getPageTitle());

					} catch (CommitException e) {
						// TODO Auto-generated catch block
		
					} catch (Exception e) {
						Notification.show("page save error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					}
					
				}
			});
			
			gridLayout_3.addComponent(savePageBtn, 2, 3);
			gridLayout_3.setComponentAlignment(savePageBtn, new Alignment(33));
			
		
		return gridLayout_3;
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public BeanFieldGroup getPageBinder() {
		return pageBinder;
	}

	public void setPageBinder(BeanFieldGroup pageBinder) {
		this.pageBinder = pageBinder;
	}

	public String getParentItem() {
		return parent;
	}

	public void getParentItem(String parent) {
		this.parent = parent;
	}

	public ProductMain getPm() {
		return pm;
	}

	public void setPm(ProductMain pm) {
		this.pm = pm;
	}
	

}
