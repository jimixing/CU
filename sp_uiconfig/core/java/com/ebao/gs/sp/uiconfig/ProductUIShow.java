package com.ebao.gs.sp.uiconfig;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;
import com.ebao.gs.pol.prdt.config.service.PageNameService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigSectionServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.PageNameServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.Tree.TreeTargetDetails;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ProductUIShow extends VerticalLayout implements View {

	
	private String productName;

	private HierarchicalContainer container;
	private Tree tree;
	private ProductMain pm; 
	private List<ConfigPageInfo> pageInfoList;
	private ConfigPageService pageService=new ConfigPageServiceImpl();
	private ConfigSectionService sectionService=new ConfigSectionServiceImpl();
	private PageNameService pageNameService=new PageNameServiceImpl();
	public ProductUIShow (String productName,ProductMain pm){
		this.productName=productName;
		this.pm=pm;
//		this.pageInfoList=pageInfoList;
		init();
	}
	
	private void init(){
		 setMargin(true);
		 this.setWidth("300px");
	       Label h3 = new Label("Product Information");
	       h3.addStyleName("h3");
	       addComponent(h3);
	       tree = new Tree();
	        tree.setSelectable(true);
	        tree.setDragMode(TreeDragMode.NODE);
	        try {
				pageInfoList=pageService.loadConfigPageList(productName);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				
			}
	        		
	      //  generateProductInfo();
	        container=PageSectionTreeGenerator.getInstance().generateProductInfo(productName);
	        tree.setContainerDataSource(container);
	        addComponent(tree);
	        tree.setItemCaptionPropertyId(PageSectionTreeGenerator.NODE_TITLE);
	        tree.addItemClickListener(new ItemClickListener(){

				@Override
				public void itemClick(ItemClickEvent event) {
					container=(HierarchicalContainer)tree.getContainerDataSource();
					String itemID=(String)event.getItemId();
					if(container.getItem(itemID)!=null){
						Item id=container.getItem(itemID);
						String value=(String)id.getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue();
//						String value="";
//						try {
//							value = pageNameService.findPageNameCodeByDesc(desc);
//						} catch (Exception e) {
//						}
						for(ConfigPageInfo pageInfo:pageInfoList){
							PageDisplay pd=new PageDisplay(productName,pm,tree,itemID,pageInfo,pageInfoList);
							String desc="";
							try {
								desc=pageNameService.findPageNameDescByCode(pageInfo.getPageName());
							} catch (Exception e) {
								
							}
							if(desc!=null&&desc.equals(value)){
							
								if(pm.getComponentCount()<=1){
									pm.addComponent(pd);
								}else{
									Component last=pm.getSecondComponent();
									pm.replaceComponent(last, pd);
								}
								return ;
							}
							List<ConfigSectionInfo> sectionList=pageInfo.getSectionList();
							for(ConfigSectionInfo sectionInfo:sectionList){
								if(sectionInfo.getSectionTitle().equals(value)){
									SectionAddUI sau=new SectionAddUI(sectionInfo,sectionList,tree,itemID,pd,pm,false);
									if(pm.getComponentCount()<=1){
										pm.addComponent(sau);
									}else{
										Component last=pm.getSecondComponent();
										pm.replaceComponent(last, sau);
									}
									return ;
								}

								for(ConfigSectionInfo subSectionInfo:sectionInfo.getSubSectionList()){
									if(showSubSection(value,subSectionInfo,tree,itemID,pd,pm)) return ;
								}

							}							
						}
						
					PageAddUI pageuiAdd = new PageAddUI(productName, pm, tree,pageInfoList);
					if (pm.getComponentCount() <= 1) {
						pm.addComponent(pageuiAdd);
					} else {
						Component last = pm.getSecondComponent();
						pm.replaceComponent(last, pageuiAdd);
					}

					}
				}
			}
	);
	        
	        tree.expandItem(productName);
	        tree.setDropHandler(new DropHandler() {

				@Override
				public void drop(DragAndDropEvent event) {
					Transferable t = event.getTransferable();
			        
			        // Make sure the drag source is the same tree
			        if (t.getSourceComponent() != tree)
			            return;
			        
			        TreeTargetDetails target = (TreeTargetDetails)
			            event.getTargetDetails();
			        Object targetItemId = target.getItemIdOver();
			        Object sourceItemId = t.getData("itemId");
			      
			        if(container.getItem(sourceItemId).getItemProperty(PageSectionTreeGenerator.NODE_TYPE).getValue().equals("PAGE"))return ;
			        VerticalDropLocation location = target.getDropLocation();
			        
			        HierarchicalContainer container = (HierarchicalContainer)
			        tree.getContainerDataSource();
			        if(location == VerticalDropLocation.TOP){
			        	Object parentTargetId = container.getParent(targetItemId);
			        	Object parentSourceId =container.getParent(sourceItemId);
			        	if(parentTargetId==null||parentSourceId==null)return;
			        	if(((String)parentTargetId).equals((String)parentSourceId)){
			        		 container.moveAfterSibling(sourceItemId, targetItemId);
					         container.moveAfterSibling(targetItemId, sourceItemId);
					         updateSection(sourceItemId,targetItemId,parentSourceId,parentTargetId);
					         
			        	}else{
			        		 ConfigPageInfo sourcePageInfo=null;
			        		 ConfigPageInfo targetPageInfo=null;
			        		for(ConfigPageInfo pageInfo:pageInfoList){
//			        			 if(pageInfo.getPageTitle().equals(tree.getItem(parentTargetId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
//			        				 targetPageInfo=pageInfo;
//			        			 }else if(pageInfo.getPageTitle().equals(tree.getItem(parentSourceId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
//			        				 sourcePageInfo=pageInfo;
//			        			 }
			        		}
			        		if(targetPageInfo==null||sourcePageInfo==null)return ;
			        		try {
			        			ConfigSectionInfo delSection=null;
			        			for(ConfigSectionInfo sectionInfo:sourcePageInfo.getSectionList()){
				        			if(sectionInfo.getSectionTitle().equals(tree.getItem(sourceItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
				        				delSection=sectionInfo;
										sectionService.changeSecitonToPage(sectionInfo, Integer.parseInt(targetPageInfo.getPageId()));
										break;
			        			}
			        		}
			        			sourcePageInfo.getSectionList().remove(delSection);	
				        		targetPageInfo.getSectionList().add(delSection);
				        		delSection.setPage(targetPageInfo);
			        		} catch (Exception e) {
								// TODO Auto-generated catch block
			        			Notification.show("Section drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
							}
			        		
			        		  container.setParent(sourceItemId, parentTargetId);
			        		  container.moveAfterSibling(sourceItemId, targetItemId);
						      container.moveAfterSibling(targetItemId, sourceItemId);
						      updateSection(sourceItemId,targetItemId,parentSourceId,parentSourceId);
						      updateSection(sourceItemId,targetItemId,parentTargetId,parentTargetId);
			        		  
			        	}
			        	
			        }else if(location == VerticalDropLocation.BOTTOM){
			        	if(tree.getItem(targetItemId).getItemProperty(PageSectionTreeGenerator.NODE_TYPE).getValue().equals("PAGE")){
			        		return ;
			        	} 
			        	
			        	Object parentTargetId = container.getParent(targetItemId);
			        	Object parentSourceId =container.getParent(sourceItemId);
			        	if(parentTargetId==null||parentSourceId==null)return;
			        	if(((String)parentTargetId).equals((String)parentSourceId)){
			        		 container.moveAfterSibling(sourceItemId, targetItemId);
			        		 updateSection(sourceItemId,targetItemId,parentSourceId,parentTargetId);
			        	}else{
			        		 ConfigPageInfo sourcePageInfo=null;
			        		 ConfigPageInfo targetPageInfo=null;
			        		for(ConfigPageInfo pageInfo:pageInfoList){
//			        			 if(pageInfo.getPageTitle().equals(tree.getItem(parentTargetId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
//			        				 targetPageInfo=pageInfo;
//			        			 }else if(pageInfo.getPageTitle().equals(tree.getItem(parentSourceId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
//			        				 sourcePageInfo=pageInfo;
//			        			 }
			        		}
			        		if(targetPageInfo==null||sourcePageInfo==null)return ;
			        		try {
			        			ConfigSectionInfo delSection=null;
			        			for(ConfigSectionInfo sectionInfo:sourcePageInfo.getSectionList()){
				        			if(sectionInfo.getSectionTitle().equals(tree.getItem(sourceItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
				        				delSection=sectionInfo;
				        				
										sectionService.changeSecitonToPage(sectionInfo, Integer.parseInt(targetPageInfo.getPageId()));
										break;
			        			}
			        		}
			        		sourcePageInfo.getSectionList().remove(delSection);	
				        	targetPageInfo.getSectionList().add(delSection);
				        	delSection.setPage(targetPageInfo);
			        		} catch (Exception e) {
								// TODO Auto-generated catch block
			        			Notification.show("section drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
							}
			        		Object parentId = container.getParent(targetItemId);
				            container.setParent(sourceItemId, parentId);
				            container.moveAfterSibling(sourceItemId, targetItemId);
				            updateSection(sourceItemId,targetItemId,parentSourceId,parentSourceId);
						    updateSection(sourceItemId,targetItemId,parentTargetId,parentTargetId);
			        	}
			        	
			        	
			        	
			        }else if(location == VerticalDropLocation.MIDDLE){
			        	Object parentTargetId = container.getItem(targetItemId);
			        	Object parentSourceId =container.getParent(sourceItemId);
			        	if(parentTargetId==null||parentSourceId==null)return;
			        	if(container.getItem(targetItemId).getItemProperty(PageSectionTreeGenerator.NODE_TYPE).getValue().equals("PAGE")){

				        		 ConfigPageInfo sourcePageInfo=null;
				        		 ConfigPageInfo targetPageInfo=null;
				        		for(ConfigPageInfo pageInfo:pageInfoList){
//				        			 if(pageInfo.getPageTitle().equals(tree.getItem(targetItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
//				        				 targetPageInfo=pageInfo;
//				        			 }else if(pageInfo.getPageTitle().equals(tree.getItem(parentSourceId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
//				        				 sourcePageInfo=pageInfo;
//				        			 }
				        		}
				        		if(targetPageInfo==null||sourcePageInfo==null)return ;
				        		try {
				        			ConfigSectionInfo delSection=null;
				        			for(ConfigSectionInfo sectionInfo:sourcePageInfo.getSectionList()){
					        			if(sectionInfo.getSectionTitle().equals(tree.getItem(sourceItemId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue())){
					        				delSection=sectionInfo;
											sectionService.changeSecitonToPage(sectionInfo, Integer.parseInt(targetPageInfo.getPageId()));
											break;
				        			}
				        		} 
				        			sourcePageInfo.getSectionList().remove(delSection);	
					        		targetPageInfo.getSectionList().add(delSection);
					        		tree.setParent(sourceItemId, targetItemId); 
					        		delSection.setPage(targetPageInfo);
					        		updateSection(sourceItemId,targetItemId,parentSourceId,parentSourceId);
					        		String targetidStr=(String)(((Item)parentTargetId).getItemProperty(PageSectionTreeGenerator.NODE_TITLE).getValue());
									updateSection(sourceItemId,targetItemId,targetidStr,targetidStr);
					        		
				        		} catch (Exception e) {
									// TODO Auto-generated catch block
				        			Notification.show("section drag&drop error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
								}
				        	}

			        }

				} 

				
				@Override
				public AcceptCriterion getAcceptCriterion() {
					// TODO Auto-generated method stub 
					return AcceptAll.get();

				}}); 
	        PageAddUI pageuiAdd = new PageAddUI(productName, pm, tree,pageInfoList);
			
			pm.setSecondComponent(pageuiAdd);//.addComponent(pageuiAdd);
	}
	
	public boolean showSubSection(String target,ConfigSectionInfo sectionInfo,Tree tree,String itemId,PageDisplay pd,ProductMain pm){
		if(sectionInfo.getSectionTitle().equals(target)){
			SectionAddUI sau=new SectionAddUI(sectionInfo,sectionInfo.getParentSection().getSubSectionList(),tree,itemId,pd,pm,true);
			if(pm.getComponentCount()<=1){
				pm.addComponent(sau);
			}else{
				Component last=pm.getSecondComponent();
				pm.replaceComponent(last, sau);
			}
			return true;
		}
		
		for(ConfigSectionInfo subSectionInfo:sectionInfo.getSubSectionList()){
			return showSubSection(target,subSectionInfo,tree,itemId,pd,pm);
		}
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public void updateSection(Object souceItemId,Object targetItemId,Object parentSourceItemId,Object parentTargetItemId){
		String parentSourceName=parentSourceItemId.toString();
		String[] parentSourceList= parentSourceName.split("-");
		String pageName=parentSourceName;
	
		boolean isNest=false;
		if(parentSourceList.length>1){
			pageName=parentSourceList[0];
			isNest=true;
		}
		

			ConfigPageInfo targetPage=null;
			for(ConfigPageInfo pageInfo:pageInfoList){
				if(pageInfo.getPageName().equals(pageName)){
					targetPage=pageInfo;
					 Collection<Object> childrenList=(Collection<Object>)container.getChildren(parentSourceName);
					 Object[] obs=childrenList.toArray();
					 if(!isNest){
							for(ConfigSectionInfo sectionInfo:pageInfo.getSectionList()){
								for(int i=0;i<obs.length;i++){
									if(sectionInfo.getSectionTitle().equals(obs[i].toString().split("-")[1])){
										sectionInfo.setSectionSequence(i);
										break;
									}
									
								}
							}
							try {
								for(ConfigSectionInfo section: targetPage.getSectionList()){
									sectionService.addConfigSection(section, true, null);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								
							}
					 }else{
						 for(ConfigSectionInfo sectionInfo:pageInfo.getSectionList()){
							 if(sectionInfo.getSectionTitle().equals(parentSourceList[1])){
								 for(ConfigSectionInfo subsectionInfo:sectionInfo.getSubSectionList()){
										for(int i=0;i<obs.length;i++){
											if(subsectionInfo.getSectionTitle().equals(obs[i].toString().split("-")[1])){
												subsectionInfo.setSectionSequence(i);
												break;
											}
											
										}
									}
								 try {
										for(ConfigSectionInfo section: sectionInfo.getSubSectionList()){
											sectionService.addConfigSection(section, true, null);
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										
									}
								 break;
							 }
							 
							 
						 }
						 
					 }

			
					
				}
				
			}
		
	}
	
//	@SuppressWarnings("unchecked")
//	private HierarchicalContainer generateProductInfo(){
//
//		  container=new HierarchicalContainer();
//		  container.addContainerProperty(NODE_TITLE, String.class, null);
//		  container.addContainerProperty(NODE_TYPE, String.class, null);
//		
//		  final Item product = container.addItem(productName);
//		  product.getItemProperty(NODE_TITLE).setValue(productName);
//		  container.setChildrenAllowed(productName, true);
//		  try {
//			  pageInfoList= pageService.loadConfigPageList(productName);
//			
//			for(ConfigPageInfo pageInfo:pageInfoList){
//				String pageId=pageInfo.getPageTitle();
//				Item pageItem = container.addItem(pageId);
//				pageItem.getItemProperty(NODE_TITLE).setValue(pageId);
//				pageItem.getItemProperty(NODE_TYPE).setValue("PAGE");
//				 container.setChildrenAllowed(pageId, true);
//				  ((Hierarchical) container).setParent(pageId,productName);
//				List<ConfigSectionInfo> sectionList=pageInfo.getSectionList();
//				for(ConfigSectionInfo sectionInfo:sectionList){
//					String sectionId=sectionInfo.getSectionTitle();
//					String sectionItemId=pageId+"-"+sectionId;
//					Item sectionItem=container.addItem(sectionItemId);
//					sectionItem.getItemProperty(NODE_TITLE).setValue(sectionId);
//					sectionItem.getItemProperty(NODE_TYPE).setValue("SECTION");
//					 container.setChildrenAllowed(sectionItemId, false);
//					((Hierarchical) container).setParent(sectionItemId,pageId);
//					if(sectionInfo.getSubSectionList().size()>0){
//						container.setChildrenAllowed(sectionItemId, true);
//						loadSubSection(sectionInfo,sectionItemId);
//				    }
//				}
//			
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Notification.show("product page information error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
//		}
//		return container;
//		
//	}
	
//	@SuppressWarnings({ "unchecked" })
//	private void loadSubSection(ConfigSectionInfo sectionInfo,String parentItemId){
//		String pageId=sectionInfo.getPage().getPageTitle();
//		for(ConfigSectionInfo subSection:sectionInfo.getSubSectionList()){
//			String sectionId=subSection.getSectionTitle();
//			String sectionItemId=pageId+"-"+sectionId;
//			Item sectionItem=container.addItem(sectionItemId);
//			sectionItem.getItemProperty(NODE_TITLE).setValue(sectionId);
//			sectionItem.getItemProperty(NODE_TYPE).setValue("SECTION");
//			 container.setChildrenAllowed(sectionItemId, false);
//			  ((Hierarchical) container).setParent(sectionItemId,parentItemId);
//			if(subSection.getSubSectionList().size()>0){
//				 container.setChildrenAllowed(sectionItemId, true);
//				 loadSubSection(subSection,sectionItemId);
//			}
//			
//		}
//		
//	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public List<ConfigPageInfo> getPageInfoList() {
		return pageInfoList;
	}

	public void setPageInfoList(List<ConfigPageInfo> pageInfoList) {
		this.pageInfoList = pageInfoList;
	}

}
