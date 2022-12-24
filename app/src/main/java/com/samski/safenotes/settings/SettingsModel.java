package com.samski.safenotes.settings;

import java.util.HashMap;
import java.util.Set;

public class SettingsModel {

    private HashMap<String, String> displayOptions;
    private HashMap<String, String> accountOptions;

    public SettingsModel(HashMap<String, String> displayOptions, HashMap<String, String> accountOptions) {
        this.displayOptions = displayOptions;
        this.accountOptions = accountOptions;
    }

    public HashMap<String, String> getDisplayOptions() {
        return displayOptions;
    }

    public void setDisplayOptions(HashMap<String, String> displayOptions) {
        this.displayOptions = displayOptions;
    }

    public HashMap<String, String> getAccountOptions() {
        return accountOptions;
    }

    public void setAccountOptions(HashMap<String, String> accountOptions) {
        this.accountOptions = accountOptions;
    }

    @Override
    public String toString() {
        return "SettingsModel{" +
                "displayOptions=" + displayOptions +
                ", accountOptions=" + accountOptions +
                '}';
    }

}
