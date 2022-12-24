package com.samski.safenotes.login;

import android.content.Context;
import android.content.Intent;

import com.samski.safenotes.MainActivityAfterLogin;
import com.samski.safenotes.data.DataHandler;

public class LoginLogic {

    private Context context;
    private String userNameFirst, userNameLast;
    private DataHandler handler;
    public static LoginLogic instance;

    public static synchronized LoginLogic getInstance(Context context, String userName, String userPhone) {

        return (instance == null) ? instance = new LoginLogic(context, userName, userPhone) : instance;
    }

    private LoginLogic(Context context, String userName, String userPhone) {

        this.context = context;
        this.userNameFirst = userName;
        this.userNameLast = userPhone;

        handler = new DataHandler(context, DataHandler.USER_DATA_LOGIN_SHAREDPREF_KEY, DataModel.class);

        addToUserLoginData();
//        go to main activity
        Intent intent = new Intent(this.context, MainActivityAfterLogin.class);
        this.context.startActivity(intent);

    }

    public void addToUserLoginData() {

        handler.writeToPreferences(new DataModel(this.userNameFirst, this.userNameLast, true));

    }

    public Object getDbPerson() {

        return handler.readPreferences();
    }

}