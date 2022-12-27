package com.samski.safenotes.colorsView;

import com.samski.safenotes.R;
import com.samski.safenotes.data.DataParent;

import java.io.Serializable;
import java.util.HashMap;

public class ColorModel extends DataParent implements Serializable {

//    color keys
    public static final String COLOR_KEY_RED = "red", COLOR_KEY_BLUE = "blue", COLOR_KEY_YELLOW = "yellow",
        COLOR_KEY_TEAL = "teal", COLOR_KEY_GREY = "grey", COLOR_KEY_ORANGE = "orange",COLOR_KEY_PINK = "pink",
        COLOR_KEY_GREEN = "green", COLOR_KEY_BROWN = "brown", COLOR_KEY_INDIGO = "indigo", COLOR_KEY_WHITE = "white",
        COLOR_KEY_THEMEVARIANT1 = "variant1";

//    color values
    public static final int COLOR_VALUE_RED = R.color.red, COLOR_VALUE_BLUE = R.color.blue,
        COLOR_VALUE_YELLOW = R.color.yellow, COLOR_VALUE_TEAL = R.color.teal, COLOR_VALUE_GREY = R.color.grey,
        COLOR_VALUE_ORANGE = R.color.orange, COLOR_VALUE_PINK = R.color.pink, COLOR_VALUE_GREEN = R.color.green,
        COLOR_VALUE_BROWN = R.color.brown, COLOR_VALUE_INDIGO = R.color.indigo, COLOR_VALUE_WHITE = R.color.white,
        COLOR_VALUE_THEMEVARIANT1 = R.color.themeDarkVariant1;

    private HashMap<String, Integer> colors;

    public ColorModel(HashMap<String, Integer> colors) {
        this.colors = colors;
    }

    public HashMap<String, Integer> getColors() {
        return colors;
    }

    public void setColors(HashMap<String, Integer> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "ColorModel{" +
                "colors=" + colors +
                '}';
    }

    public int getCount() {

        return colors.size();
    }

    public static int getColor(String key) {

        switch (key) {

            case COLOR_KEY_BLUE:
                return COLOR_VALUE_BLUE;
            case COLOR_KEY_RED:
                return COLOR_VALUE_RED;
            case COLOR_KEY_YELLOW:
                return COLOR_VALUE_YELLOW;
            case COLOR_KEY_INDIGO:
                return COLOR_VALUE_INDIGO;
            case COLOR_KEY_ORANGE:
                return COLOR_VALUE_ORANGE;
            case COLOR_KEY_PINK:
                return COLOR_VALUE_PINK;
            case COLOR_KEY_TEAL:
                return COLOR_VALUE_TEAL;
            case COLOR_KEY_GREY:
                return COLOR_VALUE_GREY;
            case COLOR_KEY_GREEN:
                return COLOR_VALUE_GREEN;
            case COLOR_KEY_BROWN:
                return COLOR_VALUE_BROWN;
            case COLOR_KEY_WHITE:
                return COLOR_VALUE_WHITE;
            case COLOR_KEY_THEMEVARIANT1:
                return COLOR_VALUE_THEMEVARIANT1;
            default:
                return 0;
        }
    }
}
