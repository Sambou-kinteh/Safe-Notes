package com.samski.safenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samski.safenotes.colorsView.ColorAdapter;
import com.samski.safenotes.colorsView.ColorModel;
import com.samski.safenotes.data.DataHandler;
import com.samski.safenotes.font.FontAdapter;
import com.samski.safenotes.font.FontModel;
import com.samski.safenotes.itemlist.ItemsAdapter;
import com.samski.safenotes.itemlist.ItemsModel;
import com.samski.safenotes.settings.SettingsModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

public class EditorActivity extends AppCompatActivity {

    public static RecyclerView colorView, fontView;
    public static RelativeLayout editorLayout;
    public static EditText editorForUserText, textTitleEditor;
    public static FloatingActionButton colorOption, fontOption;
    public static ItemsModel item;
    public static CardView editorOptionsView;
    public int color;

    private ArrayList<ItemsModel> items;
    private DataHandler handler;
    private ScrollView editorScrollView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SettingsModel settings = new DataHandler(this, DataHandler.USER_SETTINGS_DATA_SHAREDPREF_KEY,
                SettingsModel.class).readPreferences();

        switch (Objects.requireNonNull(settings.getDisplayOptions().get(SettingsActivity.KEY_DISPLAY_OPTION_THEME))) {

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_DARK:
                this.setTheme(R.style.Theme_SafeNotes_Dark_NoActionBar);
                break;

            case SettingsActivity.VALUE_DISPLAY_OPTION_THEME_LIGHT:
                this.setTheme(R.style.Theme_SafeNotes_Light_NoActionbar);
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
        textTitleEditor = findViewById(R.id.textTitleEditor);
        editorOptionsView = findViewById(R.id.editorOptionsView);
        colorOption = findViewById(R.id.colorOption);
        fontOption = findViewById(R.id.fontOption);
        colorView = findViewById(R.id.colorView);
        fontView = findViewById(R.id.fontView);
        editorScrollView = findViewById(R.id.editorScrollView);
        editorLayout = findViewById(R.id.editorLayout);
        handler = new DataHandler(this, DataHandler.USER_ITEMS_DATA_SHAREDPREF_KEY, DataHandler.ITEM_ARRAYLIST_TYPE);
        items = handler.readPreferences();
        item = items.get(ItemsAdapter.currentItemPosition);

        if (!item.getUserText().equals("")) {

            editorForUserText.setText(item.getUserText());
        }

        if (!item.getUserTitle().equals("")) {

            textTitleEditor.setText(item.getUserTitle());
        }

        if (!item.getFont().equals("")) {

            editorForUserText.setTypeface(getResources().getFont(FontModel.getFont(item.getFont())));
        }

//        set background to user preference
        color = getResources()
                .getColor(ColorModel.getColor(item.getPreferedThemeColor()), getTheme());
        editorLayout.setBackgroundColor(color);

//        card view's color and alpha
        editorOptionsView.setCardBackgroundColor(color);
        editorOptionsView.setAlpha(0.9f);

//        set backgound of menu choosers
        colorOption.setBackgroundTintList(ColorStateList.valueOf(color));
        fontOption.setBackgroundTintList(ColorStateList.valueOf(color));
//        colorOption.setSupportImageTintList(ColorStateList.valueOf(color));


        editorForUserText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                item.setUserText(editorForUserText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textTitleEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                item.setUserTitle(textTitleEditor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        colorOption.setOnClickListener(view -> {

            boolean isToShow = Objects.equals(colorView.getVisibility(), View.GONE);
            colorView.setVisibility(isToShow ? View.VISIBLE : View.GONE);
            colorOption.setBackgroundTintList(
                    isToShow
                    ? ColorStateList.valueOf(
                    getResources().getColor(R.color.clicked, getTheme()))
                    : ColorStateList.valueOf(color)
            );
            openColorView();
        });

        fontOption.setOnClickListener(view -> {

            boolean isToShow = Objects.equals(fontView.getVisibility(), View.GONE);
            fontView.setVisibility(isToShow ? View.VISIBLE: View.GONE);
            fontOption.setBackgroundTintList(
                    isToShow
                    ? ColorStateList.valueOf(
                    getResources().getColor(R.color.clicked, getTheme()))
                    : ColorStateList.valueOf(color)
            );
            openFontView();
        });

        editorScrollView.setOnScrollChangeListener((View view, int i, int i1, int i2, int i3) -> {

            if (Objects.equals(colorView.getVisibility(), View.VISIBLE)) {

                colorView.setVisibility(View.GONE);
                colorOption.setBackgroundTintList(ColorStateList.valueOf(color));
            } else if (Objects.equals(fontView.getVisibility(), View.VISIBLE)) {

                fontView.setVisibility(View.GONE);
                fontOption.setBackgroundTintList(ColorStateList.valueOf(color));
            }
            editorOptionsView.animate().translationY(i1/2f).setDuration(200).alpha(.9f - i1/1000f);
            textTitleEditor.animate().translationY(-i1/2f).setDuration(200).alpha(1f - i1/1000f);

            if (i1 > 300) {

                textTitleEditor.setVisibility(View.INVISIBLE);
                if (i1 > 400) {
                    textTitleEditor.setVisibility(View.GONE);
                }
            } else {
                textTitleEditor.setVisibility(View.VISIBLE);
            }

        });


    }

    private void storeDataUpdate() {

        if (item.getUserTitle().equals("") && item.getUserText().equals("")) {

            items.remove(item);
            ItemsAdapter.currentItemPosition = 0;
        } else {
            item.setUserTitle(textTitleEditor.getText().toString());
            item.setUserText(editorForUserText.getText().toString());
            items.set(ItemsAdapter.currentItemPosition, item);

        }
        handler.writeToPreferences(items);

    }

    @Override
    public void finish() {
        storeDataUpdate();
        super.finish();
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, MainActivityAfterLogin.class));
        storeDataUpdate();
    }

    private void openColorView() {

        DataHandler handler = new DataHandler(this, DataHandler.COLOR_DATA_SHAREDPREF_KEY, ColorModel.class);
        ColorModel color = handler.readPreferences();

        ColorAdapter adapter = new ColorAdapter(EditorActivity.this);
        adapter.setColors(color);
        colorView.setAdapter(adapter);
        colorView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    private void openFontView() {

        DataHandler handler = new DataHandler(this, DataHandler.FONT_DATA_SHAREDPREF_KEY, FontModel.class);
        FontModel font = handler.readPreferences();

        FontAdapter adapter = new FontAdapter(this);
        adapter.setFont(font);
        fontView.setAdapter(adapter);
        fontView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

}
