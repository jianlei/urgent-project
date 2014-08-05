package com.daren.core.web.wicket.security;

import org.apache.wicket.model.IDetachable;

public class PasswordInfo implements IDetachable {
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @Override
    public void detach() {

    }
}
