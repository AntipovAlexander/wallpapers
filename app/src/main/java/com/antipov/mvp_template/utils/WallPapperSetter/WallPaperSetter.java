package com.antipov.mvp_template.utils.WallPapperSetter;

import android.content.Context;
import android.graphics.Bitmap;

public class WallPaperSetter {
    public void setWallPaper(Context context, Bitmap bitmap, IOnWallPaperChanged onWallPaperChanged){
        AsyncTaskParameterWrapper wrapper = new AsyncTaskParameterWrapper(bitmap, context);
        SetWallPaperAsyncTask setWallPaperAsyncTask = new SetWallPaperAsyncTask(onWallPaperChanged);
        setWallPaperAsyncTask.execute(wrapper);
    }
}
