/*
 * Copyright 2013 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.daren.core.web.component.dashboard;

import com.daren.core.web.component.dashboard.web.DashboardContext;
import com.daren.core.web.component.dashboard.web.DashboardContextInjector;
import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.MetaDataKey;

/**
 * @author Decebal Suiu
 */
public class DashboardContextInitializer implements IInitializer {

	@SuppressWarnings("serial")
	public static MetaDataKey<DashboardContext> DASHBOARD_CONTEXT_KEY = new MetaDataKey<DashboardContext>() {};
	
	@Override
	public void init(Application application) {
		// create dashboard context
		DashboardContext dashboardContext = new DashboardContext();
		
		// store dashboard context in application
        application.setMetaData(DASHBOARD_CONTEXT_KEY, dashboardContext);
        
	    // add dashboard context injector
	    DashboardContextInjector dashboardContextInjector = new DashboardContextInjector(dashboardContext);
	    application.getComponentInstantiationListeners().add(dashboardContextInjector);
	}

	@Override
	public void destroy(Application application) {
		// does noting
	}

}
