package com.samski.safenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Locale;

public class EditorActivity extends AppCompatActivity {

    EditText editorForUserText;
    ItemsModel item;
    ArrayList<ItemsModel> items;
    DataHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editorForUserText = findViewById(R.id.usersTextInEditor);
        handler = new DataHandler(this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
        items = handler.readPreferences();
        item = items.get(ItemsAdapter.currentItemPosition);

        if (!item.getUserText().equals("")) {

            editorForUserText.setText(item.getUserText());
        }

        editorForUserText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                item.setUserText(editorForUserText.getText().toString());
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

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
    public void onBackPressed() {
        super.onBackPressed();
        storeUserText();

    }

    @Override
    public void finishAffinity() {
        storeUserText();
        super.finishAffinity();
    }

}