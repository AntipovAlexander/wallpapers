package com.antipov.mvp_template.utils.WallPapperSetter;

import com.antipov.mvp_template.pojo.Picture;

public interface IOnWallPaperChanged {
    void onWallPaperChangedSuccess(Picture mPicture);

    void onWallPaperChangedFailure(String message);
}
