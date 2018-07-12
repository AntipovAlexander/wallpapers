package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;
import com.antipov.mvp_template.utils.shared.CurrentWallpaperPrefs;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;

import static com.antipov.mvp_template.common.Const.PORTRAIT;
import static com.antipov.mvp_template.common.Const.WALLPAPER;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class MainInteractorImpl extends BaseInteractor implements MainInteractor {
    private final CurrentWallpaperPrefs mPrefs;

    @Inject
    public MainInteractorImpl(SchedulerProvider provider, CurrentWallpaperPrefs currentWallpaperPrefs) {
        super(provider);
        this.mPrefs = currentWallpaperPrefs;
    }

    @Override
    public Observable<List<Picture>> getPictures() {
        Observable<List<Picture>> call =
                RetrofitUtils.getApi()
                        .create(Api.class)
                        .getPhotos(PORTRAIT, WALLPAPER, 20)
                        .map(pictures -> {
                            Picture picture = mPrefs.get();
                            if (picture != null) {
                                // if current picture is saved to prefs, geti it from there and put as
                                // first element of pictures list
                                pictures.add(0, picture);
                            }
                            return pictures;
                        });
        return call.subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }
}
