package com.antipov.mvp_template.utils.WallPapperSetter;

import android.content.Context;
import android.graphics.Bitmap;

public class AsyncTaskParameterWrapper{
    private Bitmap bitmap;
    private Context context;

    public AsyncTaskParameterWrapper(Bitmap bitmap, Context context) {
        this.bitmap = bitmap;
        this.context = context;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
