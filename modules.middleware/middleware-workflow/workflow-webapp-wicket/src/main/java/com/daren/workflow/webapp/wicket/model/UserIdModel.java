package com.daren.workflow.webapp.wicket.model;

import org.activiti.engine.identity.User;
import org.apache.wicket.model.AbstractReadOnlyModel;

@SuppressWarnings("serial")
public class UserIdModel extends AbstractReadOnlyModel<String> {

	@Override
	public String getObject() {
        User user=new User() {
            @Override
            public String getId() {
                return "11";
            }

            @Override
            public void setId(String s) {

            }

            @Override
            public String getFirstName() {
                return "sun";
            }

            @Override
            public void setFirstName(String s) {

            }

            @Override
            public String getLastName() {
                return "test";
            }

            @Override
            public void setLastName(String s) {

            }

            @Override
            public String getEmail() {
                return null;
            }

            @Override
            public void setEmail(String s) {

            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public void setPassword(String s) {

            }
        };
		return user.getId();
	}

}
