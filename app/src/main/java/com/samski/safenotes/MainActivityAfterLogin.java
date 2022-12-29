package com.samski.safenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samski.safenotes.colorsView.ColorModel;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.itemlist.ItemsAdapter;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.settings.SettingsModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class MainActivityAfterLogin extends AppCompatActivity {

    public static final String DONT_ADD_ITEM_FLAG = "don't add";
    public static final String ADD_ITEM_FLAG = "add";
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static FloatingActionButton floatingActionButton;
    public static TextView addNewTxt;
    public boolean isDark;
    public static ArrayList<ItemsModel> items;

    private RecyclerView itemsView;
    private DataHandler handler;
    private ItemsAdapter adapter;
    private CardView searchBar;
    private EditText searchBarSearch;
    private RelativeLayout layoutActivityMain;
    private FloatingActionButton searchBarSettings, searchBarAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        handler = new DataHandler(MainActivityAfterLogin.this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);

        items = handler.readPreferences();

        SettingsModel settings = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY,
                SettingsModel.class).readPreferences();

//        set user theme pref as activity theme
        switch (Objects.requireNonNull(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_THEME))) {

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_LIGHT:
                this.setTheme(R.style.Theme_SafeNotes_Light_NoActionbar);
                isDark = false;
//                findViewById(R.id.layoutActivityMain).setBackgroundColor(getResources().getColor(R.color.white, this.getTheme()));
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DARK:
                this.setTheme(R.style.Theme_SafeNotes_Dark_NoActionBar);
                isDark = true;
//                findViewById(R.id.layoutActivityMain).setBackgroundColor(getResources().getColor(R.color.themeDarkVariant1, this.getTheme()));
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DEFAULT:
                break;
        }

//        create and set content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login);

        searchBar = findViewById(R.id.searchBar);
        searchBarSearch = findViewById(R.id.searchBarSearch);
        searchBarSettings = findViewById(R.id.searchBarSettings);
        searchBarAdd = findViewById(R.id.searchBarAdd);
        layoutActivityMain = findViewById(R.id.layoutActivityMain);

        searchBarSettings.setBackgroundTintList(ColorStateList.valueOf(
                getResources().getColor(R.color.white, getTheme())
        ));
        searchBarAdd.setBackgroundTintList(ColorStateList.valueOf(
                getResources().getColor(R.color.white, getTheme())
        ));

        searchBarSettings.setOnClickListener(view -> {

            startActivity(new Intent(MainActivityAfterLogin.this, SettingsActivity.class));
        });

        searchBarAdd.setOnClickListener(view -> {

            int itemSize = loadDataToUser(ADD_ITEM_FLAG, null);
            ItemsAdapter.currentItemPosition = itemSize-1;
            startActivity(new Intent(this, EditorActivity.class));
        });

//        load user settings to view
        addNewTxt = findViewById(R.id.createAnItemText);
        if (items.size() != 0) {

//            load data to user if user already has items
            loadDataToUser(DONT_ADD_ITEM_FLAG, null);
            if (Objects.equals(addNewTxt.getVisibility(), View.VISIBLE)) {
                addNewTxt.setVisibility(View.GONE);
            }
        }else addNewTxt.setVisibility(View.VISIBLE);

        floatingActionButton = findViewById(R.id.floatingButtonAdd);
        floatingActionButton.setOnClickListener((view) ->{

            int itemSize = loadDataToUser(ADD_ITEM_FLAG, null);
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

        searchBarSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (items.size() > 0) {

                    ArrayList<ItemsModel> results;
                    if (charSequence.length() > 2) {
                        results = searchText(charSequence.toString());

                        if (results.size() < 1) {

                            loadDataToUser(DONT_ADD_ITEM_FLAG, null);
                            addNewTxt.setText(R.string.ItemNotFound);
                            addNewTxt.setVisibility(View.VISIBLE);
                        }

                        loadDataToUser(DONT_ADD_ITEM_FLAG, results);
                    } else {

                        if (Objects.equals(addNewTxt.getVisibility(), View.VISIBLE)) {

                            addNewTxt.setVisibility(View.GONE);
                            addNewTxt.setText(R.string.createAnItemText);
                        }
                        loadDataToUser(DONT_ADD_ITEM_FLAG, null);
                    }
                } else {
                    if (charSequence.length() - 2 == 1) {

                        Toast.makeText(MainActivityAfterLogin.this, R.string.createFirst, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //share context with settingsactivity
        SettingsActivity.getMainActivityAfterLoginContext(this);

    }

//    return current size of items
    public int loadDataToUser(String flag, ArrayList<ItemsModel> searchItems) {

        itemsView = findViewById(R.id.itemsList);
        ArrayList<ItemsModel> items = handler.readPreferences();

        correctResolutionComplexity();

        adapter = new ItemsAdapter(MainActivityAfterLogin.this);

        if (!(searchItems == null)) {

            adapter.setItems(searchItems, ItemsAdapter.FLAG_SET_RANGE);

        }else {
            if (flag.equals(ADD_ITEM_FLAG)) {

                items.add(isDark ? new ItemsModel("", ColorModel.COLOR_KEY_THEMEVARIANT1, "", "") :
                        new ItemsModel("", ColorModel.COLOR_KEY_WHITE, "", ""));

                adapter.setItems(items, ItemsAdapter.FLAG_SET_ONE);

            }else adapter.setItems(items, ItemsAdapter.FLAG_SET_RANGE);
        }


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private ArrayList<ItemsModel> searchText(String text) {

//        TODO: optimization with the use of async and binary search algorithm

        ArrayList<ItemsModel> searchResults = new ArrayList<>();
        text = text.toLowerCase(Locale.ROOT);

        if (items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {

                ItemsModel item = items.get(i);

                if (item.getUserText().toLowerCase(Locale.ROOT).contains(text) || item.getUserTitle().toLowerCase(Locale.ROOT).contains(text)) {

                    searchResults.add(item);
                }
            }
        }

        return searchResults;
    }

}