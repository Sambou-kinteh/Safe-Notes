package com.samski.safenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.itemlist.ItemsAdapter;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.settings.SettingsModel;

import java.util.ArrayList;
import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    EditText editorForUserText;
    ItemsModel item;
    ArrayList<ItemsModel> items;
    DataHandler handler;
    private boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SettingsModel settings = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY,
                SettingsModel.class).readPreferences();
//        this.setTheme(Objects.equals(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_THEME),
//                SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DARK)
//                ? R.style.Theme_SafeNotes_Dark
//                : R.style.Theme_SafeNotes_Light);
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
        handler = new DataHandler(this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
        items = handler.readPreferences();
        item = items.get(ItemsAdapter.currentItemPosition);

        if (!item.getUserText().equals("")) {

            editorForUserText.setText(item.getUserText());
        }

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
//                open color menu for user

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
}