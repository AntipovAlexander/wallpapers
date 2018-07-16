package com.antipov.mvp_template.utils.WallPapperSetter;

import com.antipov.mvp_template.pojo.Picture;

import java.io.Serializable;

public interface IOnWallPaperChanged extends Serializable{
    void onWallPaperChangedSuccess(Picture mPicture);

    void onWallPaperChangedFailure(String message);
}
