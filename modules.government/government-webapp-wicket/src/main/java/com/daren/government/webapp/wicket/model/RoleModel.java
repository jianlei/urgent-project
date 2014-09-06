package com.daren.government.webapp.wicket.model;

import org.apache.wicket.model.AbstractReadOnlyModel;

@SuppressWarnings("serial")
public class RoleModel extends AbstractReadOnlyModel<String> {

	@Override
	public String getObject() {
		/*String role = (String) AuthenticatedWebSession.get().getRoles()
				.toArray()[0];
		return role.equals("DEVELOPER") ? "TESTER" : "DEVELOPER";*/
        return "DEVELOPER";
	}

}
