package com.samski.safenotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.login.DataModel;
import com.samski.safenotes.login.LoginLogic;
import com.samski.safenotes.settings.SettingsModel;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ExtendedFloatingActionButton btnLogin;
    EditText userNameFirst, userNameLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (new DataHandler(this, DataHandler.USER_DATA_LOGIN_SHAREDPREF_KEY, DataModel.class).readPreferences() != null) {

            Intent intent = new Intent(this, MainActivityAfterLogin.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        login logic
        userNameFirst = findViewById(R.id.userNameFirst);
        userNameLast = findViewById(R.id.userNameLast);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener((view -> {

            initSettingsPref();
            LoginLogic.getInstance(this, userNameFirst.getText().toString(), userNameLast.getText().toString());
        }));

    }

    public void initSettingsPref() {

//        one time settings init
        DataHandler handler = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY, SettingsModel.class);
        HashMap<String, String> displayOptions = new HashMap<>();
        HashMap<String, String> accountOptions = new HashMap<>();

//        init display options
        displayOptions.put(SettingsActivity.KEY_DISPLAY_OPTION_SHOWADDBUTTON, SettingsActivity.YES);
        displayOptions.put(SettingsActivity.KEY_DISPLAY_OPTION_THEME, SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DEFAULT);

//        init account options

//        write to settings pref
        SettingsModel settings = new SettingsModel(displayOptions, accountOptions);
        handler.writeToPreferences(settings);
    }

}