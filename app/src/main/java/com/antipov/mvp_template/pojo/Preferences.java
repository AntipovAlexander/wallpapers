package com.antipov.mvp_template.pojo;

import java.util.Set;

public class Preferences {
    private boolean isRandom;
    private boolean isCustom;
    private boolean isOnlyWiFi;
    private Set<String> wallpaperTags;
    private String wallpaperKey;
    private int frequency;

    public Preferences(boolean isRandom, boolean isCustom, boolean isOnlyWiFi, Set<String> wallpaperTags, String wallpaperKey, int frequency) {
        this.isRandom = isRandom;
        this.isCustom = isCustom;
        this.isOnlyWiFi = isOnlyWiFi;
        this.wallpaperTags = wallpaperTags;
        this.wallpaperKey = wallpaperKey;
        this.frequency = frequency;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public void setRandom(boolean random) {
        isRandom = random;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public boolean isOnlyWiFi() {
        return isOnlyWiFi;
    }

    public void setOnlyWiFi(boolean onlyWiFi) {
        isOnlyWiFi = onlyWiFi;
    }

    public Set<String> getWallpaperTags() {
        return wallpaperTags;
    }

    public void setWallpaperTags(Set<String> wallpaperTags) {
        this.wallpaperTags = wallpaperTags;
    }

    public String getWallpaperKey() {
        return wallpaperKey;
    }

    public void setWallpaperKey(String wallpaperKey) {
        this.wallpaperKey = wallpaperKey;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
