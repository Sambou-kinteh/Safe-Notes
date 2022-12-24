package com.samski.safenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.itemlist.ItemsAdapter;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.login.DataModel;

import java.util.ArrayList;


public class MainActivityAfterLogin extends AppCompatActivity {

    public static final String DONT_ADD_ITEM_FLAG = "don't add";
    public static final String ADD_ITEM_FLAG = "add";
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private RecyclerView itemsView;
    private DataHandler handler;
    ItemsAdapter adapter;
    public static FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login);

        ArrayList<DataModel> items = new DataHandler(this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY,
                DataHandler.ITEM_ARRAYLIST_TYPE).readPreferences();

        if (items.size() != 0) {

            loadDataToUser(DONT_ADD_ITEM_FLAG);
        }

        floatingActionButton = findViewById(R.id.floatingButtonAdd);
        floatingActionButton.setOnClickListener((view) ->{

            loadDataToUser(ADD_ITEM_FLAG);
//            TODO: TAKE USER TO EDITOR
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settings:
                Intent intent = new Intent(MainActivityAfterLogin.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.add:
                loadDataToUser(ADD_ITEM_FLAG);
//                TODO: TAKE USER TO EDITOR

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadDataToUser(String flag) {

        itemsView = findViewById(R.id.itemsList);
//        TODO: CAN ALSO USE NEW CARDVIEW AS AN ALTERNATIVE TO RECYCLERVIEW

        handler = new DataHandler(MainActivityAfterLogin.this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
        ArrayList<ItemsModel> items = handler.readPreferences();
        System.out.println(items);

        correctResolutionComplexity();

        adapter = new ItemsAdapter(MainActivityAfterLogin.this);
        System.out.println(items.size() > 1);
        if (flag.equals(ADD_ITEM_FLAG)) {

            items.add(new ItemsModel("", 0));
        }
        adapter.setItems(items);
//        dataset could be initialised alternatively with adapter.InitializeItemPref()
        itemsView.setAdapter(adapter);
        itemsView.setLayoutManager(new GridLayoutManager(MainActivityAfterLogin.this, 2));
        handler.writeToPreferences(items);

//        CardView card = new CardView(MainActivityAfterLogin.this);

    }

    public void correctResolutionComplexity() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
    }


}