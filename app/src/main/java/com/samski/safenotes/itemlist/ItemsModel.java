package com.samski.safenotes.itemlist;

import com.samski.safenotes.data.DataParent;

import java.io.Serializable;

public class ItemsModel extends DataParent implements Serializable {

    private String userText;
    private String preferedThemeColor;

    public ItemsModel(String userText, String preferedThemeColor) {
        this.userText = userText;
        this.preferedThemeColor = preferedThemeColor;
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

    @Override
    public String toString() {
        return "ItemsModel{" +
                "userText='" + userText + '\'' +
                ", preferedThemeColor='" + preferedThemeColor + '\'' +
                '}';
    }
}
