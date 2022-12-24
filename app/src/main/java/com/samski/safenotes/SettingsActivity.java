package com.samski.safenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.settings.SettingsModel;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    public static final String FLAG_ALL = "all";
    public static final String FLAG_DISPLAY_OPTIONS_ONLY = "displayOption";
    public static final String FLAG_ACCOUNT_OPTIONS_ONLY = "accountOption";
    public static final String KEY_DISPLAY_OPTION_SHOWADDBUTTON = "showAddButton";
    public static final String KEY_DISPLAY_OPTION_THEME = "theme";
    public static final String VALUE_DISPLAY_OPTION_THEME_DEFAULT = "system";
    public static final String VALUE_DISPLAY_OPTION_THEME_DARK = "dark";
    public static final String VALUE_DISPLAY_OPTION_THEME_LIGHT = "light";
    public static final String YES = "yes";
    public static final String NO = "no";

    FloatingActionButton btnBack;
    SwitchCompat showAddButton;
    CardView themeOption;
    TextView themeOptionValue;
    CardView themeOptionCard;
    RadioGroup themeOptionCardRadiogroup;
    DataHandler handler;
    SettingsModel settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.back_from_settings);
        showAddButton = findViewById(R.id.showAddNewButtonSwitch);
        themeOption = findViewById(R.id.themeOptionLayout);
        themeOptionValue = findViewById(R.id.themeOptionValue);
        themeOptionCardRadiogroup = findViewById(R.id.themeOptionCardRadiogroup);
        handler = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY, SettingsModel.class);
        settings = handler.readPreferences();

        //if btnOnClick, take user back
        btnBack.setOnClickListener((view) -> {

            takeUserBack();
        });

        showAddButton.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> {

            showAddButton(b);
        });

        themeOption.setOnClickListener((view -> {

            showThemeOptionCard();
        }));

        themeOptionCardRadiogroup.setOnCheckedChangeListener((RadioGroup radioGroup, int i) -> {

//            TODO: CHANGE THE APP THEME ACCORDING TO USERS PREF
            final int system = R.id.radioButtonSystem;
            final int light = R.id.radioButtonLight;
            final int dark = R.id.radioButtonDark;

            SettingsModel settings = handler.readPreferences();

            switch (i) {

                case system:
                    this.setTheme(R.style.NoActionBar);
                    themeOptionValue.setText(R.string.radioButtonSystem);
                    settings.getDisplayOptions().replace(KEY_DISPLAY_OPTION_THEME, VALUE_DISPLAY_OPTION_THEME_DEFAULT);
                    break;
                case light:
//                    TODO: change app theme
                    this.setTheme(R.style.NoActionBar);
                    themeOptionValue.setText(R.string.radioButtonLight);
                    settings.getDisplayOptions().replace(KEY_DISPLAY_OPTION_THEME, VALUE_DISPLAY_OPTION_THEME_LIGHT);
                    break;
                case dark:
//                    TODO: change app theme
                    this.setTheme(R.style.NoActionBar);
                    themeOptionValue.setText(R.string.radioButtonDark);
                    settings.getDisplayOptions().replace(KEY_DISPLAY_OPTION_THEME, VALUE_DISPLAY_OPTION_THEME_DARK);
                    break;
                default:
                    break;
            }

            handler.writeToPreferences(settings);
        });

//        check switch if user switched off and on if user switched on
        showAddButton.setChecked(Objects.equals(settings.getDisplayOptions().get(KEY_DISPLAY_OPTION_SHOWADDBUTTON),
                SettingsActivity.YES));

    }

    public void takeUserBack() {

        Intent intent = new Intent(this, MainActivityAfterLogin.class);
        startActivity(intent);
    }

    public void showAddButton(boolean value) {

        SettingsModel userSettingsData = handler.readPreferences();
        HashMap<String, String> displayOptions = userSettingsData.getDisplayOptions();

        if (!value) {

//            save state in settings pref
            displayOptions.replace(KEY_DISPLAY_OPTION_SHOWADDBUTTON, NO);
            saveSettings(displayOptions, new HashMap<String, String>(), FLAG_DISPLAY_OPTIONS_ONLY);

//            set button invisible
            MainActivityAfterLogin.floatingActionButton.setVisibility(View.INVISIBLE);

        } else {
//            save state in settings pref
            displayOptions.replace(KEY_DISPLAY_OPTION_SHOWADDBUTTON, YES);
            saveSettings(displayOptions, new HashMap<String, String>(), FLAG_DISPLAY_OPTIONS_ONLY);

//            set button visible
            MainActivityAfterLogin.floatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    public void saveSettings(HashMap<String, String> displayOption, HashMap<String, String> accountOption, String flag) {

        SettingsModel userSettingsData = handler.readPreferences();

        switch (flag) {

            case FLAG_ALL:
                userSettingsData.setDisplayOptions(displayOption);
                userSettingsData.setAccountOptions(accountOption);
                break;
            case FLAG_ACCOUNT_OPTIONS_ONLY:
                userSettingsData.setAccountOptions(accountOption);
                break;
            case FLAG_DISPLAY_OPTIONS_ONLY:
                userSettingsData.setDisplayOptions(displayOption);
                break;
            default:
                break;
        }

        handler.writeToPreferences(userSettingsData);


    }

    public void showThemeOptionCard() {

        themeOptionCard = findViewById(R.id.themeOptionCard);
        themeOptionCard.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {

        if (themeOptionCard.getVisibility() == View.VISIBLE) {

            themeOptionCard.setVisibility(View.GONE);
        }else {

            super.onBackPressed();
//            Intent intent = new Intent(this, MainActivityAfterLogin.class);
//            startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        System.out.println(event.getAction());
        if (themeOptionCard.getVisibility() == View.VISIBLE) {

            themeOptionCard.setVisibility(View.GONE);
            return true;
        }
        return super.onTouchEvent(event);
    }
}