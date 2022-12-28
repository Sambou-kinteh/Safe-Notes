package com.samski.safenotes.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samski.safenotes.itemlist.ItemsModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataHandler {

    public static String USER_DATA_LOGIN_SHAREDPREF_KEY = "userLoginData"; //IN CLASS USAGE NOT INTENDED
    public static String USER_ITEMS_DATA_SHAREDPREF_KEY = "userItemsData"; //IN CLASS USAGE NOT INTENDED
    public static String USER_SETTINGS_DATA_SHAREDPREF_KEY = "settingsData"; //IN CLASS USAGE NOT INTENDED
    public static String COLOR_DATA_SHAREDPREF_KEY = "colorsHashMapSerialized";
    public static String FONT_DATA_SHAREDPREF_KEY = "fontsHashMapSerialized";
    public static Type ITEM_ARRAYLIST_TYPE = new TypeToken<ArrayList<ItemsModel>>(){}.getType();

    public String userPrefKey;
    Gson gsonObj = new Gson();
    Context context;
    SharedPreferences sharedPreferences;
    Type type;
    SharedPreferences.Editor editor;

    public DataHandler(Context context, String userPrefKey, Type type) {

        this.userPrefKey = userPrefKey;
        this.context = context;
        this.type = type;
        sharedPreferences = context.getSharedPreferences(userPrefKey, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public <AnyType> AnyType readPreferences() {

        try {
//            convert the json object to DataModel object
            if (sharedPreferences.contains(userPrefKey)) {

                return gsonObj.fromJson(sharedPreferences.getString(userPrefKey, null), type);
            }

            ArrayList<ItemsModel> init = new ArrayList<>();
            init.add(new ItemsModel("", "", "", ""));
            return gsonObj.fromJson(gsonObj.toJson(new ArrayList<ItemsModel>()), type);

        } catch (Exception e) {
//TODO:          Log logic
            e.printStackTrace();
//            Log.e(String.valueOf(Log.ERROR), e.getMessage());
            return null;
        }
    }

    public <T> boolean writeToPreferences(T userData) {

        try {

            editor.putString(userPrefKey, gsonObj.toJson(userData));
            editor.apply();

            return true;
        } catch (Exception e) {

            e.printStackTrace();
//            Log.e(String.valueOf(Log.ERROR), e.getMessage());
            return false;
        }
    }

    public <T> boolean updateLoginPreference(T newData) {

        try {

            editor.putString(userPrefKey, gsonObj.toJson(newData));
            editor.apply();
            return true;
        } catch (Exception e) {

            return false;
        }

    }

    public boolean deleteAllData() {

//        only in testing phase
        try {
            editor.clear();
            editor.apply();

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public void deleteItemFromPref(int position, ArrayList<ItemsModel> items) {

        writeToPreferences(items);
    }
}
