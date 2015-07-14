package com.ebao.gs.pol.prdt.config.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;



public interface XmlParser {
    
	/**
	 * get current xmlParser document
	 * 
	 * @return Document
	 */
	public Document getDocument();
	
	public void parseXmlFile() throws Exception;
	
	public void parseCreatedXmlFile(String nodeName, Map<String, String> attributeMap) throws Exception;
	
	/**
	 * add element into a special element
	 * Element parent    --parent element
	 * Element child    --child element
	 * boolean doSave   --do save as need
	 * 
	 */
	public void addElement(Element parent,Element child,boolean doSave) throws Exception;
	/**
	 * create a empty document
	 * 
	 * @param attributeMap --the beans node attribute parameter
	 * 
	 * @return Document
	 */
	public Document createEmptyDocument(String nodeName,Map<String,String> cMap)throws Exception;
	
	/**
	 * get the specified level element
	 * 
	 * @param element              --the element parameter
	 * @param element.attribute    --the attribute parameter of element
	 * @return list
	 */
	public List<Element> getElementByAttr(Element element);
	
	/**
	 * get the specified level element's attribute
	 * 
	 * @param element              --the element parameter
	 * @param element.attribute    --the attribute parameter of element
	 * @return list
	 */
	public List<Attribute> getElementAttrByAttr(Element element);
	

	/**
	 * 
	 * use newElement update the specified layer element's attribute 
	 * 
	 * @param target
	 * @param newElement
	 * @param doSave 	---save the result
	 * @param attributeDel ---delete all the target attributes and replace them with new one
	 * @return boolean
	 */

	public boolean updateElement(Element targetElement,Element newElement,boolean doSave,boolean attributeDel)throws Exception ;
	
	/**
	 * delete the specified layer element 
	 * 
	 * @param layer    --the element layer
	 * @param element  --the element parameter 
	 * @return boolean
	 */
	public boolean deleteElement(Element targetElement,Element element,boolean doSave)throws Exception;
	
	/**
	 * Store document to storage
	 * @throws Exception
	 */
	public void writeToXml() throws Exception;
	
} 
