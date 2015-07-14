package com.ebao.gs.sp.uiconfig;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.SectionTemplateInfo;
import com.ebao.gs.pol.prdt.config.service.ConfigDynamicFieldService;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;
import com.ebao.gs.pol.prdt.config.service.PageNameService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigDynamicFieldServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigSectionServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.PageNameServiceImpl;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.tests.themes.valo.FormTab;
import com.vaadin.tests.themes.valo.Trees;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class SectionAddUI extends VerticalLayout implements View {

	public static final String SECTION_ADD="sectionAdd";
	
	private VerticalLayout mainLayout;
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
	private ComboBox nativeSelect_1;
	private Label label_4;
	private ComboBox nativeSelect_2;
	
	private ComboBox custSection;
	private TextField sectionName;
	private Label label_5;
	private TextArea textArea_1;
	private Button button_2;
	private Button button_1;
	private Button saveSectionBtn;
	private Button addSectionBtn;
	private VerticalLayout verticalLayout_4;
	private ConfigSectionInfo sectionInfo;
	private BeanFieldGroup<ConfigSectionInfo>	sectionBinder;
	private Tree tree;
	private Table table;
	private String sectionItemId;
	private List<ConfigSectionInfo> sectionList ;
	private ConfigSectionService sectionService=new ConfigSectionServiceImpl();
	private ConfigPageService pageService=new ConfigPageServiceImpl();
	private ConfigDynamicFieldService dynamicFieldService=new ConfigDynamicFieldServiceImpl();
	private PageNameService pageNameService=new PageNameServiceImpl();
	private PageDisplay pageOp;
	private SectionTemplateInfo sectionTemplate;
	private ProductMain pm;
	private boolean isSubSection;
	
	public SectionAddUI(ConfigSectionInfo sectionInfo,List<ConfigSectionInfo>sectionList,Tree tree,
			String sectionItemId,PageDisplay pageOp,ProductMain pm,boolean isSubSection){
		this.sectionInfo=sectionInfo;
		this.tree=tree;
		this.sectionItemId=sectionItemId;
		this.sectionList=sectionList;
		this.pageOp=pageOp;
		this.pm=pm;
		this.isSubSection=isSubSection;
		init1();
		sectionBinding();
	}
	
	private void init1(){
		 Label title = new Label("Section Configuration");
	     addComponent(title);
		
	     final FormLayout form = new FormLayout();
	     form.setWidth("700px");
	     form.setSizeUndefined();

	     addComponent(form);

		textField_1 = new TextField("Section Title:");
		textField_1.setRequired(true);
		textField_1.setImmediate(false);
		textField_1.setWidth("400px");
		textField_1.setHeight("40px");
		form.addComponent(textField_1);
		textField_1.addBlurListener(new BlurListener(){

				@Override
				public void blur(BlurEvent event) {
					String oldName=sectionInfo.getSectionTitle(); 
					if(textField_1.getValue().trim().length()>PageDisplay.MAX_NAME_LENGTH){
						Notification.show("Section Title Warning:","Title of section is too long", Notification.Type.WARNING_MESSAGE);
						textField_1.focus();
						return;
					}
					if(oldName.equals(textField_1.getValue().trim()))return ;
					for(ConfigSectionInfo section:sectionList){
						if(section.getSectionTitle().equals(textField_1.getValue().trim())){
							Notification.show("section name error","duplicate name for section", Notification.Type.ERROR_MESSAGE);
							textField_1.focus();
							return;
						}
					}  
					if((custSection.getValue()==null||((String)custSection.getValue()).isEmpty())){
						sectionName.setValue(textField_1.getValue());
					}
				}});
	     
		sectionName=new TextField("Section Name");
		sectionName.setImmediate(false);
		sectionName.setWidth("400px");
		sectionName.setHeight("40px");
		form.addComponent(sectionName);
				
				
				
		nativeSelect_1 = new ComboBox("Section Type:");
		nativeSelect_1.addItems("Form","Table");
		nativeSelect_1.setImmediate(false);
		nativeSelect_1.setWidth("400px");
		nativeSelect_1.setHeight("40px");
		if(sectionInfo.getParentSection()!=null||sectionInfo.getSubSectionList().size()==0){
			form.addComponent(nativeSelect_1);
		}
		
		
	     

		nativeSelect_2 = new ComboBox("Section Template:");
		nativeSelect_2.addItems("Default Section","ReadOnly Section","Table Section");
		nativeSelect_2.setImmediate(false);
		nativeSelect_2.setHeight("40px");
		nativeSelect_2.setWidth("400px");
	
	
		form.addComponent(nativeSelect_2);
		
		
		
			custSection =new  ComboBox("Customized Section");
			List<String> tagName;
			try {
				tagName = sectionService.getCustSectionName();
				custSection.addItems(tagName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				
			}
			
			custSection.setImmediate(false);
			custSection.setHeight("40px");
			custSection.setWidth("400px");


			custSection.addBlurListener(new BlurListener(){

				@Override
				public void blur(BlurEvent event) {
					String value=(String)custSection.getValue();
					if(value==null||value.isEmpty()){
						button_1.setEnabled(true);
						textField_1.setEnabled(true);
						nativeSelect_1.setEnabled(true);
					}else{
						
						try {
							if(sectionTemplate==null){
								sectionTemplate = sectionService.getSectionNameByTag(value,true);
							}
						
							if(sectionTemplate.getSectionName()!=null){
								textField_1.setValue(sectionTemplate.getSectionName());
								sectionName.setValue(sectionTemplate.getSectionName());
							}else{ 
								textField_1.setValue(value);
								sectionName.setValue(value);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
					
						}
						
						textField_1.setEnabled(false);
						nativeSelect_1.setEnabled(false);
						button_1.setEnabled(false);
						nativeSelect_1.setValue("");
					}
				}
				
			});
		if(!isSubSection){		
			form.addComponent(custSection);
		}
		
		

		textArea_1 = new TextArea("Section Description:");
		textArea_1.setImmediate(false);
		textArea_1.setWidth("400px");
		textArea_1.setHeight("60px"); 
		form.addComponent(textArea_1);
		
		
		HorizontalLayout horizontal=new HorizontalLayout();
		horizontal.setSpacing(true);
		horizontal.setWidth("600px");
		button_1 = new Button("Add Field");
		button_1.setImmediate(true);
		button_1.setWidth("120px");
		button_1.setStyleName("primary");
		if(sectionInfo.getParentSection()!=null||sectionInfo.getSubSectionList().size()==0){
			horizontal.addComponent(button_1);
		}
		
		button_1.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
		
				try {
					sectionBinder.commit();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					Notification.show("section save error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
				HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
				container.getItem(sectionItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionInfo.getSectionTitle());
				
				
				final Window fieldWindow = new Window(null);
				fieldWindow.setCaption("Edit Field Property");
				fieldWindow.setWidth("950px");
	//			fieldWindow.setStyleName("basedialog");
				fieldWindow.setHeight("700px");
				HorizontalSplitPanel splitPanel=new HorizontalSplitPanel();
				splitPanel.setSplitPosition(32);
				fieldWindow.setContent(splitPanel);
				ConfigFieldInfo fieldInfo=new ConfigFieldInfo();
				int productId=0;
				if(sectionInfo.getPage()==null){
					productId=Integer.parseInt(sectionInfo.getParentSection().getPage().getProductId());
				}else{
					productId=Integer.parseInt(sectionInfo.getPage().getProductId());
				}
				fieldInfo.setPrdtId(productId);
				fieldInfo.setEdit(false);
				fieldInfo.setSequence(sectionInfo.getFieldList().size());
				String sectionVariable=null;
				String skinboClassName=null;
//				if(sectionInfo.getSectionType()!=null&&!sectionInfo.getSectionType().isEmpty()){
				
				if(sectionTemplate!=null){
					sectionVariable=sectionTemplate.getVariable();
					skinboClassName=sectionTemplate.getClassName();
				}
				
					
//				}
				
				splitPanel.addComponent(new Trees(splitPanel,sectionInfo,fieldInfo,fieldWindow,table,false,SectionAddUI.this,pageOp.getParentItem(),sectionItemId,sectionVariable,skinboClassName));
				
				fieldWindow.center();
				UI.getCurrent().addWindow(fieldWindow);
		
			}
		});
		
		if(sectionInfo.getSectionType()!=null&&!sectionInfo.getSectionType().isEmpty()){
			button_1.setVisible(false);
		}
		button_2 = new Button("Delete Section");
		button_2.setImmediate(true);
		button_2.setWidth("120px");
		button_2.setStyleName("primary");
		button_2.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				
				 ConfirmDialog dialog=ConfirmDialog.show(getUI(), "Reminder Message",
				        "Are you sure to delete section?", "Yes", "No",
				        new ConfirmDialog.Listener() {

				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				                	SectionAddUI.this.getPageOp().getPm().removeComponent(SectionAddUI.this.getPageOp().getPm().getSecondComponent());
				    				sectionList.remove(sectionInfo);
				    				HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
				    				container.removeItemRecursively(sectionItemId);
				    			
				    				if(sectionInfo.getSectionId()==null||sectionInfo.getSectionId().isEmpty())return ;
				    				try {
				    					sectionService.delConfigSection(sectionInfo);
				    				} catch (Exception e) {

				    					Notification.show("section delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				    				}
				                	
				                } 
				            }
				        });		
				  dialog.getCancelButton().setStyleName("primary");
			}});
		horizontal.addComponent(button_2);

		
		saveSectionBtn =new Button("Save Section");
		saveSectionBtn.setImmediate(true);
		saveSectionBtn.setWidth("120px");
		saveSectionBtn.setStyleName("primary");
		saveSectionBtn.addClickListener(new Button.ClickListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if(textField_1.getValue()==null||textField_1.getValue().isEmpty()){
						Notification.show("section save error", "please check the required item", Notification.Type.ERROR_MESSAGE);
						return ;
					}	
					HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
					if(!isSubSection){
						if(sectionInfo.getPage().getPageId()==null||sectionInfo.getPage().getPageId().isEmpty()){
							pageOp.getPageBinder().commit();
							String pageDesc=pageNameService.findPageNameDescByCode(sectionInfo.getPage().getPageName());
							container.getItem(pageOp.getParentItem()).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(pageDesc);
							pageService.addConfigPage(sectionInfo.getPage(),false,null);
							
						}
						if(sectionInfo.getSectionId()!=null&&!sectionInfo.getSectionId().isEmpty()){ 
							String sectionTitle=sectionInfo.getSectionTitle();
							sectionBinder.commit();
							if(sectionTitle.equals(sectionInfo.getSectionTitle())){
								sectionService.addConfigSection(sectionInfo,true,null);
							}else{
								sectionService.addConfigSection(sectionInfo,true,sectionTitle);
							}
						}else{
							sectionInfo.setEdit(true);
							sectionBinder.commit();
							sectionInfo.setSectionSequence(sectionInfo.getPage().getSectionList().size());
							sectionInfo.getPage().getSectionList().add(sectionInfo);
							sectionService.addConfigSection(sectionInfo,false,null);
							for(ConfigSectionInfo subsection:sectionInfo.getSubSectionList()){
								sectionService.addConfigSection(subsection,false,null);
							}
						}
					}else{
						ConfigPageInfo page=sectionInfo.getParentSection().getPage();
						if(page.getPageId()==null||page.getPageId().isEmpty()){
							pageOp.getPageBinder().commit();
							String desc=pageNameService.findPageNameDescByCode(page.getPageName());
							container.getItem(pageOp.getParentItem()).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(desc);
							pageService.addConfigPage(page,false,null);
						}
						ConfigSectionInfo parentSection=sectionInfo.getParentSection();
						if(parentSection.getSectionId()==null||parentSection.getSectionId().isEmpty()){ 
							String sectionTitle=parentSection.getSectionTitle();
							if(sectionTitle.equals(parentSection.getSectionTitle())){
								sectionService.addConfigSection(parentSection,true,null);
							}else{
								sectionService.addConfigSection(parentSection,true,sectionTitle);
							}
						}
						if(sectionInfo.getSectionId()!=null&&!sectionInfo.getSectionId().isEmpty()){ 
							String sectionTitle=sectionInfo.getSectionTitle();
							sectionBinder.commit();
							if(sectionTitle.equals(sectionInfo.getSectionTitle())){
								sectionService.addConfigSection(sectionInfo,true,null);
							}else{
								sectionService.addConfigSection(sectionInfo,true,sectionTitle);
							}
						}else{
							sectionBinder.commit();
							sectionInfo.setSectionSequence(sectionInfo.getParentSection().getSubSectionList().size()-1);
	//						sectionInfo.getParentSection().getSubSectionList().add(sectionInfo);
							sectionService.addConfigSection(sectionInfo,false,null);
						}
						
						
					}
					container.getItem(sectionItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionInfo.getSectionTitle());
				
				} catch (CommitException e) {
					// TODO Auto-generated catch block
			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Notification.show("section save error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
				

			}});
		horizontal.addComponent(saveSectionBtn);

		
		
			addSectionBtn =new Button("Add Section");
			addSectionBtn.setImmediate(true);
			addSectionBtn.setWidth("120px");
			addSectionBtn.setStyleName("primary");
			addSectionBtn.addClickListener(new Button.ClickListener(){

				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {
					if(sectionInfo.getFieldList().size()>0){

						
						 ConfirmDialog dialog=ConfirmDialog.show(getUI(), "Reminder Message",
						        "Are you sure to delete field list in this subsection?", "Yes", "No",
						        new ConfirmDialog.Listener() {

						            public void onClose(ConfirmDialog dialog) {
						                if (dialog.isConfirmed()) {
						                	HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
						                	for(ConfigFieldInfo fieldInfo:sectionInfo.getFieldList()){
						                		
						                		try {
													dynamicFieldService.deleteDynamicFieldConfig(fieldInfo);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													
												}
						                		
						                	}
						                	
						                	
						                	sectionInfo.getFieldList().clear();
											
											ConfigSectionInfo subsectionInfo=new ConfigSectionInfo();
											try {
												sectionBinder.commit();
											} catch (CommitException e) {
												Notification.show("Section add error", "Can not commit section data", Notification.Type.ERROR_MESSAGE);
												return ;
											}
											 
											String parentItemId=sectionItemId;
											container.setChildrenAllowed(parentItemId, true);
											String sectionId = "section"+container.size();
											String sectionItemId=sectionInfo.getPage().getPageName()+"-"+sectionId;
											subsectionInfo.setSectionTitle(sectionId);
											Item section = container.addItem(sectionItemId);
											container.setChildrenAllowed(sectionItemId, false);
											section.getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionId);
											section.getItemProperty(PageSectionTreeGenerator.NODE_TYPE).setValue("SECTION");
											sectionInfo.getSubSectionList().add(subsectionInfo);
											subsectionInfo.setParentSection(sectionInfo);
											
											SectionAddUI sectionAdd=new SectionAddUI(subsectionInfo,sectionInfo.getSubSectionList(),tree,sectionItemId,pageOp,pm,true);
											 
											if (pm.getComponentCount() <= 1) {
												pm.addComponent(sectionAdd);
											} else {
												Component last = pm.getSecondComponent();//.getComponent(1);
												pm.replaceComponent(last, sectionAdd);
											}  
											
											container.setParent(sectionItemId, parentItemId);
						                	
						                } 
						            }
						        });		
						  dialog.getCancelButton().setStyleName("primary");
					
						
					}
					
					
					
					
					HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
					
					
					if(sectionInfo.getPage()==null){
						Notification.show("Section add error", "Can not add section in nest section", Notification.Type.HUMANIZED_MESSAGE);
						return ;
					}
					ConfigSectionInfo subsectionInfo=new ConfigSectionInfo();
					try {
						sectionBinder.commit();
					} catch (CommitException e) {
						Notification.show("Section add error", "Can not commit section data", Notification.Type.ERROR_MESSAGE);
						return ;
					}
					 
					String parentItemId=sectionItemId;
					container.setChildrenAllowed(parentItemId, true);
					String sectionId = "section"+container.size();
					String sectionItemId=sectionInfo.getPage().getPageName()+"-"+sectionId;
					subsectionInfo.setSectionTitle(sectionId);
					Item section = container.addItem(sectionItemId);
					container.setChildrenAllowed(sectionItemId, false);
					section.getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionId);
					section.getItemProperty(PageSectionTreeGenerator.NODE_TYPE).setValue("SECTION");
					sectionInfo.getSubSectionList().add(subsectionInfo);
					subsectionInfo.setParentSection(sectionInfo);
					
					SectionAddUI sectionAdd=new SectionAddUI(subsectionInfo,sectionInfo.getSubSectionList(),tree,sectionItemId,pageOp,pm,true);
					 
					if (pm.getComponentCount() <= 1) {
						pm.addComponent(sectionAdd);
					} else {
						Component last = pm.getSecondComponent();//.getComponent(1);
						pm.replaceComponent(last, sectionAdd);
					}  
					
					container.setParent(sectionItemId, parentItemId);
				}
				
			});
		if(!isSubSection){
//			horizontal.addComponent(addSectionBtn);
		}
	
		
		addComponent(horizontal);
		horizontal.setMargin(true);
		horizontal.setSpacing(true);
		table = new Table("Field List");
		table.setWidth("100%");
	
		table.addContainerProperty("Field Name", Label.class,  null);
		table.addContainerProperty("Edit",       Button.class, null);
		table.addContainerProperty("Delete",        Button.class,    null);
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		table.setDragMode(TableDragMode.ROW);
		table.setDropHandler(new DropHandler(){

			@SuppressWarnings("unchecked")
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
		        
		        // Make sure the drag source is the same tree
		        if (t.getSourceComponent() != table)
		            return;

		        AbstractSelectTargetDetails target = (AbstractSelectTargetDetails) event.getTargetDetails();
		        Object sourceItemId = t.getData("itemId");
		        Object targetItemId = target.getItemIdOver();
		        
		        if(sourceItemId==targetItemId||targetItemId==null)return;
		        // On which side of the target the item was dropped 
		        VerticalDropLocation location = target.getDropLocation();
		        
		        IndexedContainer container = (IndexedContainer)table.getContainerDataSource();
		        int newIndex = container.indexOfId(targetItemId) - 1;
		        int sourceIndex=container.indexOfId(sourceItemId);
		        if(location==VerticalDropLocation.MIDDLE)return ;
		        if (location != VerticalDropLocation.TOP) {
		        	newIndex++;
		        	
		        }
		        if (newIndex < 0) {
		        	newIndex = 0;
		    	
	        	 try {
					IndexedContainer  clone = (IndexedContainer) container.clone();
					 container.removeItem(sourceItemId);
					Item newItem= container.addItemAt(0, sourceItemId);
					 Item item = clone.getItem(sourceItemId);
	                    for (Object propId : item.getItemPropertyIds()) {
	                        newItem.getItemProperty(propId).setValue(
	                                item.getItemProperty(propId).getValue());
	                    }

				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					Notification.show("field drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
                  
                   
                   
           }else{
        	   Object idAfter = container.getIdByIndex(newIndex);
		        
		        Collection<?> selections = (Collection<?>) table.getValue();
		        if (selections != null && selections.contains(sourceItemId)) {
                   // dragged a selected item, if multiple rows selected, drag
                   // them too (functionality similar to apple mail)
                   for (Object object : selections) {
                       moveAfter(container, object, idAfter);
                   }
		        }else{
		        	moveAfter(container, sourceItemId, idAfter);
		        }
           }
		       
		    if(sourceIndex<newIndex){
		    	   int tem=newIndex;
		    	   newIndex=sourceIndex;
		    	   sourceIndex=tem;
		       }
		       for(int i=newIndex;i<=sourceIndex;i++){ 
		    	  String itemId=(String) container.getIdByIndex(i);
		    	  for(ConfigFieldInfo fieldIn:sectionInfo.getFieldList()){
		    		  if(fieldIn.getFieldName().equals(itemId)){
		    			  fieldIn.setSequence(i);
		    			  try {
							dynamicFieldService.addDynamicFieldConfig(fieldIn, 0, true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Notification.show("field drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
						}
		    		  }
		    		  
		    	  }
		    	   
		       }
		        
			}
			
			  @SuppressWarnings("unchecked")
			private void moveAfter(IndexedContainer containerDataSource,
	                    Object itemId, Object idAfter) {
	                try {
	                    IndexedContainer clone = null;
	                    clone = (IndexedContainer) containerDataSource.clone();
	                    containerDataSource.removeItem(itemId);
	                    Item newItem = containerDataSource.addItemAfter(idAfter,
	                            itemId);
	                    Item item = clone.getItem(itemId);
	                    for (Object propId : item.getItemPropertyIds()) {
	                        newItem.getItemProperty(propId).setValue(
	                                item.getItemProperty(propId).getValue());
	                    }

	                    // TODO Auto-generated method stub
	                } catch (CloneNotSupportedException e) {
	                    // TODO Auto-generated catch block
	                	Notification.show("field drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
	                }

	            }
			@Override
			public AcceptCriterion getAcceptCriterion() {
				
				return (AcceptCriterion) SourceIsTarget.get();
			}} );
		for(ConfigFieldInfo fieldInfo: sectionInfo.getFieldList()){
			addItemToTable(fieldInfo);
		}
		
		addComponent(table);
	}
	
	
	
	
	private void sectionBinding(){
		sectionBinder=new BeanFieldGroup<ConfigSectionInfo>(ConfigSectionInfo.class);
		sectionBinder.setItemDataSource(sectionInfo);
		sectionBinder.bind(textField_1, "sectionTitle");
//		sectionBinder.bind(textField_2, "sectionId");
		sectionBinder.bind(nativeSelect_1, "type");
		sectionBinder.bind(nativeSelect_2, "sectionTemplate");
		sectionBinder.bind(textArea_1, "description");
		sectionBinder.bind(custSection, "sectionType");
		if(!isSubSection){
			sectionBinder.bind(sectionName, "sectionName");
		}else{
			sectionBinder.bind(sectionName, "subSectionName");
		}
		if(sectionInfo.getSectionName()==null||sectionInfo.getSectionName().isEmpty()){
			sectionName.setValue(textField_1.getValue());
		}
		sectionName.setEnabled(false);
		if(sectionInfo.getParentSection()!=null){
			textField_1.setEnabled(false);
		}
		if(custSection.getValue()!=null&&!((String)custSection.getValue()).isEmpty()){
			textField_1.setEnabled(false);
			nativeSelect_1.setEnabled(false);
			nativeSelect_1.setValue("");
		}
		
		custSection.addValueChangeListener(new ValueChangeListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value=(String)custSection.getValue();
				try {
					
					List<String> subSectionList=sectionService.getSubSectionListByCustSectionName(value);
					HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
					ConfigPageInfo pageInfo=sectionInfo.getPage();
					Collection chlren=container.getChildren(sectionItemId);
					if(chlren!=null&&chlren.size()>0){
						Object[] obList=chlren.toArray();
						for(int i=0;i<obList.length;i++){
							container.removeItem(obList[i]);
						}
					}

					if(subSectionList.size()>0){
						container.setChildrenAllowed(sectionItemId, true);
					}	
					if(sectionTemplate==null){
						sectionTemplate = sectionService.getSectionNameByTag(value,true);
					}
					String secName =sectionTemplate.getSectionName();
					if(secName!=null){
						container.getItem(sectionItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(secName);
					
						sectionInfo.setSectionTitle(secName);
						sectionInfo.setSectionName(secName);
						sectionInfo.setSectionType(value);
					}else{
						if(value==null||value.isEmpty()){
							value=sectionItemId.split("-")[1];
							textField_1.setValue(value);
							sectionName.setValue(value);
						}
						container.getItem(sectionItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(value);
					} 
				
					for(String subsection:subSectionList){
						ConfigSectionInfo secInfo=new ConfigSectionInfo();

						String subsectionItemId=pageInfo.getPageName()+"-"+subsection;
						secInfo.setSectionTitle(subsection);
						secInfo.setSubSectionName(subsection);
						secInfo.setSectionName(sectionInfo.getSectionName());
						Item section = container.addItem(subsectionItemId);
						if(section==null)continue;
						container.setChildrenAllowed(subsectionItemId, false);
						section.getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(subsection);
						section.getItemProperty(PageSectionTreeGenerator.NODE_TYPE).setValue("SECTION");
						sectionInfo.getSubSectionList().add(secInfo);	
						secInfo.setParentSection(sectionInfo);
						container.setParent(subsectionItemId, sectionItemId);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
				
				}
				
			}	
		} );
		boolean isSubsection=true;
		if(sectionInfo.getParentSection()==null){
			isSubsection=false;
		}
		try {
			sectionTemplate=sectionService.getSectionNameByTag(sectionInfo.getSectionTitle(),!isSubsection);
		} catch (Exception e) {
		}
		
	}
	
	private void init(){
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		sectionLayout=new VerticalLayout();
		panel_1 = buildPanel_1();
		sectionLayout.addComponent(panel_1);
		sectionLayout.setComponentAlignment(panel_1, new Alignment(20));
		mainLayout.addComponent(sectionLayout);
		mainLayout.setComponentAlignment(sectionLayout, new Alignment(20));
		addComponent(mainLayout);
//		setCompositionRoot(mainLayout);
		
	}
	private Panel buildPanel_1() {
		// common part: create layout
		
		final Panel panel_1 = new Panel("Section Configuration");
		panel_1.setImmediate(false);
		panel_1.setWidth("100%");
		panel_1.setHeight("800px");
		
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
		textField_1 = new TextField();
		textField_1.setImmediate(false);
		textField_1.setWidth("157px");
		textField_1.setHeight("-1px");
		gridLayout_1.addComponent(textField_1, 1, 0);
		gridLayout_1.setComponentAlignment(textField_1, new Alignment(33));
		// label_2
//		label_2 = new Label();
//		label_2.setImmediate(false);
//		label_2.setWidth("-1px");
//		label_2.setHeight("-1px");
//		label_2.setValue("Section Id:");
//		gridLayout_1.addComponent(label_2, 2, 0);
//		gridLayout_1.setComponentAlignment(label_2, new Alignment(48));
		// textField_2
//		textField_2 = new TextField();
//		textField_2.setImmediate(false);
//		textField_2.setWidth("145px");
//		textField_2.setHeight("-1px");
//		gridLayout_1.addComponent(textField_2, 3, 0);
//		gridLayout_1.setComponentAlignment(textField_2, new Alignment(33));
		
		// label_3
		label_3 = new Label();
		label_3.setImmediate(false);
		label_3.setWidth("-1px");
		label_3.setHeight("-1px");
		label_3.setValue("Section Type:");
		gridLayout_1.addComponent(label_3, 2, 0);
		gridLayout_1.setComponentAlignment(label_3, new Alignment(48));
		// nativeSelect_1
//		nativeSelect_1 = new NativeSelect();
//		nativeSelect_1.addItems("Form","Table");
//		nativeSelect_1.setImmediate(false);
//		nativeSelect_1.setWidth("157px");
//		nativeSelect_1.setHeight("-1px");
//		gridLayout_1.addComponent(nativeSelect_1, 3, 0);
//		gridLayout_1.setComponentAlignment(nativeSelect_1, new Alignment(33));
		
		// label_4
		label_4 = new Label();
		label_4.setImmediate(false);
		label_4.setWidth("-1px");
		label_4.setHeight("-1px");
		label_4.setValue("Section Template:");
		gridLayout_1.addComponent(label_4, 0, 1);
		gridLayout_1.setComponentAlignment(label_4, new Alignment(48));
		// nativeSelect_2
//		nativeSelect_2 = new NativeSelect();
//		nativeSelect_2.addItems("Default Section","ReadOnly Section","Table Section");
//		nativeSelect_2.setImmediate(false);
//		nativeSelect_2.setWidth("145px");
//		nativeSelect_2.setHeight("-1px");
//		gridLayout_1.addComponent(nativeSelect_2, 1, 1);
//		gridLayout_1.setComponentAlignment(nativeSelect_2, new Alignment(33));
		
		// label_5
		label_5 = new Label();
		label_5.setImmediate(false);
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue("Section Description:");
		gridLayout_1.addComponent(label_5, 2, 1);
		gridLayout_1.setComponentAlignment(label_5, new Alignment(48));
		// textArea_1
		textArea_1 = new TextArea();
		textArea_1.setImmediate(false);
		textArea_1.setWidth("180px");
		textArea_1.setHeight("50px");
		gridLayout_1.addComponent(textArea_1, 3, 1);
		gridLayout_1.setComponentAlignment(textArea_1, new Alignment(33));
		
	
		// button_1
		button_1 = ComponentFactory.createDefaultButton("Add Field");
		button_1.setImmediate(true);
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		
		

		gridLayout_1.addComponent(button_1, 0, 3);
		gridLayout_1.setComponentAlignment(button_1, new Alignment(48));
		
		// button_2
		button_2 = ComponentFactory.createDefaultButton("Delete Section");

		button_2.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				
				HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
//				Collection children=container.getChildren(sectionItemId);
//				if(children!=null&&children.size()>0){
//					Object[] obChildren=children.toArray();
//					container.removeItemRecursively(itemId)
//					
//					
//					
//				}
				
				
				container.removeItemRecursively(sectionItemId);
				if(sectionInfo.getSectionId()==null||sectionInfo.getSectionId().isEmpty())return ;
				try {
					sectionList.remove(sectionInfo);
					sectionService.delConfigSection(sectionInfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Notification.show("section delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
			}});
		button_2.setImmediate(true);
		button_2.setWidth("-1px");
		button_2.setHeight("-1px");
		gridLayout_1.addComponent(button_2, 1, 3);
		gridLayout_1.setComponentAlignment(button_2, new Alignment(5));
		
		
		saveSectionBtn = ComponentFactory.createDefaultButton("Save Section");
		saveSectionBtn.addClickListener(new Button.ClickListener(){

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						
						HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
						if(sectionInfo.getPage().getPageId()==null||sectionInfo.getPage().getPageId().isEmpty()){
							pageOp.getPageBinder().commit();
						
//							container.getItem(pageOp.getParentItem()).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionInfo.getPage().getPageTitle());
							pageService.addConfigPage(sectionInfo.getPage(),false,null);
						}
						
						if(sectionInfo.getSectionId()!=null&&!sectionInfo.getSectionId().isEmpty()){ 
							String sectionTitle=sectionInfo.getSectionTitle();
							sectionBinder.commit();
							if(sectionTitle.equals(sectionInfo.getSectionTitle())){
								sectionService.addConfigSection(sectionInfo,true,null);
							}else{
								sectionService.addConfigSection(sectionInfo,true,sectionTitle);
							}
						}else{
							sectionBinder.commit();
							sectionService.addConfigSection(sectionInfo,false,null);
							for(ConfigSectionInfo subsection:sectionInfo.getSubSectionList()){
								sectionService.addConfigSection(subsection,false,null);
							}
						}
					
						container.getItem(sectionItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionInfo.getSectionTitle());
					
					} catch (CommitException e) {
						// TODO Auto-generated catch block
				
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Notification.show("section save error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					}
					

				}});
		saveSectionBtn.setImmediate(true);
		saveSectionBtn.setWidth("-1px");
		saveSectionBtn.setHeight("-1px");
		gridLayout_1.addComponent(saveSectionBtn, 2, 3);
		gridLayout_1.setComponentAlignment(saveSectionBtn, new Alignment(5));
		
		panel_3 = new Panel();
		panel_3.setCaption("Field List");
		panel_3.setStyleName("field-config");
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
		
		
		table = new Table();
		table.setWidth("100%");
		table.addContainerProperty("Field Name", Label.class,  null);
		table.setColumnWidth("Field Name", 200);
		table.addContainerProperty("Edit",       Button.class, null);
		table.setColumnWidth("Edit", 200);
		table.addContainerProperty("Delete",     Button.class,    null);
		table.setDragMode(Table.TableDragMode.ROW);
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		table.setDropHandler(new DropHandler(){

			@SuppressWarnings("unchecked")
			@Override
			public void drop(DragAndDropEvent event) {
				Transferable t = event.getTransferable();
		        
		        // Make sure the drag source is the same tree
		        if (t.getSourceComponent() != table)
		            return;

		        AbstractSelectTargetDetails target = (AbstractSelectTargetDetails) event.getTargetDetails();
		        Object sourceItemId = t.getData("itemId");
		        Object targetItemId = target.getItemIdOver();
		        
		        if(sourceItemId==targetItemId||targetItemId==null)return;
		        // On which side of the target the item was dropped 
		        VerticalDropLocation location = target.getDropLocation();
		        
		        IndexedContainer container = (IndexedContainer)table.getContainerDataSource();
		        int newIndex = container.indexOfId(targetItemId) - 1;
		        int sourceIndex=container.indexOfId(sourceItemId);
		        if(location==VerticalDropLocation.MIDDLE)return ;
		        if (location != VerticalDropLocation.TOP) {
		        	newIndex++;
		        	
		        }
		        if (newIndex < 0) {
		        	newIndex = 0;
		    	
	        	 try {
					IndexedContainer  clone = (IndexedContainer) container.clone();
					 container.removeItem(sourceItemId);
					Item newItem= container.addItemAt(0, sourceItemId);
					 Item item = clone.getItem(sourceItemId);
	                    for (Object propId : item.getItemPropertyIds()) {
	                        newItem.getItemProperty(propId).setValue(
	                                item.getItemProperty(propId).getValue());
	                    }

				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					Notification.show("field drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
                  
                   
                   
           }else{
        	   Object idAfter = container.getIdByIndex(newIndex);
		        
		        Collection<?> selections = (Collection<?>) table.getValue();
		        if (selections != null && selections.contains(sourceItemId)) {
                   // dragged a selected item, if multiple rows selected, drag
                   // them too (functionality similar to apple mail)
                   for (Object object : selections) {
                       moveAfter(container, object, idAfter);
                   }
		        }else{
		        	moveAfter(container, sourceItemId, idAfter);
		        }
           }
		       
		    if(sourceIndex<newIndex){
		    	   int tem=newIndex;
		    	   newIndex=sourceIndex;
		    	   sourceIndex=tem;
		       }
		       for(int i=newIndex;i<=sourceIndex;i++){ 
		    	  String itemId=(String) container.getIdByIndex(i);
		    	  for(ConfigFieldInfo fieldIn:sectionInfo.getFieldList()){
		    		  if(fieldIn.getFieldName().equals(itemId)){
		    			  fieldIn.setSequence(i);
		    			  try {
							dynamicFieldService.addDynamicFieldConfig(fieldIn, 0, true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Notification.show("field drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
						}
		    		  }
		    		  
		    	  }
		    	   
		       }
		        
			}
			
			  private void moveAfter(IndexedContainer containerDataSource,
	                    Object itemId, Object idAfter) {
	                try {
	                    IndexedContainer clone = null;
	                    clone = (IndexedContainer) containerDataSource.clone();
	                    containerDataSource.removeItem(itemId);
	                    Item newItem = containerDataSource.addItemAfter(idAfter,
	                            itemId);
	                    Item item = clone.getItem(itemId);
	                    for (Object propId : item.getItemPropertyIds()) {
	                        newItem.getItemProperty(propId).setValue(
	                                item.getItemProperty(propId).getValue());
	                    }

	                    // TODO Auto-generated method stub
	                } catch (CloneNotSupportedException e) {
	                    // TODO Auto-generated catch block
	                	Notification.show("field drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
	                }

	            }
			@Override
			public AcceptCriterion getAcceptCriterion() {
				
				return (AcceptCriterion) SourceIsTarget.get();
			}} );

		if(sectionInfo.getFieldList().size()>0){
			ConfigFieldInfo[] fieldArray=new ConfigFieldInfo[sectionInfo.getFieldList().size()];
			for(ConfigFieldInfo fieldInfo: sectionInfo.getFieldList()){
				fieldArray[fieldInfo.getSequence()]=fieldInfo;
			}
			List<ConfigFieldInfo> linkedList=new LinkedList<ConfigFieldInfo>();
			for(int i=0;i<fieldArray.length;i++){
				linkedList.add(fieldArray[i]);
			}
			sectionInfo.setFieldList(linkedList);
		}
		
		
		for(ConfigFieldInfo fieldInfo: sectionInfo.getFieldList()){
			addItemToTable(fieldInfo);
		}
		
		verticalLayout_4.addComponent(table);
		
		panel_3.setContent(verticalLayout_4);
		button_1.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
			
				try {
					sectionBinder.commit();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					Notification.show("section save error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
				HierarchicalContainer container = (HierarchicalContainer) tree.getContainerDataSource();
				container.getItem(sectionItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).setValue(sectionInfo.getSectionTitle());
				
				
				final Window fieldWindow = new Window(null);
				fieldWindow.setCaption("Edit Field Property");
				fieldWindow.setWidth("950px");
				fieldWindow.setHeight("700px");
				HorizontalSplitPanel splitPanel=new HorizontalSplitPanel();
				splitPanel.setSplitPosition(32);
	//			subContent.setMargin(true);
				fieldWindow.setContent(splitPanel);
				ConfigFieldInfo fieldInfo=new ConfigFieldInfo();
				fieldInfo.setEdit(false);
				fieldInfo.setSequence(sectionInfo.getFieldList().size());
//				fieldInfo.setSection(sectionInfo); 
	//			splitPanel.addComponent(new Trees(splitPanel,sectionInfo,fieldInfo,fieldWindow,table,false,SectionAddUI.this,pageOp.getParentItem(),sectionItemId));
	//			splitPanel.addComponent(new Forms(sectionInfo,fieldInfo,fieldWindow,verticalLayout_4));
				
				fieldWindow.center();
				UI.getCurrent().addWindow(fieldWindow);
				
			}
		});
		
		return gridLayout_1;
	}
	
	private void addItemToTable(final ConfigFieldInfo fieldInfo){
    	Label commentsField = new Label();
    	commentsField.setWidth("200px");
    	commentsField.setValue(fieldInfo.getFieldName());

    	Button editBtn=new Button("Edit");//ComponentFactory.createDefaultButton("Edit");
    	editBtn.setWidth("80px");
    	editBtn.setStyleName("primary");
    	editBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				final Window fieldWindow = new Window(null);
				fieldWindow.setCaption("Edit Field Property:");
				fieldWindow.setStyleName("light");
//				fieldWindow.setStyleName("basedialog");
				fieldWindow.setWidth("600px"); 
				fieldWindow.setHeight("500px");
//				HorizontalSplitPanel splitPanel=new HorizontalSplitPanel();
				
				
				FormTab forms=new FormTab(sectionInfo,fieldInfo,fieldWindow,table,null,null,null,null,null,null,null); 
				fieldWindow.setContent(forms);
//				splitPanel.addComponent(new Forms(sectionInfo,fieldInfo,fieldWindow,table,null));
				
				fieldWindow.center();
				UI.getCurrent().addWindow(fieldWindow);
				
			}
		});
    	
    	Button delBtn=new Button("Delete");//ComponentFactory.createDefaultButton("Delete");
    	delBtn.setWidth("80px");
    	delBtn.setStyleName("primary");
    	delBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				ConfirmDialog dialog=	ConfirmDialog.show(getUI(), "Reminder Message",
				        "Are you sure to delete field?", "Yes", "No",
				        new ConfirmDialog.Listener() {

				            public void onClose(ConfirmDialog dialog) {
				                if (dialog.isConfirmed()) {
				    				
				                	table.removeItem(fieldInfo.getFieldName());
				    				sectionInfo.getFieldList().remove(fieldInfo);
				    				try {
				    					dynamicFieldService.deleteDynamicFieldConfig(fieldInfo);
				    				} catch (Exception e) {
				    					// TODO Auto-generated catch block
				    					Notification.show("field delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				    				}
				                	
				                }  
				            }
				        });	
				dialog.getCancelButton().setStyleName("primary");
				
//				table.removeItem(fieldInfo.getFieldName());
//				sectionInfo.getFieldList().remove(fieldInfo);
//				try {
//					dynamicFieldService.deleteDynamicFieldConfig(fieldInfo);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					Notification.show("field delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
//				}
			}
		});
    	
    	String itemId=fieldInfo.getFieldName();
    	Object[] ob=new Object[]{commentsField, editBtn,delBtn};
   // 	itemIDMapping.put(itemId, ob);
    	table.addItem(ob,itemId); 
    	
    	
    }
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public BeanFieldGroup<ConfigSectionInfo> getSectionBinder() {
		return sectionBinder;
	}

	public void setSectionBinder(BeanFieldGroup<ConfigSectionInfo> sectionBinder) {
		this.sectionBinder = sectionBinder;
	}

	public PageDisplay getPageOp() {
		return pageOp;
	}

	public void setPageOp(PageDisplay pageOp) {
		this.pageOp = pageOp;
	}
	

}
