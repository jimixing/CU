package com.ebao.gs.pol.prdt.config.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ebao.gs.pol.prdt.config.service.XmlParser;



public class XmlParserImpl implements XmlParser {

	private String fileFullPath;
	private Document document;
	private SAXReader reader;
	private InputStream inputStream;
	private boolean needDeclaration;

	/**
	 * init XmlParserImpl class 
	 * elementlist is used to store elements
	 * attributelist is used to store attributes
	 * 
	 */

	public XmlParserImpl(String fileFullPath) {
		this.fileFullPath = fileFullPath;
		reader = new SAXReader();
	}

	public XmlParserImpl(InputStream inputStream) {
//		this.fileFullPath = fileFullPath;
		reader = new SAXReader();
		this.inputStream=inputStream;
	}
	
	/**
	 * Parser the xml file if it exist
	 */
	public void parseXmlFile() throws Exception {
		if (fileFullPath!=null&&!fileFullPath.isEmpty()) {
			try {
				File file = new File(fileFullPath);
				//parse file to document;
				if (file.exists()) {
					reader.setValidation(false);
					reader.setEntityResolver(new EntityResolver() {
						@Override
						public InputSource resolveEntity(String publicId,
								String systemId) throws SAXException, IOException {
							// TODO Auto-generated method stub
							return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));  
						}
					});
					reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
					document = reader.read(file);
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}else{
			try {
				reader.setValidation(false);
				reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				reader.setEntityResolver(new EntityResolver() {
					@Override
					public InputSource resolveEntity(String publicId,
							String systemId) throws SAXException, IOException {
						// TODO Auto-generated method stub
						return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));  
					}
				});
				document = reader.read(inputStream);
				
			} catch (DocumentException e) {
				throw new Exception(e.getMessage());
			}catch(SAXException e){
				throw new Exception(e.getMessage());
			}
			
			
		}
	}

	/**
	 * Create empty xml file if does not exist 
	 * @param rootNodeName  root Node
	 * @param attributeMap
	 */

	public void parseCreatedXmlFile(String rootNodeName, Map<String, String> attributeMap) throws Exception {
		document = createEmptyDocument(rootNodeName, attributeMap);
	}

	/**
	 * Get the specified level element's attribute
	 * 
	 * @param element              --the element parameter
	 * @param element.attribute    --the attribute parameter of element
	 * @return list
	 */

	@SuppressWarnings("unchecked")
	public List<Attribute> getElementAttrByAttr(Element element) {
		List<Attribute> attributelist=new LinkedList<Attribute>();
		Element root = document.getRootElement();
		Iterator<Element> rootIt = root.elementIterator();
		while (rootIt.hasNext()) {
			Element ele = rootIt.next();
			getsubFromElement(ele, element,null,attributelist);
		}
		return attributelist;
	}

	/**
	 * recursive the leaf element
	 * 
	 * @param element
	 */

	@SuppressWarnings("unchecked")
	private void getsubFromElement(Element layer, Element sub,List<Element>elementList,List<Attribute>attributeList) {
		
		if (layer.getName().equals(sub.getName())) {
			if (sub.attributes().isEmpty()) {
				if (elementList!=null) {
					elementList.add(layer);
				} else {
					attributeList.addAll(layer.attributes());
				}

			} else {
				//compare to get element if element has attributes
				Iterator<Attribute> subItAtt = sub.attributeIterator();
				int satisfy = 0;
				while (subItAtt.hasNext()) {
					Attribute itAtt = subItAtt.next();
					Iterator<Attribute> layerItAtt = layer.attributeIterator();
					while (layerItAtt.hasNext()) {
						Attribute layerIt = layerItAtt.next();
						if (itAtt.getName().contains(layerIt.getName())
								&& itAtt.getValue().contains(layerIt.getValue())) {
							satisfy++;
						}
					}

				}
				// find the target and then store into list
				if (satisfy == sub.attributeCount()) {
					if (elementList!=null) {
						elementList.add(layer);
					} else {
						attributeList.addAll(layer.attributes());
					}
				}
			}

		} else {
			//recursive it's children 
			Iterator<Element> eleIt = layer.elementIterator();
			while (eleIt.hasNext()) {
				Element ele = eleIt.next();
				getsubFromElement(ele, sub, elementList,attributeList);
			}

		}

	}

	/**
	 * add element into a special element
	 * Element parent    --parent element
	 * Element child    --child element
	 * boolean doSave   --do save if add the last element
	 * 
	 */
	public void addElement(Element parent, Element child,boolean doSave) throws Exception {
		try {
			parent.add(child);
			if(doSave){
				writeToXml();
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}

	/**
	 * Get the specified level element
	 * 
	 * @param element              --the element parameter
	 * @param element.attribute    --the attribute parameter of element
	 * @return list
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Element> getElementByAttr(Element element) {
		List<Element> elementList=new LinkedList<Element>();
		
		Element root = document.getRootElement();
		if(root.getName().equals(element.getName())){
			elementList.add(root);
			return elementList;
		}
		Iterator<Element> rootIt = root.elementIterator();
		while (rootIt.hasNext()) {
			Element ele = rootIt.next();
			getsubFromElement(ele, element, elementList,null);
		}
		return elementList;
	}

	/**
	 * Create a empty document
	 * 
	 * @param attributeMap --the beans node attribute parameter
	 * 
	 * @return Document
	 */
	public Document createEmptyDocument(String rootNodeName, Map<String, String> attributeMap) throws Exception {
		try {
			document = DocumentHelper.createDocument();
			Element root=null;
			
			if(attributeMap==null||attributeMap.isEmpty()){
				root=document.addElement(rootNodeName);
			}else if(attributeMap.size()>=1) {
				if(attributeMap.keySet().contains("xmlns")){
					root=document.addElement(rootNodeName,attributeMap.get("xmlns"));
					attributeMap.remove("xmlns");
					Iterator<String> keyit = attributeMap.keySet().iterator();
					while(keyit.hasNext()){
						String key=keyit.next();
						root.addAttribute(key, attributeMap.get(key));
					}
				}else{
					root=document.addElement(rootNodeName);
					Iterator<String> keyit = attributeMap.keySet().iterator();
					while(keyit.hasNext()){
						String key=keyit.next();
						root.addAttribute(key, attributeMap.get(key));
					}
					
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}

		return document;
	}

	/**
	 * Return true if successfully delete the required element
	 * Element targetElement   ---parent element
	 * Element Element         ---child element
	 * boolean  doSave			---- save the result	
	 * 
	 * @param layer
	 * @param sub
	 * @return
	 */
	public boolean deleteElement(Element targetElement, Element element,boolean doSave) throws Exception {
		boolean flag = false;
		try {
			flag = deepDeleteElement(targetElement, element);
			if(doSave)
			  writeToXml();
		} catch (Exception e) {
			flag = false;
			throw new Exception(e);
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	private boolean deepDeleteElement(Element targetElement, Element element) throws Exception {
		try {
			if (!targetElement.remove(element)) {
				Iterator<Element> eleIt = targetElement.elementIterator();
				while (eleIt.hasNext()) {
					targetElement = eleIt.next();
					if (deepDeleteElement(targetElement, element)) {
						return true;
					}
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return false;
	}

	/**
	 * get current xmlParser document
	 * 
	 * @return Document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * 
	 * 
	 * @param targetElement		---element need to be updated
	 * @param newElement    	---new element
	 * @throws Exception
	 */
	public void updateAndAddElement(Element targetElement, Element newElement,boolean attributeDel) throws Exception {
		if (!updateElement(targetElement, newElement,true,attributeDel)) {
			addElement(targetElement.getParent(), newElement,true);
		}

	}

	/**
	 * 
	 * Return true if new element update the target element attributes
	 * 
	 * @param target
	 * @param newElement
	 * @param doSave 	---save the result
	 * @param attributeDel ---delete all the target attributes and replace them with new one
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean updateElementAttr(Element targetElement, Element newElement,boolean doSave,boolean attributeDel) throws Exception {
		Iterator<Attribute> newAttributes = newElement.attributeIterator();
		if(targetElement.attributes().size()==0){
			return false;
		}
		
		int i = 0;
		Map<String, String> newVal = new LinkedHashMap<String, String>();
		Map<String, String> addVal = new LinkedHashMap<String, String>();
		try {
			//recursive it's attribute to find target element
			while (newAttributes.hasNext()) {
				Attribute newAtt = newAttributes.next();
				Iterator<Attribute> targetAttributes = targetElement.attributeIterator();
				int findFlag=0;
				while (targetAttributes.hasNext()) {
					Attribute tarAtt = targetAttributes.next();
					if (newAtt.getName().contains(tarAtt.getName())&&newAtt.getValue().contains(tarAtt.getValue())) {
						i++;
						break;
					}else if(tarAtt.getName().contains(newAtt.getName())){
						newVal.put(newAtt.getName(), newAtt.getValue());
						break;
					}else{
						findFlag++;
					}
					if(findFlag==targetElement.attributeCount()){
						addVal.put(newAtt.getName(), newAtt.getValue());
					}
				}
			}
			//update the target and store. 
			
			if (i > 0) {
				//delete target element attribute 
				if(attributeDel){
					int attrCount=targetElement.attributeCount();
					for(int k=0;k<attrCount;){
						targetElement.remove(targetElement.attribute(k));
						attrCount--;
					}
					for(int j=0;j<newElement.attributeCount();j++){
						targetElement.add((Attribute)newElement.attribute(j).clone());
					}
					return true;
				}else{
					// do not delete target element attribute
					Iterator<Attribute> targetAttribute = targetElement.attributeIterator();
					while (targetAttribute.hasNext()) {
						Attribute targetAtt = targetAttribute.next();
						if(newVal.get(targetAtt.getName())!=null){
							targetAtt.setValue(newVal.get(targetAtt.getName()));
						}
						
					}
					Set<String> addValset = addVal.keySet();
					for (String key : addValset) {
						Attribute att = new DefaultAttribute(key, addVal.get(key));
						targetElement.add(att);
					}

					if (doSave)
						writeToXml();
					return true;
				}	
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		

	}

	/**
	 * 
	 * Return true if new element update the target 
	 * 
	 * @param target
	 * @param newElement
	 * @param doSave 	---save the result
	 * @param attributeDel ---delete all the target attributes and replace them with new one
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public boolean updateElement(Element targetElement, Element newElement,boolean doSave,boolean attributeDel) throws Exception {
		// update the element attribute
		if(updateElementAttr(targetElement,newElement,doSave,attributeDel)){
			//update the sub element of targetElement if new element has chilrend
			if(!newElement.elements().isEmpty()){
				List<Element> tarEleList=targetElement.elements();
				List<Element> newEleList=newElement.elements();
				// addEleList is used to store the element which is new for target element
				List<Element> addEleList=new LinkedList<Element>();
				for(Element newEle:newEleList){
					int i=0;
					for(Element tarEle:tarEleList){
						if(updateElementAttr(tarEle,newEle,false,attributeDel)){
							break;
						}else{
							i++;
						}
						if(i==tarEleList.size()){
							addEleList.add(newEle);
						}
					}
					
				}
				for(Element ele:addEleList){
					targetElement.add((Element)ele.clone());
				}
				
			}
			if(doSave)
				writeToXml();
		}else{
			
			throw new Exception("Can not find updated element!");
		}
		
		
		return false;
	}
	/**
	 * Store xml file to storage 
	 */
	public void writeToXml() throws Exception {

		synchronized (document) {
			try {
				XMLWriter writer = null;
				/*String floderPath = fileFullPath.substring(0,fileFullPath.lastIndexOf("\\"));
				String fileName = fileFullPath.substring(fileFullPath.lastIndexOf("\\")+1);
				File floderFile = new File(floderPath);
				if(!floderFile.exists())
					floderFile.mkdirs();
				File file = new File(floderFile,fileName);*/
				File file = new File(fileFullPath);

				if(!file.exists())
					file.createNewFile();
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");
				format.setSuppressDeclaration(needDeclaration);
				writer = new XMLWriter(new FileWriter(file), format);
				writer.write(document);
				writer.close();

			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
	}
	public class NullEntityResolver  implements  EntityResolver {
		private String emptyDtd = "";
        private ByteArrayInputStream bytes = new ByteArrayInputStream(emptyDtd.getBytes());
   
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                      return new InputSource(bytes);
        }
	}
	public boolean isNeedDeclaration() {
		return needDeclaration;
	}

	public void setNeedDeclaration(boolean needDeclaration) {
		this.needDeclaration = needDeclaration;
	}
	
	public class MyVistor extends VisitorSupport{
		public void visit(Element element){
	
		}
	}
	
	public static void  main(String[] args){
		XmlParserImpl xml=new XmlParserImpl("D:\\basicDetails.xhtml");
		try {
			xml.parseXmlFile();
			MyVistor my=xml.new MyVistor();
			xml.getDocument().getRootElement().accept(my);
			
		} catch (Exception e) {

		}
		
		
	}
	
}
