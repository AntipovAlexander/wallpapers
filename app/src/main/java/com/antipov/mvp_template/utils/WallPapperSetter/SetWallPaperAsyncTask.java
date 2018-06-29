package com.antipov.mvp_template.utils.WallPapperSetter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;

class SetWallPaperAsyncTask extends AsyncTask<AsyncTaskParameterWrapper, Void, Boolean> {

    IOnWallPaperChanged onWallPaperChanged;

    public SetWallPaperAsyncTask(IOnWallPaperChanged onWallPaperChanged) {
        this.onWallPaperChanged = onWallPaperChanged;
    }

    @Override
    protected Boolean doInBackground(AsyncTaskParameterWrapper... wrapper) {
        Bitmap bitmap = wrapper[0].getBitmap();
        Context context = wrapper[0].getContext();

        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(context);
        try {
            myWallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess){
            onWallPaperChanged.onWallPaperChangedSuccess();
        } else {
            onWallPaperChanged.onWallPaperChangedFailure();
        }
    }
}
