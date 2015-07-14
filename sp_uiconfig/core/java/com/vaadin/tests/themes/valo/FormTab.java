package com.vaadin.tests.themes.valo;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.ebao.gs.pol.config.datamodel.DynamicFieldConfigInfo;
import com.ebao.gs.pol.prdt.config.ConfigFieldInfo;
import com.ebao.gs.pol.prdt.config.ConfigSectionInfo;
import com.ebao.gs.pol.prdt.config.DynamicMappingCode;
import com.ebao.gs.pol.prdt.config.service.ConfigDynamicFieldService;
import com.ebao.gs.pol.prdt.config.service.ConfigPageService;
import com.ebao.gs.pol.prdt.config.service.ConfigSectionService;
import com.ebao.gs.pol.prdt.config.service.ConfigUIUtilService;
import com.ebao.gs.pol.prdt.config.service.PageDataModelService;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigDynamicFieldServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigPageServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigSectionServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.ConfigUIUtilServiceImpl;
import com.ebao.gs.pol.prdt.config.service.impl.PageDataModelServiceImpl;
import com.ebao.gs.sp.uiconfig.ComponentFactory;
import com.ebao.gs.sp.uiconfig.ProductUIShow;
import com.ebao.gs.sp.uiconfig.SectionAddUI;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class FormTab extends VerticalLayout implements View {

	private ConfigSectionInfo sectionInfo;
	private TextField colspan;
	private Window fieldWindow;
	private VerticalLayout verticalLayout_4;
	private TextField skinboNameTextField;
	private String skinName;
	private Table table;
	private Tree tree;
	private String sectionItemId;
	private String pageItemId;
	
	
	private ConfigFieldInfo fieldInfo;
	private BeanFieldGroup<ConfigFieldInfo> fieldBinder=new BeanFieldGroup<ConfigFieldInfo>(ConfigFieldInfo.class);
	private TextField name;
	private TextField username;
	private TextField defaultValue; 
	private ComboBox codeTableName;
	private ComboBox displayType ;
	private ComboBox codeTableDefaultValue;
	private ComboBox format ;
	private ComboBox booleanCheckboxDefaultValue ;

	private TextField visibleEx;
	private CheckBox visible ;
	private  TextField orderBy;
	private  TextField whereClause;
	private TextField UnitedKey ;
	private ComboBox ChildItem ;
	private CheckBox selectionLoad;
	private CheckBox ReadOnly;
	private ComboBox codeTableDisplayType ;
	
	private TextField readOnlyEx ;
	private TextField requireEx ;
	private CheckBox Required;
	private  HorizontalLayout seleLoadLayout;
	private  TextField OnCreate;
	private TextField OnActive ;
	private TextField OnRefresh ;
	private  TextField OnBlur;
	private  TextField OnChange;
	private TextField OnButtonClick;
	private TextField valueBinding;
	private ComboBox IOInput;
	
	private TextField styleTextField;
	private TextField tagTextField;
	private TextField tipTextField;
	private TextField widthTextField;
	private TextField heightTextField;
	private ConfigUIUtilService service=new ConfigUIUtilServiceImpl();
	private ConfigDynamicFieldService fieldService=new ConfigDynamicFieldServiceImpl();
	private ConfigPageService pageService=new ConfigPageServiceImpl();
	private ConfigSectionService sectionService=new ConfigSectionServiceImpl();
	private PageDataModelService pageDataModelSevice=new PageDataModelServiceImpl();
	private SectionAddUI sectionUI;
	private String sectionVariable;
	private String skinBoClassName;
	public static List<String> codeTableType=new LinkedList<String>();
	public static List<String> displayTypeCode=new LinkedList<String>();
			 
			static {
				codeTableType.add("1:ComboBox");
				codeTableType.add("2:Two Text");
				codeTableType.add("3:Simple Selected");
				codeTableType.add("4:Radio Group"); 
				codeTableType.add("5:Checkbox Group");
				codeTableType.add("6:AutoComplete");
				displayTypeCode.add("TEXT");
				displayTypeCode.add("CODETABLE");
				displayTypeCode.add("DATE");
				displayTypeCode.add("NUMBER");
				displayTypeCode.add("CASCADETABLE");
				displayTypeCode.add("BOOLEANCHECKBOX");
			
				
			}
			
		
	
	
	public FormTab(ConfigSectionInfo sectionInfo,ConfigFieldInfo fieldInfo,
	    		Window fieldWindow,Table table,String skinboName,SectionAddUI sectionUI,
	    		Tree tree,String sectionItemId,String pageItemId,String variable,String skinBoclassName){
		
		this.sectionInfo=sectionInfo;
    	this.fieldInfo=fieldInfo;
    	this.fieldWindow=fieldWindow;
    	this.table=table;
    	this.skinName=skinboName;
    	this.sectionUI=sectionUI;
    	this.tree=tree;
    	this.sectionItemId=sectionItemId;
    	this.pageItemId=pageItemId;
    	this.sectionVariable=variable;
    	this.skinBoClassName=skinBoclassName;
		init();
	}
	
	private void init(){
		
		TabSheet tabsheet = new TabSheet();
//		tabsheet.setWidth("600px");
		tabsheet.setHeight("400px");
		addComponent(tabsheet);

		
		Forms form=new Forms(sectionInfo,fieldInfo,fieldWindow,table,skinName);
		
		Tab tab=tabsheet.addTab(form, "Property",null);


		FieldUXTab uxTab=new FieldUXTab();
		
		tabsheet.addTab(uxTab,"UX");
		
		FieldOperationTab opTab=new FieldOperationTab();
		tabsheet.addTab(opTab,"Event");
		
        Button saveBtn = new Button("Save");
        saveBtn.setImmediate(true);
        saveBtn.setWidth("100px");
        saveBtn.setStyleName("primary");
        saveBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	try {
            		if(name.getValue().isEmpty()||displayType.getValue()==null||(((String)(displayType.getValue())).isEmpty())
            				||username.getValue().isEmpty()||colspan.getValue().isEmpty()){
            			Notification.show("field insert error","please check the required item!", Notification.Type.ERROR_MESSAGE);
            			return ;
            		}
            		if(displayType.getValue().equals("CODETABLE")||displayType.getValue().equals("CASCADETABLE")){
            			if(codeTableDisplayType.getValue()==null||((String)codeTableDisplayType.getValue()).isEmpty()){
            				Notification.show("field insert error","please check the required item!", Notification.Type.ERROR_MESSAGE);
                			return ;
            			}
            		}
            		if(!fieldInfo.isEdit()){
            			for(ConfigFieldInfo  fieldInfo:sectionInfo.getFieldList()){
                			
                			if(fieldInfo.getFieldName().equals(name.getValue().trim())){
                				Notification.show("field insert error","duplicate field in section: "+sectionInfo.getSectionTitle(), Notification.Type.ERROR_MESSAGE);
                				return ;
                			}
                		}
            		}

					fieldBinder.commit();
					if(skinName!=null){
						fieldInfo.setSkinboName(skinName);
					}
					if(displayType.getValue().equals("CODETABLE")||displayType.getValue().equals("CASCADETABLE")){
						fieldInfo.setDefaultValue((String)codeTableDefaultValue.getValue());
						String codeTableType=((String)codeTableDisplayType.getValue()).substring(0, ((String)codeTableDisplayType.getValue()).indexOf(":"));
						fieldInfo.setCodeTabelDisplayType(codeTableType);
						String childItem=(String)ChildItem.getValue();
						if(childItem!=null){
							for(ConfigFieldInfo fieldInfo:sectionInfo.getFieldList()){
								if(fieldInfo.getFieldName().equals(childItem)){
									fieldInfo.setChildItem(fieldInfo.getFieldCode()+"");
									break;
								}
							}
						}
					}
					if(displayType.getValue().equals("BOOLEANCHECKBOX")){
						if(booleanCheckboxDefaultValue.getValue()!=null&&!((String)(booleanCheckboxDefaultValue.getValue())).isEmpty()){
							if(((String)(booleanCheckboxDefaultValue.getValue())).equals("N")){
								fieldInfo.setDefaultValue("0");
							}else if(((String)(booleanCheckboxDefaultValue.getValue())).equals("Y")){
								fieldInfo.setDefaultValue("1");
							}
						}
						
					}

					if(!sectionInfo.getFieldList().contains(fieldInfo)){
						sectionInfo.getFieldList().add(fieldInfo);
						fieldInfo.setSection(sectionInfo);
					}
					if(fieldInfo.getSection().getPage()==null){
						if(fieldInfo.getSection().getParentSection().getPage().getPageId()==null||fieldInfo.getSection().getParentSection().getPage().getPageId().isEmpty()){
							pageService.addConfigPage(fieldInfo.getSection().getPage(), false,null);
						}
						if(fieldInfo.getSection().getParentSection().getSectionId()==null||fieldInfo.getSection().getParentSection().getSectionId().isEmpty()){
							sectionService.addConfigSection(fieldInfo.getSection().getParentSection(), false,null);
						}
					}else{
						if(fieldInfo.getSection().getPage().getPageId()==null||fieldInfo.getSection().getPage().getPageId().isEmpty()){
							pageService.addConfigPage(fieldInfo.getSection().getPage(), false,null);
						}
					}
					
					if(fieldInfo.getSection().getSectionId()==null||fieldInfo.getSection().getSectionId().isEmpty()){
						sectionService.addConfigSection(fieldInfo.getSection(), false,null);
					}

					if(!fieldInfo.isEdit()){
						fieldService.addDynamicFieldConfig(fieldInfo, 0,false);
						addItemToTable();
					}else{
						fieldService.addDynamicFieldConfig(fieldInfo, 0,true);
					} 
					
					fieldInfo.setEdit(true);
					if(displayType.getValue().equals("CASCADETABLE")){
						for(ConfigFieldInfo fieldInfoOther:sectionInfo.getFieldList()){
							if(fieldInfoOther.getDisplayType().equals("CASCADETABLE")&&fieldInfoOther.getFieldName().equals(fieldInfo.getChildItem())){
								fieldInfoOther.setParentItem(fieldInfo.getFieldName());
								int fieldseq=fieldInfoOther.getSequence();
								fieldInfo.setSequence(fieldseq);
								fieldInfoOther.setSequence(fieldseq+1);
								fieldService.addDynamicFieldConfig(fieldInfoOther, 0,true);
								fieldService.addDynamicFieldConfig(fieldInfo, 0,true);
								
								break; 
							}
						}
						 
					}
					fieldWindow.close();
					Notification.show("Save Successful", "Configuration Field Save Successful!", Notification.Type.HUMANIZED_MESSAGE);
				} catch (CommitException e) {
					
					Notification.show("field insert error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}catch(Exception e){
					
					Notification.show("field insert error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
                
            }
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, true, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(footer);
        footer.addComponent(saveBtn);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	 private void addItemToTable(){
	    	Label commentsField = new Label();
	    	commentsField.setWidth("120px");
	    	commentsField.setValue(fieldInfo.getFieldName());
	    	
	    
	    	
	    	Button editBtn=new Button("Edit");//ComponentFactory.createDefaultButton("Edit");
	
	    	editBtn.setImmediate(true);
	    	editBtn.setWidth("80px");
	    	editBtn.setStyleName("primary");
	    	editBtn.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					final Window fieldWindow = new Window(null);
					fieldWindow.setWidth("700px");
					fieldWindow.setHeight("600px");
//					HorizontalSplitPanel splitPanel=new HorizontalSplitPanel();
					
					
					FormTab forms=new FormTab(sectionInfo,fieldInfo,fieldWindow,table,null,sectionUI,tree,sectionItemId,pageItemId,sectionVariable,skinBoClassName); 
					fieldWindow.setContent(forms);
//					splitPanel.addComponent(new Forms(sectionInfo,fieldInfo,fieldWindow,table,null));
					
					fieldWindow.center();
					UI.getCurrent().addWindow(fieldWindow);
					
				}
			});
	    	
	    	Button delBtn=new Button("Delete");//ComponentFactory.createDefaultButton("Delete");
	    	delBtn.setImmediate(true);
	    	delBtn.setWidth("80px");
	    	delBtn.setStyleName("primary");
	    	delBtn.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
					 ConfirmDialog dialog=ConfirmDialog.show(table.getUI(), "Reminder Message",
						        "Are you sure to delete field?", "Yes", "No",
						        new ConfirmDialog.Listener() {

						            public void onClose(ConfirmDialog dialog) {
						                if (dialog.isConfirmed()) {
						    				
						                	table.removeItem(fieldInfo.getFieldName());
											sectionInfo.getFieldList().remove(fieldInfo);
											try {
												fieldService.deleteDynamicFieldConfig(fieldInfo);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												Notification.show("field delete error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
											}
						                	
						                } 
						            }
						        });		
						  dialog.getCancelButton().setStyleName("primary");
					
					
					
				}
			});
	    	
	    	String itemId=fieldInfo.getFieldName();
	    	Object[] ob=new Object[]{commentsField, editBtn,delBtn};
	   // 	itemIDMapping.put(itemId, ob);
	    	table.addItem(ob,itemId);
	    	
	    	
	    }
	
	
public class Forms extends VerticalLayout implements View {
		
	    public Forms(ConfigSectionInfo sectionInfo,ConfigFieldInfo fieldInfo,
	    		Window fieldWindow,Table table,String skinboName) {
	    	
	    	init();
	    	bindField();
	    	
	    	
	    }  

	    private void bindField(){
//	    
	    	fieldBinder.setItemDataSource(fieldInfo);
	    	if(fieldBinder.getBoundPropertyIds().size()<1){
	    		fieldBinder.bind(name, "fieldName");
		    	fieldBinder.bind(codeTableName, "tableName");
		    	fieldBinder.bind(username, "displayLabel");
		    	fieldBinder.bind(defaultValue, "defaultValue");
		    	fieldBinder.bind(displayType, "displayType");
		    	fieldBinder.bind(visible, "visible");
		    	
		    	fieldBinder.bind(visibleEx, "visibleEx");
		    	fieldBinder.bind(ReadOnly, "readOnly");
		    	fieldBinder.bind(readOnlyEx, "readOnlyEx");
		    	
		    	fieldBinder.bind(Required, "required");
		    	fieldBinder.bind(requireEx, "requieredEx");
		 
		    	fieldBinder.bind(selectionLoad, "noSelectionLoad");
		    	
		    	
		    	fieldBinder.bind(orderBy, "orderBy");
		    	fieldBinder.bind(whereClause, "whereClause");
//		    	fieldBinder.bind(codeTableDisplayType, "codeTabelDisplayType");
		    	fieldBinder.bind(UnitedKey, "unitedKey");
		    	fieldBinder.bind(format, "format");
		    	fieldBinder.bind(ChildItem, "childItem");
		    	 
		    	fieldBinder.bind(colspan, "colspan");
		    	fieldBinder.bind(valueBinding, "valueBinding");
		    	fieldBinder.bind(IOInput, "io");
		    	
	    	}
	    	
	    	if(!fieldInfo.isEdit()){
	        	visible.setValue(true);
	        	ReadOnly.setValue(false); 
	        	Required.setValue(true);
	        	if(fieldInfo.getTableName()!=null&&!fieldInfo.getTableName().isEmpty()){
	        		displayType.setValue("CODETABLE");
	        		setCodeTableVisible(true);
	        		codeTableName.setValue(fieldInfo.getTableName());
	        		if(fieldInfo.getWhereClause()!=null&&!fieldInfo.getWhereClause().isEmpty()){
	        			whereClause.setValue(fieldInfo.getWhereClause());
	        		}
	        	}
	        	
	        	
	    	}else{    
	    		if(fieldInfo.getDisplayType()!=null){
	    			if(fieldInfo.getDisplayType().equals("CODETABLE")){
			    		codeTableName.setValue(fieldInfo.getTableName());
			    		codeTableDisplayType.setValue(codeTableType.get(Integer.parseInt(fieldInfo.getCodeTabelDisplayType())-1));
			    		setCodeTableVisible(true);
				        setUnitedTableVisible(false);
				        codeTableName.setValue(fieldInfo.getTableName());
			    		codeTableDefaultValue.setValue(fieldInfo.getDefaultValue());
			    	}else if(fieldInfo.getDisplayType().equals("CASCADETABLE")){
			    		setCodeTableVisible(true);
				        setUnitedTableVisible(true);
				        codeTableDisplayType.setValue(codeTableType.get(Integer.parseInt(fieldInfo.getCodeTabelDisplayType())-1));
				        codeTableName.setValue(fieldInfo.getTableName());
			    		codeTableDefaultValue.setValue(fieldInfo.getDefaultValue());
			    	}else if(fieldInfo.getDisplayType().equals("BOOLEANCHECKBOX")){
			    		setCodeTableVisible(false);
				        setUnitedTableVisible(false);
				        defaultValue.setVisible(false);
				        booleanCheckboxDefaultValue.setVisible(true);
				        booleanCheckboxDefaultValue.setValue(fieldInfo.getDefaultValue().equals("1")?"Y":"N");
			    		
			    	}else{
			    		defaultValue.setVisible(true);
			    		defaultValue.setValue(fieldInfo.getDefaultValue());
			    	}
	    		}
		    	
	    	}
	    	if(fieldInfo.getDisplayType()!=null){
	    		displayType.setReadOnly(true);
	    	}   
	    	String value=fieldInfo.getValueBinding();
	    	if(value==null||value.isEmpty()){
	    		try {
					if (fieldInfo.isDynamic()) {
							List<DynamicFieldConfigInfo> dynamicFieldList=pageDataModelSevice.getDynamicFieldInfo(fieldInfo.getPrdtId(), fieldInfo.getFieldName());
							if(dynamicFieldList.size()>0){
								boolean hasProperties=false;
								if(skinBoClassName!=null&&sectionVariable!=null){
									Class skinClass=Class.forName(skinBoClassName);
									
									for(;skinClass!=Object.class;skinClass=skinClass.getSuperclass()){	
										Method[] methods= skinClass.getDeclaredMethods();
										for(int i=0;i<methods.length;i++){
											if(methods[i].getName().toLowerCase().contains("property")){
												hasProperties=true;
												break;
											}
										}
										if(hasProperties){
											break;
										}
									}
									
								}
								
								DynamicFieldConfigInfo dynamicField=dynamicFieldList.get(0);
								if(hasProperties){
									value=sectionVariable+".properties."+fieldInfo.getFieldName();
								}else{
									if(dynamicField.getTableCode().equals("T_GEN_POLICY_INFO")){
										value=fieldService.getRootModel()+".properties."+fieldInfo.getFieldName();
									}else if(dynamicField.getTableCode().equals("T_INSURED_LIST")){
											String modelLevel=DynamicMappingCode.getInstance().getInsuredCateMapping(dynamicField.getInsuredcateID());
											value=fieldService.getRootModel()+"."+modelLevel+".properties."+fieldInfo.getFieldName();
									}
								}
								displayType.setValue(DynamicMappingCode.getInstance().getdynSpDisplayMapping(dynamicField.getDispType()));	
								if(dynamicField.getParentTableCode()!=null){
									codeTableName.setValue(dynamicField.getParentTableCode());
									
								}
								if(dynamicField.getWhereClause()!=null){
									whereClause.setValue(dynamicField.getWhereClause());
								}	
						}		
					} else {
						value = fieldService.getFieldValueBinding(fieldInfo.getFieldCode(), skinBoClassName,sectionVariable);

					}
	    		} catch (Exception e) {
					// TODO Auto-generated catch block
				
				}
	    	}
	        valueBinding.setValue(value);
	    	name.setReadOnly(true);
	    	 	
	    }
	    
	    private void init(){

	        setSpacing(true);
	        setMargin(true);


	        final FormLayout form = new FormLayout();
	        form.setMargin(false);
	        form.setWidth("600px");
	        form.addStyleName("light");
	        addComponent(form);

       
	        name = new TextField("Name");
	        name.setValue(fieldInfo.getFieldName());
	        name.setRequired(true);
	      
	        name.setWidth("50%");
	        form.addComponent(name);
	        
	        
	        username= new TextField("Label");
	        username.setRequired(true);
	         username.setValue("");
	        form.addComponent(username);

	        
	        HorizontalLayout wrap = new HorizontalLayout();
	        wrap.setSpacing(true);
	        wrap.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        wrap.setCaption("Visible");
	       visible = new CheckBox("", true);
	       wrap.addComponent(visible);
	       form.addComponent(wrap);
	      
	       visibleEx = new TextField("Edit Expression");
	       visibleEx.setValue("");
	       visibleEx.setVisible(false);
	       form.addComponent(visibleEx);
	        visible.addValueChangeListener(new ValueChangeListener(){

				@Override
				public void valueChange(ValueChangeEvent event) {
					if(!visible.getValue()){
						 visibleEx.setVisible(true);
					}else{
						 visibleEx.setVisible(false);
					}
					
				}});
	        colspan=new TextField("Column Span");
	        colspan.setRequired(true);
	        form.addComponent(colspan);
	         
	        displayType = new ComboBox("Display Type");
	        displayType.setHeight("40px");
	        displayType.setImmediate(true); 
	        displayType.setRequired(true);
	        List<String> allFieldTypes=null;
	        try {
				allFieldTypes=fieldService.getAllFieldDisplayType();
			} catch (Exception e1) {
				allFieldTypes=new LinkedList<String>();
				
			}
	        
	        displayType.addItems(allFieldTypes);
	        displayType.addValueChangeListener(new ValueChangeListener(){

				@Override
				public void valueChange(ValueChangeEvent event) {
					codeTableName.setVisible(false);
					if(displayType.getValue()!=null){
						if(displayType.getValue().equals("CODETABLE")){
							try {
								codeTableName.removeAllItems();
								List<String> tableNameList=service.getAllCodeTableName();
								codeTableName.addItems(tableNameList);
								setCodeTableVisible(true);
								setUnitedTableVisible(false);
								defaultValue.setVisible(false);
								format.setVisible(false);
							} catch (Exception e) {
								// TODO Auto-generated catch block
						
							}
						
						}else if(displayType.getValue().equals("NUMBER")){
							format.removeAllItems();
							setCodeTableVisible(false);
							setUnitedTableVisible(false);
							format.addItem("###,###,###.##");
							format.addItem("###,###,###"); 
							format.addItem("#############");
							format.setVisible(true);
							defaultValue.setVisible(true);
						}else if(displayType.getValue().equals("DATE")){
							format.removeAllItems();
							setCodeTableVisible(false);
							setUnitedTableVisible(false);
							format.addItem("dd/MM/yyyy HH:mm");
							format.addItem("dd/MM/yyyy");
							format.addItem("yyyy");
							format.addItem("yyyy-MM-dd");
							format.setVisible(true);
							defaultValue.setVisible(true);
						}else if(displayType.getValue().equals("CASCADETABLE")){
							try {
								codeTableName.removeAllItems();
								List<String> tableNameList=service.getAllCodeTableName();
								codeTableName.addItems(tableNameList);
								setCodeTableVisible(true);
								setUnitedTableVisible(true);
								defaultValue.setVisible(false);
								format.setVisible(false);
							} catch (Exception e) {
								// TODO Auto-generated catch block
						
							} 
						
						}else if(displayType.getValue().equals("BOOLEANCHECKBOX")){
							setCodeTableVisible(false); 
							setUnitedTableVisible(false);
							format.setVisible(false);  
							booleanCheckboxDefaultValue.setVisible(true);
							defaultValue.setVisible(false);
						}else{
							setCodeTableVisible(false);
							setUnitedTableVisible(false);
							format.setVisible(false);
							defaultValue.setVisible(true);
						}
					}  
				}});
	        displayType.setWidth("60%");
	        form.addComponent(displayType);
	        
	        format = new ComboBox("Format");
	        format.setHeight("40px");
	        format.setVisible(false);
	        format.setWidth("60%");
	        form.addComponent(format);
	        
	        
	        codeTableName=new ComboBox("Table Name");
	        codeTableName.setHeight("40px");
	        codeTableName.setImmediate(true);
	        codeTableName.setValue("");
	       
	        form.addComponent(codeTableName);
	        codeTableName.addValueChangeListener(new ValueChangeListener(){

				@Override
				public void valueChange(ValueChangeEvent event) {
					if(codeTableName.getValue()!=null){
						codeTableDefaultValue.removeAllItems();
						String tableName=(String)codeTableName.getValue();
						try {
							List<String> value=service.getCodeTableValueList(tableName);
							codeTableDefaultValue.addItems(value);
						} catch (Exception e) {
							// TODO Auto-generated catch block
						
						}
					}
				}});
	        codeTableName.setWidth("60%");
	        codeTableDisplayType=new ComboBox("Table Display Type");
	        codeTableDisplayType.setWidth("60%");
	        codeTableDisplayType.setHeight("40px");
	        codeTableDisplayType.addItems(codeTableType);
	      
	        form.addComponent(codeTableDisplayType);
	        
	        orderBy=new TextField("Order By");
	        orderBy.setValue("");
	     
	        form.addComponent(orderBy);
	        
	        whereClause=new TextField("Where Clause");
	       
	        form.addComponent(whereClause);
	        
	        
	        codeTableDefaultValue=new ComboBox("Default Value");
	        codeTableDefaultValue.setWidth("60%");
	        codeTableDefaultValue.setHeight("40px");
	        form.addComponent(codeTableDefaultValue);
	        
	        ChildItem=new ComboBox("Child Item");
	        ChildItem.setHeight("40px");
	        ChildItem.setWidth("60%");
	        for(ConfigFieldInfo fieldInfo:sectionInfo.getFieldList()){
	        	if(fieldInfo.getFieldName().equals(name.getValue()))continue;
	        	if(fieldInfo.getDisplayType().equals("CASCADETABLE")){
	        		ChildItem.addItem(fieldInfo.getFieldName());
	        	}
	        }
	        form.addComponent(ChildItem);
	        
	        IOInput=new ComboBox("IO(IN/OUT)");
	        IOInput.setHeight("40px");
	        IOInput.setWidth("60%");
	        IOInput.addItems("in","out");
	        form.addComponent(IOInput);
	        
	        UnitedKey =new TextField("United Key");
	       
	        form.addComponent(UnitedKey);
	        
	        
	        seleLoadLayout = new HorizontalLayout();
	        seleLoadLayout.setVisible(false);
	        seleLoadLayout.setSpacing(true);
	        seleLoadLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        seleLoadLayout.setCaption("No Selection Load");
	        selectionLoad=new CheckBox("",false);
	        seleLoadLayout.addComponent(selectionLoad);
	        form.addComponent(seleLoadLayout);

	        
	        defaultValue = new TextField("Default Value");
	        defaultValue.setValue("");
	        form.addComponent(defaultValue);
	        
	        booleanCheckboxDefaultValue=new ComboBox("Default Value");
	        booleanCheckboxDefaultValue.addItems("Y","N");
	        booleanCheckboxDefaultValue.setHeight("40px");
	        booleanCheckboxDefaultValue.setWidth("60%");
	        booleanCheckboxDefaultValue.setVisible(false);
	        form.addComponent(booleanCheckboxDefaultValue);

	        HorizontalLayout wrapReadOnly = new HorizontalLayout();
	        wrapReadOnly.setSpacing(true);
	        wrapReadOnly.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        wrapReadOnly.setCaption("ReadOnly");
	      
	        ReadOnly = new CheckBox("", false);
	        ReadOnly.addValueChangeListener(new ValueChangeListener(){

				@Override
				public void valueChange(ValueChangeEvent event) {
					if(!ReadOnly.getValue()){
						readOnlyEx.setVisible(true);
					}else{
						readOnlyEx.setVisible(false);
					}
					
				}});
	        wrapReadOnly.addComponent(ReadOnly);
	        form.addComponent(wrapReadOnly);
	        
	        readOnlyEx=new TextField("Edit Expression");
	        readOnlyEx.setVisible(true);
	        form.addComponent(readOnlyEx);
	        
	        
	        
	        HorizontalLayout wrapRequired = new HorizontalLayout();
	        wrapRequired.setSpacing(true);
	        wrapRequired.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        wrapRequired.setCaption("Required");
	        Required = new CheckBox("", true);
	        Required.addValueChangeListener(new ValueChangeListener(){

				@Override
				public void valueChange(ValueChangeEvent event) {
					if(!Required.getValue()){
						requireEx.setVisible(true);
					}else{
						requireEx.setVisible(false);
					}
					
				}});
	        
	        
	        
	        wrapRequired.addComponent(Required);
	        form.addComponent(wrapRequired);
	        requireEx =new TextField("Edit Expression");
	        requireEx.setVisible(false);
	        form.addComponent(requireEx);
	        
	        
	        valueBinding=new TextField("Value Binding");
	        
	        
	        form.addComponent(valueBinding);
        
	        setCodeTableVisible(false);
	        setUnitedTableVisible(false);
	  
    
	    }
	    
	    private void setCodeTableVisible(boolean visible){
	    	
	    	 codeTableName.setVisible(visible);
	    	 codeTableName.setRequired(visible);
	    	 orderBy.setVisible(visible);
	    	 whereClause.setVisible(visible);
	    	 codeTableDefaultValue.setVisible(visible);
	    	 codeTableDisplayType.setVisible(visible);
	    	 codeTableDisplayType.setRequired(visible);
	    	 booleanCheckboxDefaultValue.setVisible(false);
	    }
	    
	    private void setUnitedTableVisible(boolean visible){
	    	 ChildItem.setVisible(visible);
	    	
	    	 UnitedKey.setVisible(visible);
	    	 seleLoadLayout.setVisible(visible);
	    	 selectionLoad.setVisible(visible);
	    }
	    
	    
	    

	    
	    @Override
	    public void enter(ViewChangeEvent event) {
	        // TODO Auto-generated method stub

	    }
	}


public class FieldOperationTab extends VerticalLayout implements View {

	public FieldOperationTab(){
		
		init();
		bindField();
		
		
	}
	
	public void bindField(){
//		fieldBinder.bind(OnCreate, "onCreate");
//		fieldBinder.bind(OnActive, "onActive");
//    	fieldBinder.bind(OnRefresh, "onRefresh");
    	fieldBinder.bind(OnBlur, "onBlur");
    	fieldBinder.bind(OnChange, "onChange");
    	fieldBinder.bind(OnButtonClick, "onButtonClick");
	}   
	
	
	public void init(){
		
		    final FormLayout form = new FormLayout();
	        form.setMargin(false);
	        form.setSizeFull();
	        form.setWidth("500px");
	        form.addStyleName("light");
	        addComponent(form);
	         

//	        OnCreate = new TextField("OnCreate");
//	        OnCreate.setValue("");
//	        form.addComponent(OnCreate);
//	        
//	        OnActive = new TextField("OnActive");
//	        OnActive.setValue("");
//	        form.addComponent(OnActive);
//	        
//	        OnRefresh = new TextField("OnRefresh");
//	        OnRefresh.setValue("");
//	        form.addComponent(OnRefresh);
	        
	        OnBlur = new TextField("OnBlur");
	        OnBlur.setValue("");
	        form.addComponent(OnBlur);
	          
	        OnChange = new TextField("OnChange");
	        OnChange.setValue("");
	        form.addComponent(OnChange);
	           
	        OnButtonClick=new TextField("OnButtonClick");
	        OnButtonClick.setValue("");
	        form.addComponent(OnButtonClick);
	}
	
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}



}

public class FieldUXTab extends VerticalLayout implements View{

	public FieldUXTab(){
		init();
		
		
	}
	private void init(){
		 final FormLayout form = new FormLayout();
	     form.setMargin(false);
	     form.setSizeFull();
	     form.setWidth("500px");
	     form.addStyleName("light");
	     addComponent(form);
	     styleTextField = new TextField("Style");
	     styleTextField.setValue("");
	     form.addComponent(styleTextField); 
	     
	     tagTextField = new TextField("Tag");
	     tagTextField.setValue("");
	     form.addComponent(tagTextField); 
	     
	     tipTextField = new TextField("Tip");
	     tipTextField.setValue("");
	     form.addComponent(tipTextField); 
	    
	     widthTextField = new TextField("Width(px)");
	     widthTextField.setValue("");
	     form.addComponent(widthTextField); 
	     
	     heightTextField = new TextField("Width(px)");
	     heightTextField.setValue("");
	     form.addComponent(heightTextField); 
	}
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}



	
private Component buildFieldLayout(final  VerticalLayout verticalLayout_4){

	final CssLayout csslayout=new CssLayout();
	csslayout.setWidth("100%");
	Label display=new Label(fieldInfo.getFieldName());
	display.setWidth("250px");
	
//	display.setValue();
	Button editFieldBtn=ComponentFactory.createDefaultButton("Edit");//new Button("Edit");
	editFieldBtn.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window fieldWindow = new Window(null);
			fieldWindow.setWidth("950px");
			fieldWindow.setHeight("700px");
//			HorizontalSplitPanel splitPanel=new HorizontalSplitPanel();
//			splitPanel.setMargin(true);
			Forms forms=new Forms(sectionInfo,fieldInfo,fieldWindow,table,null); 
			fieldWindow.setContent(forms);
			

//			splitPanel.addComponent(new Trees(splitPanel,
//					sectionInfo,fieldInfo,fieldWindow,verticalLayout_4,true));
//			verticalLayout_4.removeComponent(csslayout);
//			splitPanel.addComponent(new Forms(sectionInfo,fieldInfo,fieldWindow,verticalLayout_4));
			
			fieldWindow.center();
			UI.getCurrent().addWindow(fieldWindow);
			
		}
	});
//	editFieldBtn.setIcon(new ThemeResource("icons/editField.gif"));
	editFieldBtn.setWidth("100px");
	Button delFieldBtn=ComponentFactory.createDefaultButton("Delete");//new Button("Edit");
//	delFieldBtn.setIcon(new ThemeResource("icons/delete.gif"));
	delFieldBtn.setWidth("100px");
	Label space=new Label();
	space.setWidth("50px");
	delFieldBtn.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			verticalLayout_4.removeComponent(csslayout);
			sectionInfo.getFieldList().remove(fieldInfo);
			try {
				fieldService.deleteDynamicFieldConfig(fieldInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			
			}
		}
	});
	csslayout.addComponent(display);
	csslayout.addComponent(editFieldBtn);
	csslayout.addComponent(space);
	csslayout.addComponent(delFieldBtn);
	
	return csslayout;
	}



}
