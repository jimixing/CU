package com.ebao.gs.pol.prdt.config.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.ebao.gs.pol.prdt.config.CoreTagInfo;
import com.ebao.gs.pol.prdt.config.FieldInfo;
import com.ebao.gs.pol.prdt.config.JDBCDataSourceConnect;
import com.ebao.gs.pol.prdt.config.SkinboInfo;
import com.ebao.gs.pol.prdt.config.service.XmlParser;



public class XmlParserTool implements XmlParser {

	private String fileFullPath;
	private Document document;
	private SAXReader reader;
	private InputStream inputStream;
	private boolean needDeclaration;
	private static StringBuffer strResSb=new StringBuffer();
	private static List<String> strResIdList=new LinkedList<String>();
	private static StringBuffer strskinBoNodeSb=new StringBuffer();
	private static List<Integer> skinboNodeIdList=new LinkedList<Integer>();
	private static StringBuffer strskinFieldNodeSb=new StringBuffer();
	private static List<Integer> skinboFieldNodeIdList=new LinkedList<Integer>();
	private static StringBuffer strskinBoNodeInfoSb=new StringBuffer();
	private static List<Integer> skinboNodeInfoIdList=new LinkedList<Integer>();
	

	/**
	 * init XmlParserImpl class 
	 * elementlist is used to store elements
	 * attributelist is used to store attributes
	 * 
	 */

	public XmlParserTool(String fileFullPath) {
		this.fileFullPath = fileFullPath;
		reader = new SAXReader();
	}

	public XmlParserTool(InputStream inputStream) {
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
					public InputSource resolveEntity(String publicId,String systemId) throws SAXException, IOException {
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
		XmlParserTool xml=new  XmlParserTool("");
		xml.parselUiconfig();
		
	}
	
	
	private void parseTagLib(){
		InputStream is=XmlParserTool.class.getResourceAsStream("facelets.taglib-biz.xml");
		XmlParserTool xml=new XmlParserTool(is);
		try {
			xml.parseXmlFile();
			Element skinboElement=new DefaultElement("tag");
			List<Element> skinboList= xml.getElementByAttr(skinboElement);
			List<CoreTagInfo> skinList=new LinkedList<CoreTagInfo>();
			for(Element ele:skinboList){
				CoreTagInfo tag=new CoreTagInfo();
				String tagName=ele.element("tag-name").getTextTrim();
				tagName=tagName.substring(0, 1).toUpperCase()+tagName.substring(1);
				String tagUrl=ele.element("source").getTextTrim();
				
				tag.setTag_name(tagName);
				tag.setTag_url(tagUrl);	
				skinList.add(tag);
			}
			generateTagId(skinList);
			StringBuffer sb=new StringBuffer();
			generateTagScript(skinList,sb);
			
			String  path="D:\\staticTaglib.sql";
			writeToSQL(path,sb);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
		
		
		
		
	}
	
	
	private void generateTagScript(List<CoreTagInfo> skinList,StringBuffer sb){
		for(CoreTagInfo tagInfo:skinList){
			String template="update T_UICONFIG_SECTION_RELATION set(TAG_ID,TAG_NAME,TAG_URL)="
					+ "(select "+tagInfo.getTag_id()+",'"+tagInfo.getTag_name()+"','"+tagInfo.getTag_url()+"'from  dual) where TAG_ID="+tagInfo.getTag_id()+";\n"
						+"insert into T_UICONFIG_SECTION_RELATION (TAG_ID,TAG_NAME,TAG_URL)"
						+ "select "+ tagInfo.getTag_id()+",'"+tagInfo.getTag_name()+"','"+ tagInfo.getTag_url()+"' from  dual where not exists"
						+"(select * from T_UICONFIG_SECTION_RELATION where TAG_ID="+tagInfo.getTag_id()+");";
			sb.append(template).append("\n").append("\n");
		}
		
		
	}
	
	public void generateTagId(List<CoreTagInfo> skinList){
		for(CoreTagInfo tagInfo:skinList){
			int tagId=getTagId();
			tagInfo.setTag_id(tagId);

		}

		
	}
	
	private void parselUiconfig(){
		
		InputStream is=XmlParserTool.class.getResourceAsStream("uiconfig.xml");
		XmlParserTool xml=new XmlParserTool(is);
		try {
			xml.parseXmlFile();
			Element skinboElement=new DefaultElement("skinBo");
			
						
			List<Element> skinboList= xml.getElementByAttr(skinboElement);
			List<SkinboInfo> skinList=new LinkedList<SkinboInfo>();
			for(Element skinEle:skinboList){
				SkinboInfo skinbo=new SkinboInfo();

				skinbo.setClassName(skinEle.attributeValue("id"));
				List<Element> fieldList= skinEle.elements();
				List<FieldInfo> fieldInfoList=new LinkedList<FieldInfo>();
				List<SkinboInfo> embedSkinList=new LinkedList<SkinboInfo>();
				for(Element fieldEle:fieldList){
					String cate=	fieldEle.attributeValue("category");
					String sig=fieldEle.attributeValue("signature");
					if(cate.equals("Fields for user to input")){
						if(!sig.equals("null")){
							SkinboInfo embedSkinbo=new SkinboInfo();
							String nodeName=fieldEle.attributeValue("name");
							embedSkinbo.setNodeName(nodeName);
							char[] skinArray=nodeName.toCharArray();
							StringBuffer sb1=new StringBuffer();
							for(int i=0;i<skinArray.length;i++){
								if(i==0){
									sb1.append(Character.toUpperCase(skinArray[i]));
									continue;
								}
								sb1.append(skinArray[i]);
							}
							embedSkinbo.setNodeDisplayName(sb1.toString());
							embedSkinbo.setClassName(sig);
							embedSkinList.add(embedSkinbo);
							continue;
						}
						FieldInfo fieldInfo=new FieldInfo();
						String fieldName=fieldEle.attributeValue("name");
						fieldInfo.setFieldName(fieldName);
						char[] fieldArray=fieldName.toCharArray();
						StringBuffer sb=new StringBuffer();
						for(int i=0;i<fieldArray.length;i++){
							if(i==0){
								sb.append(Character.toUpperCase(fieldArray[i]));
								continue;
							}
							sb.append(fieldArray[i]);
						}
						fieldInfo.setFieldDisplayName(sb.toString());
						fieldInfoList.add(fieldInfo);
					}
					
	
				}
				if(fieldInfoList.size()>0){
					skinbo.setFieldList(fieldInfoList);
				}
				if(embedSkinList.size()>0){
					skinbo.setEmbedSkinBo(embedSkinList);
				}
				if(skinbo.getFieldList().size()>0){
					skinList.add(skinbo);
				}
			
			}
			int i=0;
			for(SkinboInfo skin:skinList){
			
				i++;
			}
			
			SkinboInfo rootSkinBo=	xml.generateSkinBoRelation(skinList);
			
			xml.generateSql(rootSkinBo);
			xml.closeDb();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
		
		
		
	}
	
	
	private void generateSql(SkinboInfo rootSkinBo){
		String filePath0="D:\\uiconfigStrResScript.sql";
		String filePath1="D:\\uiconfigSkinNodeScript.sql";
		String filePath2="D:\\uiconfigSkinNodeInfoScript.sql";
		String filePath3="D:\\uiconfigSkinNodeFieldScript.sql";
		String filePath4="D:\\uiconfigSkinNodeDeleteScript.sql";
		
	
		geneateSkinboNodeInfo(rootSkinBo);

		writeToSQL(filePath0,strResSb);
		writeToSQL(filePath1,strskinBoNodeSb);
		writeToSQL(filePath2,strskinBoNodeInfoSb);
		writeToSQL(filePath3,strskinFieldNodeSb);


		StringBuffer sb=new StringBuffer();
		collectDropList(sb);
		writeToSQL(filePath4,sb);
		
		
	}
	public void collectDropList(StringBuffer sb){
		StringBuffer tem=new StringBuffer("delete from t_uic_page_datamodel_node t where t.NODE_ID in(");
		int i=0;
		for(;i<skinboNodeIdList.size()-1;i++){
			tem.append(skinboNodeIdList.get(i)+",");
		}
		tem.append(skinboNodeIdList.get(i)+");").append("\n");
		
		sb.append(tem);
		
		tem.delete(0, tem.length());
		
		tem.append("delete from t_uic_page_datamodel_field t where t.field_id in(");
		
		i=0;
		for(;i<skinboFieldNodeIdList.size()-1;i++){
			tem.append(skinboFieldNodeIdList.get(i)+",");
			if(i%20==0)tem.append("\n");
		}
		tem.append(skinboFieldNodeIdList.get(i)+");").append("\n");
		sb.append(tem);
		tem.delete(0, tem.length());
		
		tem.append("delete from t_uic_page_datamodel_node_info t where t.DATA_MODEL_NODE_INFO_ID in(");
		i=0;
		for(;i<skinboNodeInfoIdList.size()-1;i++){
			tem.append(skinboNodeInfoIdList.get(i)+",");
		}
		tem.append(skinboNodeInfoIdList.get(i)+");").append("\n");
		sb.append(tem);
		tem.delete(0, tem.length());
		
		
		tem.append("delete from t_string_resource t where t.str_id in(");
		i=0;
		for(;i<strResIdList.size()-1;i++){
			tem.append("'"+strResIdList.get(i)+"',");
			if(i%10==0)tem.append("\n");
		}
		tem.append("'"+strResIdList.get(i)+"');").append("\n");
		sb.append(tem);
		
		
		
	}
	
	
	private void writeToSQL(String filePath,StringBuffer sb){
		
		try {
			File out0=new File(filePath);
			if(!out0.exists()){
				out0.createNewFile();
			}else{
				out0.delete();
				out0.createNewFile();	
			}

			FileOutputStream fo=new FileOutputStream(out0);
			byte[] val=sb.toString().getBytes();
			fo.write(val);
			fo.flush();
			fo.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
		}
		
		
		
	}
	
	
	private void geneateSkinboNodeInfo(SkinboInfo SkinBo){
		int skinboNodeId=SkinBo.getNodeID(); 
		generateSkinboNode(SkinBo);
		generateFieldNode(SkinBo);
		for(SkinboInfo skin:SkinBo.getEmbedSkinBo()){
	//		generateSkinboNode(skin,sb);
			
			int embedSkinboID=skin.getNodeID();
			int nodeInfoID=getNodeInfoId();
			String skinboFieldName=skin.getNodeName();
			String skinboDisplayName=skin.getNodeDisplayName();

			String template="update t_uic_page_datamodel_node_info set(DATA_MODEL_NODE_INFO_ID,FROM_NODE_ID,TO_NODE_ID,RELATIONSHIP_TYPE,ALIAS_NAME,DISPLAY_NAME)"
							+"=(select "+nodeInfoID+","+skinboNodeId+","+embedSkinboID+",1,'"+skinboFieldName+"','"+skinboDisplayName+"' from  dual) where DATA_MODEL_NODE_INFO_ID="+nodeInfoID+";"+"\n"
							+"insert into t_uic_page_datamodel_node_info (DATA_MODEL_NODE_INFO_ID,FROM_NODE_ID,TO_NODE_ID,RELATIONSHIP_TYPE,ALIAS_NAME,DISPLAY_NAME)"
							+"select "+nodeInfoID+","+skinboNodeId+","+embedSkinboID+",1,'"+skinboFieldName+"','"+skinboDisplayName+"' from  dual where not exists(select * from t_uic_page_datamodel_node_info where DATA_MODEL_NODE_INFO_ID="+nodeInfoID+");";
			strskinBoNodeInfoSb.append(template).append("\n");
			skinboNodeInfoIdList.add(nodeInfoID);

		
			geneateSkinboNodeInfo(skin);
		
		}
		
		
		
		
	}
	
	
	
	private void generateFieldNode(SkinboInfo SkinBo){
		int skinboNodeId=SkinBo.getNodeID(); 
		
		for(FieldInfo fieldInfo:SkinBo.getFieldList()){
			int fieldId=fieldInfo.getFieldId();
			String fieldName=fieldInfo.getFieldName();
			String fieldDisplay=fieldInfo.getFieldDisplayName();
			String label=getStringResource(fieldDisplay);
			String template="	update t_uic_page_datamodel_field set(FIELD_ID,BUSINESS_FIELD_TYPE,NODE_ID,REF_COMPT_TYPE,SPECIAL_RULE,FIELD_NAME,FIELD_DISPLAY_NAME,"
					       +"TABLE_NAME,LABEL,FOREIGN_KEY,HELP_TEXT,PASSWORD,IME_DISABLED,IS_CORE,DESCRIPTION,ALLOW_DECIMALS,ALLOW_NEGATIVE,OPTIONAL,IS_IDENTITY_FIELD,"
					       +"MULTI_SELECTION,DISPLAY_AS_HARMONY_CALENDAR,DEFAULT_VALUE,IS_AUTO_COMPLETE,BLANK_OPTION_TEXT_PROVIDER,DECIMAL_PRECISION,CREATE_TM,"
					       +"UPDATE_TM,VALIDATE_REQUEST,NO_BLANK_OPTION,CASCADE_P_FIELD_ID,CASCADE_C_FIELD_ID,DISPLAY_TYPE,WHERE_CLAUSE,MAX_LENGTH)"
					       +"=(select "+fieldId+",4,"+skinboNodeId+",null,null,'"+fieldName+"','"+fieldDisplay+"',null,'"+label+"',null,null,null,null,0,null,null,null,null,null,null,null,null,null,null,null,null,"
					       +"null,null,null,null,null,null,null,null from dual) where FIELD_ID="+fieldId+";"+"\n"
						   +"insert into t_uic_page_datamodel_field(FIELD_ID,BUSINESS_FIELD_TYPE,NODE_ID,REF_COMPT_TYPE,SPECIAL_RULE,FIELD_NAME,FIELD_DISPLAY_NAME,"
						   +"TABLE_NAME,LABEL,FOREIGN_KEY,HELP_TEXT,PASSWORD,IME_DISABLED,IS_CORE,DESCRIPTION,ALLOW_DECIMALS,ALLOW_NEGATIVE,OPTIONAL,IS_IDENTITY_FIELD,"
						   +"MULTI_SELECTION,DISPLAY_AS_HARMONY_CALENDAR,DEFAULT_VALUE,IS_AUTO_COMPLETE,BLANK_OPTION_TEXT_PROVIDER,DECIMAL_PRECISION,CREATE_TM,"
						   +"UPDATE_TM,VALIDATE_REQUEST,NO_BLANK_OPTION,CASCADE_P_FIELD_ID,CASCADE_C_FIELD_ID,DISPLAY_TYPE,WHERE_CLAUSE,MAX_LENGTH)"
						   +"select "+fieldId+",4,"+skinboNodeId+",null,null,'"+fieldName+"','"+fieldDisplay+"',null,'"+label+"',null,null,null,null,0,null,null,null,null,null,null,null,null,null,null,null,null,"
					       +"null,null,null,null,null,null,null,null from dual where not exists (select * from  t_uic_page_datamodel_field where FIELD_ID=" +fieldId+");";
			strskinFieldNodeSb.append(template).append("\n");
			skinboFieldNodeIdList.add(fieldId);
		}
		
		
		
	}
	
	
	private void closeDb(){
		JDBCDataSourceConnect.getInstance().closeAll();
	}
		private String getStringResource(String fieldName){
		
			Map<String,Boolean> map= JDBCDataSourceConnect.getInstance().getTStringResource(fieldName);
			String str_id=map.keySet().iterator().next();
//			boolean exist=map.get(str_id);
//			if(!exist){
				String template="insert into t_string_resource(STR_ID,LANG_ID,STR_DATA,ORG_TABLE,UPDATE_TIME)"
					       +"select '"+str_id+"','011','"+fieldName+"','T_MESSAGE',sysdate from dual "
					       +"where not exists(select 1 from t_string_resource where str_id = '"+str_id+"'"
					       +"and lang_id = '011');";
				strResSb.append(template).append("\n");
				strResIdList.add(str_id);
	//		}

			return str_id;

		}
		
		private int getNodeInfoId(){
			
			int nodeid=JDBCDataSourceConnect.getInstance().getMaxDataNodeInfoId();
			
			return nodeid;
		}
	
	
		private int getTagId(){
			
			return JDBCDataSourceConnect.getInstance().getMaxTagId();
			
		}
	
	
	private void generateSkinboNode(SkinboInfo rootSkinBo){
		int skinboNodeId=rootSkinBo.getNodeID();
		String nodeName="EP_"+rootSkinBo.getClassName().substring(rootSkinBo.getClassName().lastIndexOf(".")+1);
		
		String template="update t_uic_page_datamodel_node set(NODE_ID,NODE_NAME,DESCRIPTION,CLASS_NAME,METHOD_CLASS_NAME,"
				+"NEW_METHOD,LOAD_METHOD,LOAD_LIST_METHOD,SAVE_METHOD,DELETE_METHOD,CREATE_TM,UPDATE_TM,PAGE_MODEL_ID)"
				+"=(select "+skinboNodeId+",'"+nodeName+"',null,'"+rootSkinBo.getClassName()+"',null,null,null,null,null,null,sysdate,null,null from dual) where "
				+"NODE_ID="+skinboNodeId+";\n"+ "insert into t_uic_page_datamodel_node (NODE_ID,NODE_NAME,DESCRIPTION,CLASS_NAME,METHOD_CLASS_NAME,"
				+"NEW_METHOD,LOAD_METHOD,LOAD_LIST_METHOD,SAVE_METHOD,DELETE_METHOD,CREATE_TM,UPDATE_TM,PAGE_MODEL_ID)"
				+"select "+skinboNodeId+",'"+nodeName+"',null,'"+rootSkinBo.getClassName()+"',null,null,null,null,null,null,sysdate,null,null from dual "
				+"where not exists(select * from t_uic_page_datamodel_node where NODE_ID="+skinboNodeId+");";
		skinboNodeIdList.add(skinboNodeId);
		strskinBoNodeSb.append(template).append("\n");
		
	}
	


	
	
	private SkinboInfo generateSkinBoRelation(List<SkinboInfo> skinList){
		SkinboInfo skinBoRoot=new SkinboInfo();
		StringBuffer beginSkinNodeId=new StringBuffer("20000000");
	    StringBuffer beginFieldNodeId=new StringBuffer("2000000000");
		for(SkinboInfo skin:skinList){
			if(skin.getClassName().equals("com.ebao.gs.pa.model.Policy")){
				skinBoRoot.setClassName(skin.getClassName());
				int skinvalue=Integer.parseInt(beginSkinNodeId.toString());
				skinBoRoot.setNodeID(skinvalue++);
				beginSkinNodeId.delete(0, beginSkinNodeId.length());
				beginSkinNodeId.append(skinvalue);
				for(FieldInfo fieldInfo:skin.getFieldList()){
					int fieldValue=Integer.parseInt(beginFieldNodeId.toString());
					
					fieldInfo.setFieldId(fieldValue++);
					skinBoRoot.getFieldList().add(fieldInfo);
					beginFieldNodeId.delete(0, beginFieldNodeId.length());
					beginFieldNodeId.append(fieldValue);
				}
				for(SkinboInfo emskin:skin.getEmbedSkinBo()){
					skinBoRoot.getEmbedSkinBo().add(emskin);

				}
				break;
			}
		}

		for(SkinboInfo emskin:skinBoRoot.getEmbedSkinBo()){

			setRelationShip(emskin,skinList,beginFieldNodeId,beginSkinNodeId);
	
		}
		List<SkinboInfo> tem=new LinkedList<SkinboInfo>();
		tem.addAll(skinBoRoot.getEmbedSkinBo());
		for(SkinboInfo emskin:tem){
			if(emskin.getFieldList().size()==0&&emskin.getEmbedSkinBo().size()==0){
				skinBoRoot.getEmbedSkinBo().remove(emskin);
			}
		}
		return skinBoRoot;
	}
	
	private void setRelationShip(SkinboInfo parent,List<SkinboInfo> skinList,StringBuffer beginFieldNodeId,StringBuffer beginSkinNodeId){
		for(SkinboInfo skinbo: skinList){

			if(skinbo.getClassName().equals(parent.getClassName())){
				int skinvalue=Integer.parseInt(beginSkinNodeId.toString());
				parent.setClassName(skinbo.getClassName());
				parent.setNodeID(skinvalue++);
				beginSkinNodeId.delete(0, beginSkinNodeId.length());
				beginSkinNodeId.append(skinvalue);
				
				for(FieldInfo fieldInfo:skinbo.getFieldList()){
					int fieldValue=Integer.parseInt(beginFieldNodeId.toString());
					fieldInfo.setFieldId(fieldValue++);
					parent.getFieldList().add(fieldInfo);
					beginFieldNodeId.delete(0, beginFieldNodeId.length());
					beginFieldNodeId.append(fieldValue);
					
				}
				for(SkinboInfo emskin:skinbo.getEmbedSkinBo()){
					parent.getEmbedSkinBo().add(emskin);
				}	
				break;
			}	
		}
		

		for(SkinboInfo emskin:parent.getEmbedSkinBo()){
		
			setRelationShip(emskin,skinList,beginFieldNodeId,beginSkinNodeId);
			
		}
		List<SkinboInfo> tem=new LinkedList<SkinboInfo>();
		tem.addAll(parent.getEmbedSkinBo());
		for(SkinboInfo emskin:tem){
			if(emskin.getFieldList().size()==0&&emskin.getEmbedSkinBo().size()==0){
				parent.getEmbedSkinBo().remove(emskin);
			}
		}
		
	}
	
	
}
