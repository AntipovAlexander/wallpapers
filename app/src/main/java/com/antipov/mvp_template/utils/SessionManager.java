package com.antipov.mvp_template.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AlexanderAntipov on 22.12.17.
 */

public class SessionManager {
    // Sharedpref file name
    private final String PREF_NAME = "ApexClubSession";
    // login or not login
    private final String IS_LOGIN = "IS_LOGIN";
    // Token
    private final String HEADER = "Authentication";
    // Full name
    private final String FULL_NAME = "full-name";
    // primary car
    private final String PRIMARY_CAR = "user-car";
    // avatar url
    private final String AVATAR = "avatar-url";
    // social network
    private final String SOCIAL = "social-network";
    // user id
    private final String ID = "user-id";
    int PRIVATE_MODE = 0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logInUser(String token, String name, String avatar, String network, String id, String primaryCar) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(HEADER, token);
        editor.putString(FULL_NAME, name);
        editor.putString(AVATAR, avatar);
        editor.putString(SOCIAL, network);
        editor.putString(ID, id);
        editor.putString(PRIMARY_CAR, primaryCar);
        editor.commit();
    }

    public void updateProfileData(String name, String picture, String primaryCar) {
        editor.putString(FULL_NAME, name);
        editor.putString(AVATAR, picture);
        editor.putString(PRIMARY_CAR, primaryCar);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(HEADER, "");
    }

    public String getUsername() {
        return sharedPreferences.getString(FULL_NAME, "");
    }

    public String getAvatar() {
        return sharedPreferences.getString(AVATAR, "");
    }

    public String getSocial() {
        return sharedPreferences.getString(SOCIAL, "");
    }

    public String getId() {
        return sharedPreferences.getString(ID, "");
    }

    public String getPrimaryCar() {
        return sharedPreferences.getString(PRIMARY_CAR, "no primary car");
    }

    public void removeSession() {
        editor.clear();
        editor.commit();
    }

    public void logout() {
        editor.putBoolean(IS_LOGIN, false);
        editor.remove(HEADER);
        editor.remove(FULL_NAME);
        editor.remove(AVATAR);
        editor.remove(SOCIAL);
        editor.commit();
    }

    public void updatePrimary(String car) {
        editor.putString(PRIMARY_CAR, car);
        editor.commit();
    }
}
