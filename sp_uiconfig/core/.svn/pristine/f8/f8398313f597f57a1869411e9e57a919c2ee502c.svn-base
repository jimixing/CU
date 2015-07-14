package com.vaadin.tests.themes.valo;

import java.util.Map;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Tree;

public class SkinboFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3230097978215170223L;
	private String filter;
	private HierarchicalContainer container;
	private Map<String,String> chiParMap;
	
	public SkinboFilter(String filter,Map<String,String> chilrenParentMapping){
		this.filter=filter;
		this.chiParMap=chilrenParentMapping;
	//	this.container=container;
	}
	
	@Override
	public boolean passesFilter(Object itemId, Item item)
			throws UnsupportedOperationException {
	
		if(chiParMap.get((String)itemId)!=null){
			String parent=chiParMap.get((String)itemId);
			if(parent.toLowerCase().contains(filter))return true;
			return false;
		}
		String value=(String)item.getItemProperty(Trees.CAPTION_PROPERTY).getValue();
		if(value.contains(".")&&value.toLowerCase().contains(filter)){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		// TODO Auto-generated method stub
		return false;
	}

}
