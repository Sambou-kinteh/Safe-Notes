package com.samski.safenotes.itemlist;

import android.graphics.fonts.Font;

import com.samski.safenotes.data.DataParent;

import java.io.Serializable;

public class ItemsModel extends DataParent implements Serializable {

    private String userText;
    private String preferedThemeColor;
    private String userTitle;
    private String font;

    public ItemsModel(String userText, String preferedThemeColor, String userTitle, String font) {
        this.userText = userText;
        this.preferedThemeColor = preferedThemeColor;
        this.userTitle = userTitle;
        this.font = font;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getPreferedThemeColor() {
        return preferedThemeColor;
    }

    public void setPreferedThemeColor(String preferedThemeColor) {
        this.preferedThemeColor = preferedThemeColor;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    @Override
    public String toString() {
        return "ItemsModel{" +
                "userText='" + userText + '\'' +
                ", preferedThemeColor='" + preferedThemeColor + '\'' +
                ", userTitle='" + userTitle + '\'' +
                ", font='" + font + '\'' +
                '}';
    }
}
