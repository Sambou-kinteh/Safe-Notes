package com.samski.safenotes.login;

import com.samski.safenotes.data.DataParent;

public class DataModel extends DataParent {

    private boolean isLogin;
    private String userNameFirst, userNameLast;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public DataModel(String userName, String userPhone, boolean isLogin) {
        this.userNameFirst = userName;
        this.userNameLast = userPhone;
        this.isLogin = isLogin;
    }

    public String getUserNameFirst() {
        return userNameFirst;
    }

    public void setUserNameFirst(String userNameFirst) {
        this.userNameFirst = userNameFirst;
    }

    public String getUserNameLast() {
        return userNameLast;
    }

    public void setUserNameLast(String userNameLast) {
        this.userNameLast = userNameLast;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "userName='" + userNameFirst + '\'' +
                ", userPhone='" + userNameLast + '\'' +
                '}';
    }
}
