package com.ebao.gs.sp.uiconfig.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.testng.Assert;

import com.ebao.gs.common.test.GenericTestCase;
import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigPageInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.dao.ConfigSqlExecuteDao;
import com.ebao.gs.pol.prdt.config.dao.impl.ConfigSqlExecuteDaoImpl;
import com.ebao.gs.pol.prdt.config.service.ConfigDynamicFieldService;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigDynamicFieldServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigSectionServiceImpl;

public class SPUIConfigTestCase extends GenericTestCase {
	private ConfigPageInfo pageInfo;
	private ConfigSectionInfo sectionInfo;
	private ConfigFieldInfo fieldInfo;
	private List<ConfigSectionInfo> sectionList=new LinkedList<ConfigSectionInfo>();
	private List<ConfigFieldInfo> fieldInfoList=new LinkedList<ConfigFieldInfo>();
	private ConfigPageService pageService=new ConfigPageServiceImpl();
	private ConfigSectionService sectionService=new ConfigSectionServiceImpl();
	private ConfigDynamicFieldService  fieldService=new    ConfigDynamicFieldServiceImpl(); 
	
	
	@Test
	public void testSuit(){
		try {
			savePageInfo();
			saveSecitionInfo();
			updateSectionInfo();
			saveConfigFieldInfo();
			updateConfigFieldInfo();
			deletePage();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@Test
	public void savePageInfo()throws Exception{

		ConfigPageInfo pageInfo=new ConfigPageInfo();
		String pageTitle="PageUnitTest"; 
		pageInfo.setPageName(pageTitle); 
		String productName="Private Motor Car";
		pageInfo.setProductName(productName);
		String descrption="This is page unit test";
		pageInfo.setPageDescription(descrption);
		String productId=String.valueOf(1750000645);
		pageInfo.setProductId(productId);
		
		pageService.addConfigPage(pageInfo, false, null);
		  
		this.pageInfo=pageService.getConfigPageInfoById(Integer.parseInt(pageInfo.getPageId()));
		Assert.assertEquals(this.pageInfo.getPageName(), pageInfo.getPageName());
		
		
	}
	@Test
	public void saveSecitionInfo()throws Exception{
	
		ConfigSectionInfo sectioInfo=new ConfigSectionInfo();
		String sectionTitle="SectionUnitTest";
		sectioInfo.setSectionTitle(sectionTitle);
		String sectionDescription="This is section unit test";
		sectioInfo.setDescription(sectionDescription);
		String sectionType="Form";
		sectioInfo.setPage(pageInfo);
		sectioInfo.setType(sectionType);
		 
		sectionService.addConfigSection(sectioInfo, false, null);
		this.sectionInfo =sectionService.getSectionInfoBySectionId(Integer.parseInt(sectioInfo.getSectionId()));
		pageInfo.getSectionList().add(sectionInfo);
		sectionInfo.setPage(this.pageInfo);
		Assert.assertEquals(this.sectionInfo.getSectionTitle(), sectioInfo.getSectionTitle());
		
	}
	
	@Test
	public void updateSectionInfo() throws Exception{
		String oldTitle=this.sectionInfo.getSectionTitle();
		String sectionTitle="newSectionTitle";
		this.sectionInfo.setSectionTitle(sectionTitle);
		String sectionType="Table";
		this.sectionInfo.setType(sectionType);
		sectionService.addConfigSection(this.sectionInfo, true, oldTitle);
		ConfigSectionInfo sectioInfo=sectionService.getSectionInfoBySectionId(Integer.parseInt(sectionInfo.getSectionId()));

		Assert.assertEquals(sectioInfo.getSectionTitle(), this.sectionInfo.getSectionTitle());
		Assert.assertEquals(sectioInfo.getType(), this.sectionInfo.getType());
		
	}
	
	
	
	@Test
	public void saveConfigFieldInfo()throws Exception{
		ConfigFieldInfo fieldInfo=new ConfigFieldInfo();
		
		String fieldName="FieldName";
		fieldInfo.setFieldName(fieldName);
	
		fieldInfo.setDisplayLabel("Display Label"+fieldName);
		fieldInfo.setColspan(1);

		fieldInfo.setFieldCode(201303);
		fieldInfo.setReadOnly(false);
		fieldInfo.setRequired(true);
		fieldInfo.setTableName("T_YES_NO");
		fieldInfo.setDefaultValue("N");
		fieldInfo.setDisplayType("CODETABLE");
		fieldInfo.setCodeTabelDisplayType("2");
		
		fieldInfo.setVisible(true);
		fieldInfo.setOrderBy("desc");
		fieldInfo.setSection(sectionInfo);
		fieldService.addDynamicFieldConfig(fieldInfo, 0, false);
		ConfigSectionInfo sectioInfo=sectionService.getSectionInfoBySectionId(Integer.parseInt(sectionInfo.getSectionId()));
		this.fieldInfo=sectioInfo.getFieldList().get(0);
		sectionInfo.getFieldList().add(this.fieldInfo);
		Assert.assertEquals(this.fieldInfo.getFieldName(),fieldInfo.getFieldName());
		
	}
	
	@Test
	public void updateConfigFieldInfo()throws Exception{
		ConfigSectionInfo sectioInfo=sectionService.getSectionInfoBySectionId(Integer.parseInt(sectionInfo.getSectionId()));
		
		ConfigFieldInfo fieldInfo=sectioInfo.getFieldList().get(0);
		fieldInfo.setRequired(false);
		fieldInfo.setReadOnly(true);
		String defaultValue="Y";
		this.sectionInfo.getFieldList().clear();
		fieldInfo.setSection(this.sectionInfo);
		this.sectionInfo.getFieldList().add(fieldInfo);
		fieldInfo.setDefaultValue(defaultValue);
		fieldService.addDynamicFieldConfig(fieldInfo, 0, true);

		ConfigSectionInfo sectionInfo=sectionService.getSectionInfoBySectionId(Integer.parseInt(sectioInfo.getSectionId()));
		this.fieldInfo=sectionInfo.getFieldList().get(0);
		this.sectionInfo.getFieldList().clear();
		this.sectionInfo.getFieldList().add(this.fieldInfo);
		Assert.assertEquals(this.fieldInfo.getDefaultValue(),defaultValue);
		Assert.assertEquals(this.fieldInfo.isReadOnly(),true);

	}
	
	@Test
	public void deletePage()throws Exception{
		
		pageService.delConfigPage(pageInfo);
		ConfigPageInfo pageInfo=pageService.getConfigPageInfoById(Integer.parseInt(this.pageInfo.getPageId()));
		Assert.assertNull(pageInfo);
		
	}
	
	

}
