package com.ebao.gs.sp.uiconfig;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.ComponentContainer;

@SuppressWarnings("serial")
@Theme("uiconfig")
public class UidesignerUI extends UI {

	@Override
	protected void init(VaadinRequest request) {



		
		
//		final VerticalLayout layout = new VerticalLayout();
//		layout.setMargin(true);
//		setContent(layout);
//		layout.setCaption("Web UI Configuration");
//		layout.setSizeFull();
//		sectionUI newUI=new sectionUI();
//		setContent(newUI);

		Navigator navigator=new Navigator(this, this);
		 this.setSizeFull();
		 ProductUIEnter uiEnterConfig=new ProductUIEnter();
//		UIMainConfig1   uiMainConfig=new UIMainConfig1();
		navigator.addView("", uiEnterConfig);
//		navigator.addView(UIMainConfig1.CONFIG_PAGE, uiMainConfig);
//		
//		
//		setContent(uiEnterConfig);
//		setContent(uiMainConfig);

	}

}