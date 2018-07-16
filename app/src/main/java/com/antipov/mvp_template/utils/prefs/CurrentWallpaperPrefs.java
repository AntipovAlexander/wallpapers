package com.antipov.mvp_template.utils.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Picture;

public class CurrentWallpaperPrefs {
    private final SharedPreferences sharedPref;
    private final Context context;

    public CurrentWallpaperPrefs(Context context) {
        this.sharedPref = context.getSharedPreferences(Const.APP_PREFERENCES_CURRENT_WALLPAPER, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void save(String id,
                     String pictureSmallUrl,
                     String pictureFullUrl,
                     String htmlLink,
                     String userName,
                     String userBio,
                     String userLocation) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Const.Prefs.ID, id);
        editor.putString(Const.Prefs.SMALL, pictureSmallUrl);
        editor.putString(Const.Prefs.FULL, pictureFullUrl);
        editor.putString(Const.Prefs.USERNAME, userName);
        editor.putString(Const.Prefs.USERBIO, userBio);
        editor.putString(Const.Prefs.USERNAME, userLocation);
        editor.putString(Const.Prefs.HTML_LINK, htmlLink);
        editor.apply();
    }

    public Picture get() {
        Picture picture = new Picture();
        Picture.Urls urls = picture.new Urls();
        Picture.User user = picture.new User(sharedPref.getString(Const.Prefs.USERNAME, ""));

        Picture.Links links = picture.new Links();
        links.setHtml(sharedPref.getString(Const.Prefs.HTML_LINK, ""));

        picture.setLinks(links);

        String id = sharedPref.getString(Const.Prefs.ID, "");
        if (id.isEmpty()) return null;

        picture.setId(id);

        urls.setRegular(sharedPref.getString(Const.Prefs.FULL, ""));
        urls.setSmall(sharedPref.getString(Const.Prefs.SMALL, ""));
        picture.setUrls(urls);

        user.setBio(sharedPref.getString(Const.Prefs.USERBIO, ""));
        user.setLocation(sharedPref.getString(Const.Prefs.USERLOCATION, ""));

        picture.setUser(user);
        picture.setCurrent(true);

        return picture;
    }
}
