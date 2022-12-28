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
import com.samski.safenotes.colorsView.ColorModel;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.font.FontModel;
import com.samski.safenotes.login.DataModel;
import com.samski.safenotes.login.LoginLogic;
import com.samski.safenotes.settings.SettingsModel;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ExtendedFloatingActionButton btnLogin;
    EditText userNameFirst, userNameLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (new DataHandler(this, DataHandler.USER_DATA_LOGIN_SHAREDPREF_KEY, DataModel.class).readPreferences() != null) {

            Intent intent = new Intent(this, MainActivityAfterLogin.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);

            //        login logic
            userNameFirst = findViewById(R.id.userNameFirst);
            userNameLast = findViewById(R.id.userNameLast);
            btnLogin = findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener((view -> {

                initSettingsPref();
                initColorsPref();
                initFontPref();
                LoginLogic.getInstance(this, userNameFirst.getText().toString(), userNameLast.getText().toString());
            }));

        }

    }

    private void initSettingsPref() {

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

    private void initColorsPref() {

//        one time colors init
        DataHandler handler = new DataHandler(this, DataHandler.COLOR_DATA_SHAREDPREF_KEY, ColorModel.class);

//        default colors to init
        HashMap<String, Integer> colors = new HashMap<>();

        colors.put(ColorModel.COLOR_KEY_BLUE, ColorModel.COLOR_VALUE_BLUE);
        colors.put(ColorModel.COLOR_KEY_RED, ColorModel.COLOR_VALUE_RED);
        colors.put(ColorModel.COLOR_KEY_GREEN, ColorModel.COLOR_VALUE_GREEN);
        colors.put(ColorModel.COLOR_KEY_BROWN, ColorModel.COLOR_VALUE_BROWN);
        colors.put(ColorModel.COLOR_KEY_INDIGO, ColorModel.COLOR_VALUE_INDIGO);
        colors.put(ColorModel.COLOR_KEY_YELLOW, ColorModel.COLOR_VALUE_YELLOW);
        colors.put(ColorModel.COLOR_KEY_PINK, ColorModel.COLOR_VALUE_PINK);
        colors.put(ColorModel.COLOR_KEY_GREY, ColorModel.COLOR_VALUE_GREY);
        colors.put(ColorModel.COLOR_KEY_TEAL, ColorModel.COLOR_VALUE_TEAL);
        colors.put(ColorModel.COLOR_KEY_ORANGE, ColorModel.COLOR_VALUE_ORANGE);

        ColorModel color = new ColorModel(colors);
        handler.writeToPreferences(color);

    }

    private void initFontPref() {

        DataHandler handler = new DataHandler(this, DataHandler.FONT_DATA_SHAREDPREF_KEY, FontModel.class);

//        font names and paths to init
        HashMap<String, Integer> fonts = new HashMap<>();

//        available fonts to user
        fonts.put(FontModel.KEY_FONT_AMSTERDAM, FontModel.VALUE_FONT_AMSTERDAM);
        fonts.put(FontModel.KEY_FONT_AKAYA_TELI, FontModel.VALUE_FONT_AKAYA_TELI);
        fonts.put(FontModel.KEY_FONT_ALLERTA_STENCIL, FontModel.VALUE_FONT_ALLERTA_STENCIL);
        fonts.put(FontModel.KEY_FONT_AMARANTH_ITALIC, FontModel.VALUE_FONT_AMARANTH_ITALIC);
        fonts.put(FontModel.KEY_FONT_AUDIOWIDE, FontModel.VALUE_FONT_AUDIOWIDE);
        fonts.put(FontModel.KEY_FONT_BALOO_TAMMUDU_2, FontModel.VALUE_FONT_BALOO_TAMMUDU_2);
        fonts.put(FontModel.KEY_FONT_LUKIEST, FontModel.VALUE_FONT_LUKIEST);
        fonts.put(FontModel.KEY_FONT_MANSALVA, FontModel.VALUE_FONT_MANSALVA);
        fonts.put(FontModel.KEY_FONT_OLDENBURG, FontModel.VALUE_FONT_OLDENBURG);

//        store serialized objects in user storeage
        FontModel font = new FontModel(fonts);
        handler.writeToPreferences(font);
    }

}