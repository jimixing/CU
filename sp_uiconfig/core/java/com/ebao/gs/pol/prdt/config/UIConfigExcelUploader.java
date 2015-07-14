package com.ebao.gs.pol.prdt.config;

import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

import com.ebao.foundation.commons.exceptions.ExceptionUtil;
import com.ebao.foundation.commons.exceptions.GenericException;
import com.ebao.gs.pol.prdt.config.service.UIConfigImportService;
import com.ebao.gs.pol.prdt.config.service.impl.UIConfigImportServiceImpl;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededListener;

public class UIConfigExcelUploader implements Receiver, SucceededListener, FailedListener, StartedListener, FinishedListener  {
	
	private static final long serialVersionUID = 1L;
	
	private String productName;
	
	private UIConfigImportService uiconfigImportService = new UIConfigImportServiceImpl();
	
	public UIConfigExcelUploader(){}
	
	public UIConfigExcelUploader(String productName){
		this.productName = productName;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
		// TODO Auto-generated method stub
    }
	
    @Override
    public void uploadFailed(Upload.FailedEvent event) {
		// TODO Auto-generated method stub
    }
    
	@Override
	public void uploadStarted(StartedEvent event) {
		// TODO Auto-generated method stub
		String filename = event.getFilename();
		String mimeType = event.getMIMEType();
		
		if (StringUtils.endsWithIgnoreCase(filename, ".xls") || StringUtils.endsWithIgnoreCase(filename, ".xlsx")) { 
			try {
				uiconfigImportService.uiconfigImport(productName, filename);
				
				Notification.show("Humanized Message", "Configuration Field Save Successful!", Notification.Type.HUMANIZED_MESSAGE);
			} catch (Exception e) {
				GenericException ge = ExceptionUtil.parse(e);
				throw ge;
			}
		} else {
			Notification.show("Warning Message", "Import excel format is incorrect", Notification.Type.WARNING_MESSAGE);
		}
	}

	@Override
	public void uploadFinished(FinishedEvent event) {
		// TODO Auto-generated method stub
	}

}

