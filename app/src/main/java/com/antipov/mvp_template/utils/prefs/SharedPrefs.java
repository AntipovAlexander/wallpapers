package com.antipov.mvp_template.utils.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Preferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AlexanderAntipov on 01.03.2018.
 */

public class SharedPrefs {
    private final SharedPreferences sharedPref;
    private final Context context;

    public SharedPrefs(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(Const.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public Preferences getPreferences(){
        return new Preferences(
                isUseRandomTag(),
                isUseCustomTag(),
                isLoadOnlyWhenWifi(),
                getWallpaperTags(),
                getKeywordForWallpapers(),
                getWallpaperChangesFrequency()
        );
    }

    public boolean isUseRandomTag(){
        return sharedPref
                .getBoolean(
                        context.getString(R.string.prefs_key_random_flag),
                        false
                );
    }

    public boolean isUseCustomTag(){
        return sharedPref
                .getBoolean(
                        context.getString(R.string.prefs_key_custom_tag_flag),
                        false
                );
    }

    public String getKeywordForWallpapers(){
        return sharedPref
                .getString(
                        context.getString(R.string.prefs_key_keyword),
                        ""
                );
    }

    public int getWallpaperChangesFrequency(){
        return sharedPref
                .getInt(
                        context.getString(R.string.prefs_key_frequency),
                        0
                );
    }

    public boolean isLoadOnlyWhenWifi(){
        return sharedPref
                .getBoolean(
                        context.getString(R.string.prefs_key_onlywifi),
                        true
                );
    }

    public Set<String> getWallpaperTags(){
        return sharedPref
                .getStringSet(
                        context.getString(R.string.prefs_key_tags),
                        new HashSet<>()
                );
    }

    public boolean save(Preferences preferences) {
        try {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(context.getString(R.string.prefs_key_random_flag), preferences.isRandom());
            editor.putBoolean(context.getString(R.string.prefs_key_custom_tag_flag), preferences.isCustom());
            editor.putString(context.getString(R.string.prefs_key_keyword), preferences.getWallpaperKey());
            editor.putInt(context.getString(R.string.prefs_key_frequency), preferences.getFrequency());
            editor.putBoolean(context.getString(R.string.prefs_key_onlywifi), preferences.isOnlyWiFi());
            editor.putStringSet(context.getString(R.string.prefs_key_tags), preferences.getWallpaperTags());
            editor.apply();
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
