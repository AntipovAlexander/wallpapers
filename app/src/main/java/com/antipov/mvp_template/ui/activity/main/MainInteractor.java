package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.IBaseInteractor;

import java.util.List;

import rx.Observable;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface MainInteractor extends IBaseInteractor {
    Observable<List<Picture>> getPictures();
}
