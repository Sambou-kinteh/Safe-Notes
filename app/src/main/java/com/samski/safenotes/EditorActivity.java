package com.samski.safenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.samski.safenotes.colorsView.ColorAdapter;
import com.samski.safenotes.colorsView.ColorModel;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.itemlist.ItemsAdapter;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.settings.SettingsModel;

import java.util.ArrayList;
import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    public static RecyclerView colorView;
    public static RelativeLayout editorLayout;
    private EditText editorForUserText;
    public static ItemsModel item;
    private ArrayList<ItemsModel> items;
    private DataHandler handler;
    private boolean isDark;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SettingsModel settings = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY,
                SettingsModel.class).readPreferences();

        switch (Objects.requireNonNull(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_THEME))) {

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DARK:
                this.setTheme(R.style.Theme_SafeNotes_Dark);
                isDark = true;
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_LIGHT:
                this.setTheme(R.style.Theme_SafeNotes_Light);
                isDark = false;
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DEFAULT:
//                set to system
                break;

            default:
                break;

        }

//        set content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editorForUserText = findViewById(R.id.usersTextInEditor);
        colorView = findViewById(R.id.colorView);
        editorLayout = findViewById(R.id.editorLayout);
        handler = new DataHandler(this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
        items = handler.readPreferences();
        item = items.get(ItemsAdapter.currentItemPosition);

        if (!item.getUserText().equals("")) {

            editorForUserText.setText(item.getUserText());
        }

//        set background to user preference
        editorLayout.setBackgroundColor(getResources()
                .getColor(ColorModel.getColor(item.getPreferedThemeColor()), getTheme()));
        System.out.println(item.getPreferedThemeColor());

        editorForUserText.setOnKeyListener((View view, int i, KeyEvent keyEvent) -> {

            item.setUserText(editorForUserText.getText().toString());
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (!isDark) {

            menuInflater.inflate(R.menu.editor_menu, menu);
        } else {

            menuInflater.inflate(R.menu.editor_menu_dark, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final int color = R.id.changeColor;

        switch (item.getItemId()) {

            case color:
                colorView.setVisibility(Objects.equals(colorView.getVisibility(), View.GONE) ? View.VISIBLE : View.GONE);
                openColorView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void storeUserText() {

        item.setUserText(editorForUserText.getText().toString());
        items.set(ItemsAdapter.currentItemPosition, item);
        handler.writeToPreferences(items);

    }

    @Override
    public void finish() {
        storeUserText();
        super.finish();
    }



    @Override
    public void onBackPressed() {

        storeUserText();
        startActivity(new Intent(this, MainActivityAfterLogin.class));
    }

    public void openColorView() {

        DataHandler handler = new DataHandler(this, DataHandler.COLOR_DATA_SHAREDPREF_KEY, ColorModel.class);
        ColorModel color = handler.readPreferences();

        ColorAdapter adapter = new ColorAdapter(EditorActivity.this);
        adapter.setColors(color);
        colorView.setAdapter(adapter);
        colorView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }
}
