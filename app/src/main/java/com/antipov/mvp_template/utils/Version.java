package com.antipov.mvp_template.utils;

/**
 * class for checking api version in runtime
 * prefer using this instead Build.VERSION.SDK_INT to have ability mock it in unit tests
 */
public class Version {
    /**
     * @return api version
     */
    public int getApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * @param thisVersion version to compare
     * @return compare result
     */
    public boolean isApiVersionGraterOrEqual(int thisVersion) {
        return android.os.Build.VERSION.SDK_INT >= thisVersion;
    }
}