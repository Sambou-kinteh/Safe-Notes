package com.samski.safenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samski.safenotes.colorsView.ColorModel;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.itemlist.ItemsAdapter;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.login.DataModel;
import com.samski.safenotes.settings.SettingsModel;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivityAfterLogin extends AppCompatActivity {

    public static final String DONT_ADD_ITEM_FLAG = "don't add";
    public static final String ADD_ITEM_FLAG = "add";
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private RecyclerView itemsView;
    private DataHandler handler;
    private ItemsAdapter adapter;
    private TextView addNewTxt;
    public static FloatingActionButton floatingActionButton;
    public boolean isDark;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        handler = new DataHandler(MainActivityAfterLogin.this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);

        ArrayList<DataModel> items = handler.readPreferences();

        SettingsModel settings = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY,
                SettingsModel.class).readPreferences();

//        set user theme pref as activity theme
        switch (Objects.requireNonNull(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_THEME))) {

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_LIGHT:
                this.setTheme(R.style.Theme_SafeNotes_Light);
                isDark = false;
//                findViewById(R.id.layoutActivityMain).setBackgroundColor(getResources().getColor(R.color.white, this.getTheme()));
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DARK:
                this.setTheme(R.style.Theme_SafeNotes_Dark);
                isDark = true;
//                findViewById(R.id.layoutActivityMain).setBackgroundColor(getResources().getColor(R.color.themeDarkVariant1, this.getTheme()));
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DEFAULT:
                break;
        }

//        create and set content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login);

//        load user settings to view
        addNewTxt = findViewById(R.id.createAnItemText);
        if (items.size() != 0) {

//            load data to user if user already has items
            loadDataToUser(DONT_ADD_ITEM_FLAG);
            if (Objects.equals(addNewTxt.getVisibility(), View.VISIBLE)) {
                addNewTxt.setVisibility(View.GONE);
            }
        }else addNewTxt.setVisibility(View.VISIBLE);

        floatingActionButton = findViewById(R.id.floatingButtonAdd);
        floatingActionButton.setOnClickListener((view) ->{

            int itemSize = loadDataToUser(ADD_ITEM_FLAG);
            ItemsAdapter.currentItemPosition = itemSize-1;
            startActivity(new Intent(this, EditorActivity.class));

        });

        if (Objects.equals(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_SHOWADDBUTTON),
                SettingsActivity.NO) && Objects.equals(floatingActionButton.getVisibility(), View.VISIBLE)) {

            floatingActionButton.setVisibility(View.INVISIBLE);
        } else if (Objects.equals(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_SHOWADDBUTTON),
                SettingsActivity.YES) && Objects.equals(floatingActionButton.getVisibility(), View.INVISIBLE)) {

            floatingActionButton.setVisibility(View.VISIBLE);
        }

        //share context with settingsactivity
        SettingsActivity.getMainActivityAfterLoginContext(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (isDark) {
            menuInflater.inflate(R.menu.main_menu_dark, menu);

        }else menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final int settingsCase = R.id.settings;
        final int addCase = R.id.add;
        switch (item.getItemId()) {

            case settingsCase:
                startActivity(new Intent(MainActivityAfterLogin.this, SettingsActivity.class));
                return true;
            case addCase:
                int itemSize = loadDataToUser(ADD_ITEM_FLAG);
                ItemsAdapter.currentItemPosition = itemSize-1;
                startActivity(new Intent(this, EditorActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    return current size of items
    public int loadDataToUser(String flag) {

        itemsView = findViewById(R.id.itemsList);
        ArrayList<ItemsModel> items = handler.readPreferences();

        correctResolutionComplexity();

        adapter = new ItemsAdapter(MainActivityAfterLogin.this);

        if (flag.equals(ADD_ITEM_FLAG)) {

            items.add(isDark ? new ItemsModel("", ColorModel.COLOR_KEY_THEMEVARIANT1, "", "") :
                    new ItemsModel("", ColorModel.COLOR_KEY_WHITE, "", ""));

            adapter.setItems(items, ItemsAdapter.FLAG_SET_ONE);

        }else adapter.setItems(items, ItemsAdapter.FLAG_SET_RANGE);

//        dataset could be initialised alternatively with adapter.InitializeItemPref()
        itemsView.setAdapter(adapter);
        itemsView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        handler.writeToPreferences(items);

        return items.size();

    }

    public void correctResolutionComplexity() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
    }


}