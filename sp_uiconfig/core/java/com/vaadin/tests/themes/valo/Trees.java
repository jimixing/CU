/*
 * Copyright 2000-2013 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.themes.valo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import com.ebao.gs.pol.config.datamodel.DynamicFieldConfigInfo;
import com.ebao.gs.pol.config.datamodel.PageDataModel;
import com.ebao.gs.pol.config.datamodel.PageDataModelField;
import com.ebao.gs.pol.config.datamodel.PageDataModelNode;
import com.ebao.gs.pol.config.datamodel.PageDataModelNodeInfo;
import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.SkinboInfo;
import com.ebao.gs.pol.prdt.config.service.PageDataModelService;
import com.ebao.gs.pol.prdt.config.service.impl.PageDataModelServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.XmlParserImpl;
import com.ebao.gs.sp.uiconfig.ComponentFactory;
import com.ebao.gs.sp.uiconfig.SectionAddUI;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.CollapseEvent;
import com.vaadin.ui.Tree.CollapseListener;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.Tree.ExpandListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.VerticalLayout;

public class Trees extends VerticalLayout implements View {
    static final String CAPTION_PROPERTY = "skinbo_title";
    static final String NODE_ID="node_id";
    static final String IS_ROOT="is_root";
    static final String HAS_CHILDREN="has_chilren";
    static final String DESCRIPTION_PROPERTY = "description";
    static final String ICON_PROPERTY = "icon";
    static final String INDEX_PROPERTY = "index";
    static final String TABLE_NAME="table_name";
    static final String TABLE_CLAUSE="where_clause";
    static final String IS_DYNAMIC="is_dynamic";
    private HierarchicalContainer container;
    private HorizontalSplitPanel splitPanel;
	private ConfigSectionInfo sectionInfo;
	private Window fieldWindow;
	private VerticalLayout verticalLayout_4;
	private ConfigFieldInfo fieldInfo;
	private Tree tree;
	private Table table;
	private String sectionItemId;
	private String pageItemId;
	private String variable;
	private String skinBoclassName;
	private SectionAddUI sectionUI;
	private HashMap<String,String> chiParMap=new HashMap<String,String>();
	private PageDataModelService dataModelService=new PageDataModelServiceImpl();
	
	public Trees(final HorizontalSplitPanel splitPanel,final ConfigSectionInfo sectionInfo,
			final ConfigFieldInfo fieldInfo,
    		final Window fieldWindow,final Table table,boolean isEdit,
    		SectionAddUI sectionUI,String pageItemId,String sectionItemId,
    		String variable,String skinBoclassName) {
    	this.sectionInfo=sectionInfo;
    	this.fieldInfo=fieldInfo;
    	this.fieldWindow=fieldWindow;
    	this.verticalLayout_4=verticalLayout_4;
    	this.table=table;
		this.splitPanel=splitPanel;
		this.sectionUI=sectionUI;
		this.sectionItemId=sectionItemId;
    	this.pageItemId=pageItemId;
    	this.variable=variable;
    	this.skinBoclassName=skinBoclassName;
        setMargin(true);
        init();


   }

	private void init(){
        Label h1 = new Label("Data Model");
        h1.addStyleName("h1");
        addComponent(h1);

        
        HorizontalLayout searchPanel = new HorizontalLayout();
        final TextField searchField=new TextField();
        searchPanel.addComponent(searchField);
        Button searchBtn=new NativeButton("Search");
        
        searchBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String keyWord=searchField.getValue();

				((HierarchicalContainer)tree.getContainerDataSource()).removeAllContainerFilters();
				if(!keyWord.isEmpty())
				((HierarchicalContainer)tree.getContainerDataSource()).addContainerFilter(new SkinboFilter(keyWord,chiParMap));

				
			}
		});
        searchPanel.addComponent(searchBtn);
   
        addComponent(searchPanel);
        
        
        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        addComponent(row);

     tree = new Tree();
        tree.setSelectable(true);
        tree.setMultiSelect(true);
         container = generateContainer();

        
        tree.setContainerDataSource(container);
        tree.setDragMode(TreeDragMode.NODE);
        row.addComponent(tree);
       tree.setItemCaptionPropertyId(CAPTION_PROPERTY);
  //      tree.setItemIconPropertyId(ICON_PROPERTY);
  //      tree.expandItem(container.getItemIds().iterator().next());
       tree.addExpandListener(new ExpandListener(){

		@SuppressWarnings("unchecked")
		@Override
		public void nodeExpand(ExpandEvent event) {
			String itemID=(String)event.getItemId();
			try { 
			if(container.getItem(itemID)!=null){
				Item item=container.getItem(itemID);
				if(container.hasChildren(itemID))return ;
				if(item.getItemProperty(IS_DYNAMIC).getValue().equals("false")){
					if(item.getItemProperty(IS_ROOT).getValue().equals("true")){
						int nodeId=(Integer)item.getItemProperty(NODE_ID).getValue();
						
						List<PageDataModelNode> rootChildren=	dataModelService.getRootChildren(nodeId);
						for(PageDataModelNode node:rootChildren){
							String skinboName=node.getClassName().substring(node.getClassName().lastIndexOf(".")+1);
							String itemId=itemID+"_"+skinboName;
				        	Item rootchildItem = container.addItem(itemId);
				        	container.setChildrenAllowed(itemId, true);
				        	rootchildItem.getItemProperty(CAPTION_PROPERTY).setValue(skinboName);
				        	rootchildItem.getItemProperty(IS_ROOT).setValue("false");
				        	rootchildItem.getItemProperty(NODE_ID).setValue(node.getNodeId());
				        	rootchildItem.getItemProperty(HAS_CHILDREN).setValue("true");
				        	rootchildItem.getItemProperty(IS_DYNAMIC).setValue("false");
				            ((Hierarchical) container).setParent(itemId,itemID);
						}
						
						
					}else{
						int nodeId=(Integer)item.getItemProperty(NODE_ID).getValue();
						if(item.getItemProperty(HAS_CHILDREN).getValue().equals("true")){
							List<PageDataModelField> filedList=	dataModelService.getSkinBOFieldsBySkinID(nodeId);
							for(PageDataModelField node:filedList){
								String fieldName=node.getFieldDisplayName();
							     String itemId=itemID+"_"+fieldName;
					        	Item fieldItem = container.addItem(itemId);
					        	container.setChildrenAllowed(itemId, false);
					        	fieldItem.getItemProperty(CAPTION_PROPERTY).setValue(fieldName);
					        	fieldItem.getItemProperty(IS_ROOT).setValue("false");
					        	fieldItem.getItemProperty(NODE_ID).setValue(node.getFieldId());
					        	fieldItem.getItemProperty(HAS_CHILDREN).setValue("false");
					        	fieldItem.getItemProperty(IS_DYNAMIC).setValue("false");
					        	fieldItem.getItemProperty(TABLE_NAME).setValue(node.getTableName());
					        	fieldItem.getItemProperty(TABLE_CLAUSE).setValue(node.getWhereClause());
					            ((Hierarchical) container).setParent(itemId,itemID);
							} 
							
							List<PageDataModelNodeInfo> skinboNodeList=	dataModelService.getSkinboBySkinNodeID(nodeId);
							for(PageDataModelNodeInfo node:skinboNodeList){
								String skinboName=node.getDisplayName();
								String itemId=itemID+"_"+skinboName;
					        	Item fieldItem = container.addItem(itemId);
					        	container.setChildrenAllowed(itemId, true);
					        	fieldItem.getItemProperty(CAPTION_PROPERTY).setValue(skinboName);
					        	fieldItem.getItemProperty(IS_ROOT).setValue("false");
					        	fieldItem.getItemProperty(NODE_ID).setValue(node.getToNode());
					        	fieldItem.getItemProperty(HAS_CHILDREN).setValue("true"); 
					        	fieldItem.getItemProperty(IS_DYNAMIC).setValue("false");
					            ((Hierarchical) container).setParent(itemId,itemID);
							}
						}
						
					}
					
				}				
			
			}
			} catch (Exception e) {
			
				Notification.show("get field information error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
			}
			
		}});
       
       
        tree.addItemClickListener(new ItemClickListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void itemClick(ItemClickEvent event) {
				String itemID=(String)event.getItemId();
				Item item=container.getItem(itemID);
				if(splitPanel.getSecondComponent()!=null){
					splitPanel.removeComponent(splitPanel.getSecondComponent());
				}
				
				if(item.getItemProperty(HAS_CHILDREN).getValue().equals("true")){
					return ; 
				}

				String field=(String)item.getItemProperty(CAPTION_PROPERTY).getValue();
				if(item.getItemProperty(IS_DYNAMIC).getValue().equals("false")){
					int fieldId=(Integer)item.getItemProperty(NODE_ID).getValue();
					fieldInfo.setFieldCode(fieldId);
					fieldInfo.setTableName((String)item.getItemProperty(TABLE_NAME).getValue());
					fieldInfo.setWhereClause((String)item.getItemProperty(TABLE_CLAUSE).getValue());
				}else{
					int fieldId=(Integer)item.getItemProperty(NODE_ID).getValue();
					fieldInfo.setFieldCode(fieldId);
					fieldInfo.setDynamic(true);
				}
				
				fieldInfo.setFieldName(field);
				if (splitPanel.getSecondComponent() != null) {

					splitPanel.replaceComponent(
							splitPanel.getSecondComponent(), new FormTab(
									sectionInfo, fieldInfo, fieldWindow,
									table, field, sectionUI,tree,sectionItemId,pageItemId,variable,skinBoclassName));
				} else {
					splitPanel.addComponent(new FormTab(sectionInfo, fieldInfo,
							fieldWindow, table, field,sectionUI,tree,sectionItemId,pageItemId,variable,skinBoclassName));

				}
		
			}

        
        });
	}
	
	
	
    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
    
    @SuppressWarnings("unchecked")
	public  HierarchicalContainer generateContainer() {
        container =new HierarchicalContainer();

        container.addContainerProperty(CAPTION_PROPERTY, String.class, null);
        container.addContainerProperty(ICON_PROPERTY, Resource.class, null);
        container.addContainerProperty(IS_ROOT, String.class, null);
        container.addContainerProperty(NODE_ID, Integer.class, null);
        container.addContainerProperty(HAS_CHILDREN, String.class, null);
        container.addContainerProperty(IS_DYNAMIC, String.class, null);
        
        container.addContainerProperty(TABLE_NAME, String.class, null);
        container.addContainerProperty(TABLE_CLAUSE, String.class, null);
        
        List<PageDataModel> allSkin;
        List<DynamicFieldConfigInfo> dynamicField;
        
		try {
//			if(isEdit){
//				SkinboInfo skinInfo=new SkinboInfo();
//				skinInfo.setSkinboName(fieldInfo.getSkinboName());
//				skinInfo.getFieldList().add(fieldInfo.getFieldName());
//				allSkin=new LinkedList<SkinboInfo>();
//				allSkin.add(skinInfo);
//			 
//			}else{
				allSkin = getAllSkinInfo();
//			}
				dynamicField=getAllDynamicFiled();
			 for(PageDataModel skinbo:allSkin){
				 if(!skinbo.getDataModelName().equals("SP_BOP_PolicyForm")){
					 continue;
				 }
		        	String itemId=skinbo.getDataModelName();
		        	Item item = container.addItem(itemId);
		        	container.setChildrenAllowed(itemId, true);
		            item.getItemProperty(CAPTION_PROPERTY).setValue(itemId);
		            item.getItemProperty(IS_ROOT).setValue("true");
		            item.getItemProperty(NODE_ID).setValue(skinbo.getDataModelId());
		            item.getItemProperty(HAS_CHILDREN).setValue("true");
		            item.getItemProperty(IS_DYNAMIC).setValue("false");

		        }
			 addDynamicFieldCategorys();
			 for(DynamicFieldConfigInfo dynamicFieldInfo:dynamicField){
				 String parentItemId=dynamicFieldInfo.getConfigLevel();
				 String fieldItemId=dynamicFieldInfo.getFieldName();
					Item fieldItem = container.addItem(fieldItemId);
			    	container.setChildrenAllowed(fieldItemId, false);
			    	fieldItem.getItemProperty(CAPTION_PROPERTY).setValue(fieldItemId);
			    	fieldItem.getItemProperty(IS_ROOT).setValue("false");
			    	fieldItem.getItemProperty(HAS_CHILDREN).setValue("false");
			    	fieldItem.getItemProperty(NODE_ID).setValue(dynamicFieldInfo.getFieldId());
			    	fieldItem.getItemProperty(IS_DYNAMIC).setValue("true");
			    	((Hierarchical) container).setParent(fieldItemId,parentItemId);
 
			 }

	        return container;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Notification.show("skinbo getting error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
       return null;

       

       
    }
    @SuppressWarnings("unchecked")
	public void addDynamicFieldCategorys(){
    	String dynamicRootId="Dynamic Fields";
    	Item item = container.addItem(dynamicRootId);
    	container.setChildrenAllowed(dynamicRootId, true);
    	 item.getItemProperty(CAPTION_PROPERTY).setValue(dynamicRootId);
         item.getItemProperty(IS_ROOT).setValue("true");
         item.getItemProperty(HAS_CHILDREN).setValue("true");
         item.getItemProperty(IS_DYNAMIC).setValue("true");
         
         
         String dynamicPolicyLevel="Policy Information";
         Item PolicyItem = container.addItem(dynamicPolicyLevel);
         PolicyItem.getItemProperty(CAPTION_PROPERTY).setValue(dynamicPolicyLevel);
         PolicyItem.getItemProperty(IS_ROOT).setValue("false");
         PolicyItem.getItemProperty(HAS_CHILDREN).setValue("true");
         PolicyItem.getItemProperty(IS_DYNAMIC).setValue("true");
         ((Hierarchical) container).setParent(dynamicPolicyLevel,dynamicRootId);
         
         
         String dynamicInsuredLevel="Insured Information";
         Item InsuredItem = container.addItem(dynamicInsuredLevel);
         InsuredItem.getItemProperty(CAPTION_PROPERTY).setValue(dynamicInsuredLevel);
         InsuredItem.getItemProperty(IS_ROOT).setValue("false");
         InsuredItem.getItemProperty(HAS_CHILDREN).setValue("true");
         InsuredItem.getItemProperty(IS_DYNAMIC).setValue("true");
         ((Hierarchical) container).setParent(dynamicInsuredLevel,dynamicRootId);
         
         String dynamicCTLevel="CT Information";
         Item ctItem = container.addItem(dynamicCTLevel);
         ctItem.getItemProperty(CAPTION_PROPERTY).setValue(dynamicCTLevel);
         ctItem.getItemProperty(IS_ROOT).setValue("false");
         ctItem.getItemProperty(HAS_CHILDREN).setValue("true");
         ctItem.getItemProperty(IS_DYNAMIC).setValue("true");
         ((Hierarchical) container).setParent(dynamicCTLevel,dynamicRootId);
         
         
         String dynamicBenifitLevel="Benefit Information";
         Item BenifitItem = container.addItem(dynamicBenifitLevel);
         BenifitItem.getItemProperty(CAPTION_PROPERTY).setValue(dynamicBenifitLevel);
         BenifitItem.getItemProperty(IS_ROOT).setValue("false");
         BenifitItem.getItemProperty(HAS_CHILDREN).setValue("true");
         BenifitItem.getItemProperty(IS_DYNAMIC).setValue("true");
         ((Hierarchical) container).setParent(dynamicBenifitLevel,dynamicRootId);
         
    	
    }
    
    
    public List<DynamicFieldConfigInfo> getAllDynamicFiled()throws Exception{
    	if(sectionInfo.getParentSection()!=null){
    		return dataModelService.getDynamicFieldInfo(Integer.parseInt(sectionInfo.getParentSection().getPage().getProductId()),null);
    	}else{ 
    		return dataModelService.getDynamicFieldInfo(Integer.parseInt(sectionInfo.getPage().getProductId()),null);
    	}
    	
    	
    }
    
    
    public List<PageDataModel> getAllSkinInfo()throws Exception{
    	List<PageDataModel> pageDataModel=dataModelService.getRootSkinBO();
    	
    	
    	return pageDataModel;

    	
    	
    }
    

}
