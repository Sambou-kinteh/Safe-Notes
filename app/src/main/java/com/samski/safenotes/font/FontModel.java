package com.samski.safenotes.font;

import com.samski.safenotes.R;
import com.samski.safenotes.data.DataParent;

import java.io.Serializable;
import java.util.HashMap;

public class FontModel extends DataParent implements Serializable {

//    font keys
    public static final String KEY_FONT_AMSTERDAM = "amsterdam", KEY_FONT_ABEEZEE = "abeezee",
        KEY_FONT_AKAYA_TELI = "akaya_telivigala", KEY_FONT_ALLERTA_STENCIL = "allerta_stencil",
        KEY_FONT_AMARANTH_ITALIC = "amaranth_italic", KEY_FONT_AUDIOWIDE = "audiowide",
        KEY_FONT_BALOO_TAMMUDU_2 = "baloo_tammudu_2_madium", KEY_FONT_LUKIEST = "lukiest_guy",
        KEY_FONT_MANSALVA = "mansalva", KEY_FONT_OLDENBURG = "oldenburg";

    public static final int VALUE_FONT_AMSTERDAM = R.font.amsterdam, VALUE_FONT_ABEEZEE = R.font.abeezee,
        VALUE_FONT_AKAYA_TELI = R.font.akaya_telivigala, VALUE_FONT_ALLERTA_STENCIL = R.font.allerta_stencil,
        VALUE_FONT_AMARANTH_ITALIC = R.font.amaranth_italic, VALUE_FONT_AUDIOWIDE = R.font.audiowide,
        VALUE_FONT_BALOO_TAMMUDU_2 = R.font.baloo_tammudu_2_medium, VALUE_FONT_LUKIEST = R.font.luckiest_guy,
        VALUE_FONT_MANSALVA = R.font.mansalva, VALUE_FONT_OLDENBURG = R.font.oldenburg;

    //    font values i.e Path of the font in project
//    public static final int VALUE_FONT_AMSTERDAM = R.font.amsterdam;

    private HashMap<String, Integer> fonts;

    public FontModel(HashMap<String, Integer> fonts) {
        this.fonts = fonts;
    }

    public HashMap<String, Integer> getFonts() {
        return fonts;
    }

    public void setFonts(HashMap<String, Integer> fonts) {
        this.fonts = fonts;
    }

    @Override
    public String toString() {
        return "FontModel{" +
                "fonts=" + fonts +
                '}';
    }

    public int getCount() {

        return fonts.size();
    }

    public static int getFont(String key) {

        switch (key) {

            case KEY_FONT_AMSTERDAM:
                return VALUE_FONT_AMSTERDAM;
            case KEY_FONT_ABEEZEE:
                return VALUE_FONT_ABEEZEE;

            case KEY_FONT_AKAYA_TELI:
                return VALUE_FONT_AKAYA_TELI;

            case KEY_FONT_ALLERTA_STENCIL:
                return VALUE_FONT_ALLERTA_STENCIL;

            case KEY_FONT_AMARANTH_ITALIC:
                return VALUE_FONT_AMARANTH_ITALIC;

            case KEY_FONT_AUDIOWIDE:
                return VALUE_FONT_AUDIOWIDE;

            case KEY_FONT_BALOO_TAMMUDU_2:
                return VALUE_FONT_BALOO_TAMMUDU_2;

            case KEY_FONT_LUKIEST:
                return VALUE_FONT_LUKIEST;

            case KEY_FONT_MANSALVA:
                return VALUE_FONT_MANSALVA;

            case KEY_FONT_OLDENBURG:
                return VALUE_FONT_OLDENBURG;


            default:
                return 0;
        }
    }
}
