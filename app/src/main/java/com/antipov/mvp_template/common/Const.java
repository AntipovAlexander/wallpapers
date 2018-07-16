package com.antipov.mvp_template.common;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class Const {
    public static final String BASE_URL = "https://api.unsplash.com/";
    public static final String APP_PREFERENCES = "wallpapers-shared-prefs";
    public static final short RETRY_COUNT = 0;
    public static final String PORTRAIT = "portrait";
    public static final String WALLPAPER = "wallpaper";
    public static final String APP_PREFERENCES_CURRENT_WALLPAPER = "wallpapers-shared-prefs-current";

    public static class Args {
        public static final String ID = "id";
        public static final String CUSTOM = "custom";
        public static final String RANDOM = "random";
        public static final String KEYWORD = "keyword";
        public static final String TAGS = "tags";
        public static final String PICTURE = "picture";
        public static final String BITMAP = "bitmap";
        public static final String LISTENER = "listener";
    }

    public static class Prefs {
        public static final String ID = "id";
        public static final String SMALL = "small-pic";
        public static final String FULL = "full-pic";
        public static final String USERNAME = "user-name";
        public static final String USERLOCATION = "user-location";
        public static final String USERBIO = "user-bio";
    }
}
