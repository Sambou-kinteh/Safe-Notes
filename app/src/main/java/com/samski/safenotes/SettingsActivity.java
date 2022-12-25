package com.samski.safenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.login.DataModel;
import com.samski.safenotes.settings.SettingsModel;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    public static final String FLAG_ALL = "all";
    public static final String FLAG_DISPLAY_OPTIONS_ONLY = "displayOption";
    public static final String FLAG_ACCOUNT_OPTIONS_ONLY = "accountOption";
    public static final String KEY_DISPLAY_OPTION_SHOWADDBUTTON = "showAddButton";
    public static final String KEY_DISPLAY_OPTION_THEME = "theme";
    public static final String VALUE_DISPLAY_OPTION_THEME_DEFAULT = "System";
    public static final String VALUE_DISPLAY_OPTION_THEME_DARK = "Dark";
    public static final String VALUE_DISPLAY_OPTION_THEME_LIGHT = "Light";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static Context context;

    FloatingActionButton btnBack;
    SwitchCompat showAddButton;
    CardView themeOption;
    TextView themeOptionValue;
    CardView themeOptionCard;
    RadioGroup themeOptionCardRadiogroup;
    DataHandler handler;
    SettingsModel settings;
    Button deleteAllBtn, deleteLoginData, deleteItems;
    RelativeLayout relativeLayoutSettings;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.back_from_settings);
        showAddButton = findViewById(R.id.showAddNewButtonSwitch);
        themeOption = findViewById(R.id.themeOptionLayout);
        themeOptionValue = findViewById(R.id.themeOptionValue);
        themeOptionCardRadiogroup = findViewById(R.id.themeOptionCardRadiogroup);
        themeOptionCard = findViewById(R.id.themeOptionCard);
        deleteAllBtn = findViewById(R.id.deleteAllBtn);
        deleteLoginData = findViewById(R.id.deleteLoginData);
        deleteItems = findViewById(R.id.deleteItems);
        relativeLayoutSettings = findViewById(R.id.settingsRelativeLayout);
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
                    context.setTheme(R.style.Theme_SafeNotes_Dark);
                    themeOptionValue.setText(R.string.radioButtonSystem);
                    settings.getDisplayOptions().replace(KEY_DISPLAY_OPTION_THEME, VALUE_DISPLAY_OPTION_THEME_DEFAULT);
                    break;
                case light:
                    context.setTheme(R.style.Theme_SafeNotes_Light);
                    this.setTheme(R.style.Theme_SafeNotes_Light_NoActionbar);
                    relativeLayoutSettings.setBackgroundColor(getResources().getColor(R.color.white, this.getTheme()));
                    themeOptionValue.setText(R.string.radioButtonLight);
                    settings.getDisplayOptions().replace(KEY_DISPLAY_OPTION_THEME, VALUE_DISPLAY_OPTION_THEME_LIGHT);
                    break;
                case dark:
                    context.setTheme(R.style.Theme_SafeNotes_Dark);
                    this.setTheme(R.style.Theme_SafeNotes_Dark_NoActionBar);
                    relativeLayoutSettings.setBackgroundColor(getResources().getColor(R.color.themeDarkVariant1, this.getTheme()));
                    themeOptionValue.setText(R.string.radioButtonDark);
                    settings.getDisplayOptions().replace(KEY_DISPLAY_OPTION_THEME, VALUE_DISPLAY_OPTION_THEME_DARK);
                    break;
                default:
                    break;
            }

            handler.writeToPreferences(settings);
        });

        deleteAllBtn.setOnClickListener(view -> {

            handler.deleteAllData();
        });

        deleteLoginData.setOnClickListener(view -> {

            DataHandler handler = new DataHandler(this, DataHandler.USER_DATA_LOGIN_SHAREDPREF_KEY, DataModel.class);
            handler.deleteAllData();
        });

        deleteItems.setOnClickListener(view -> {

            DataHandler handler = new DataHandler(this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
            handler.deleteAllData();
        });

//        set the values to the user pref
        updateSettings();

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

        themeOptionCard.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {

        if (!(themeOptionCard.getVisibility() == View.VISIBLE)) {

            startActivity(new Intent(this, MainActivityAfterLogin.class));
        }else {
            themeOptionCard.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (themeOptionCard.getVisibility() == View.VISIBLE) {

            themeOptionCard.setVisibility(View.GONE);
//            return true;
            return false;
        }
        return super.onTouchEvent(event);
    }

    public void updateSettings() {

        //        check switch if user switched off and on if user switched on
        showAddButton.setChecked(Objects.equals(settings.getDisplayOptions().get(KEY_DISPLAY_OPTION_SHOWADDBUTTON),
                SettingsActivity.YES));

        themeOptionValue.setText(settings.getDisplayOptions().get(KEY_DISPLAY_OPTION_THEME));
    }

    public static void getMainActivityAfterLoginContext(Context contextExp) {

        context = contextExp;
    }
}