package com.antipov.mvp_template.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.antipov.mvp_template.R;

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
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
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
        return Integer.valueOf(
                sharedPref
                .getString(
                        context.getString(R.string.prefs_key_frequency),
                        "1"
                ));
    }

    public boolean isLoadOnlyWhenWifi(){
        return sharedPref
                .getBoolean(
                        context.getString(R.string.prefs_key_onlywifi),
                        false
                );
    }

    public Set<String> getWallpaperTags(){
        return sharedPref
                .getStringSet(
                        context.getString(R.string.prefs_key_tags),
                        new HashSet<>()
                );
    }
}
