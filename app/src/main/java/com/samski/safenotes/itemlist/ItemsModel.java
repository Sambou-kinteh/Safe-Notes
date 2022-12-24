package com.samski.safenotes.itemlist;

import com.samski.safenotes.data.DataParent;

public class ItemsModel extends DataParent {

    private String userText;
    private int preferedThemeColor;

    public ItemsModel(String userText, int preferedThemeColor) {
        this.userText = userText;
        this.preferedThemeColor = preferedThemeColor;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public int getPreferedThemeColor() {
        return preferedThemeColor;
    }

    public void setPreferedThemeColor(int preferedThemeColor) {
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
