package com.ebao.gs.sp.uiconfig;

import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigUIUtilService;
import com.ebao.gs.pol.prdt.config.service.PageNameService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigUIUtilServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.PageNameServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Notification;

public class PageSectionTreeGenerator {
	public static final String PRODUCT_TITLE="productTitle";
	public static final String PAGE_TITLE="pageTitle";
	public static final String SECTION_TITLE="sectionTitle";
	public static final String NODE_TITLE="title";
	public static final String NODE_TYPE="node_type";
	private ConfigPageService pageService=new ConfigPageServiceImpl();
	private PageNameService pageNameService=new PageNameServiceImpl();
	
	private static PageSectionTreeGenerator generator;
	
	public static PageSectionTreeGenerator getInstance(){
		if(generator==null){
			generator=new PageSectionTreeGenerator();
		}
		return generator;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public HierarchicalContainer generateProductInfo(String productName){

		HierarchicalContainer  container=new HierarchicalContainer();
		  container.addContainerProperty(NODE_TITLE, String.class, null);
		  container.addContainerProperty(NODE_TYPE, String.class, null);
		
		  final Item product = container.addItem(productName);
		  product.getItemProperty(NODE_TITLE).setValue(productName);
		  container.setChildrenAllowed(productName, true);
		  try {
			  List<ConfigPageInfo> pageInfoList= pageService.loadConfigPageList(productName);
			
			for(ConfigPageInfo pageInfo:pageInfoList){
				String pageId=pageInfo.getPageName();
				Item pageItem = container.addItem(pageId);
				String pageName=pageNameService.findPageNameDescByCode(pageId);
				pageItem.getItemProperty(NODE_TITLE).setValue(pageName);
				pageItem.getItemProperty(NODE_TYPE).setValue("PAGE");
				 container.setChildrenAllowed(pageId, true);
				  ((Hierarchical) container).setParent(pageId,productName);
				List<ConfigSectionInfo> sectionList=pageInfo.getSectionList();
				for(ConfigSectionInfo sectionInfo:sectionList){
					String sectionId=sectionInfo.getSectionTitle();
					String sectionItemId=pageId+"-"+sectionId;
					Item sectionItem=container.addItem(sectionItemId);
					sectionItem.getItemProperty(NODE_TITLE).setValue(sectionId);
					sectionItem.getItemProperty(NODE_TYPE).setValue("SECTION");
					 container.setChildrenAllowed(sectionItemId, false);
					((Hierarchical) container).setParent(sectionItemId,pageId);
					if(sectionInfo.getSubSectionList().size()>0){
						container.setChildrenAllowed(sectionItemId, true);
						loadSubSection(sectionInfo,sectionItemId,container);
				    }
				}
			
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Notification.show("product page information error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return container;
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private void loadSubSection(ConfigSectionInfo sectionInfo,String parentItemId,	HierarchicalContainer  container){
		String pageId=sectionInfo.getPage().getPageName();
		for(ConfigSectionInfo subSection:sectionInfo.getSubSectionList()){
			String sectionId=subSection.getSectionTitle();
			String sectionItemId=pageId+"-"+sectionId;
			Item sectionItem=container.addItem(sectionItemId);
			sectionItem.getItemProperty(NODE_TITLE).setValue(sectionId);
			sectionItem.getItemProperty(NODE_TYPE).setValue("SECTION");
			 container.setChildrenAllowed(sectionItemId, false);
			  ((Hierarchical) container).setParent(sectionItemId,parentItemId);
			if(subSection.getSubSectionList().size()>0){
				 container.setChildrenAllowed(sectionItemId, true);
				 loadSubSection(subSection,sectionItemId,container);
			}
			
		}
		
	}
	

}
