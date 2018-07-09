package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.base.BaseInteractor;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

import static com.antipov.mvp_template.common.Const.PORTRAIT;
import static com.antipov.mvp_template.common.Const.WALLPAPER;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class MainInteractorImpl extends BaseInteractor implements MainInteractor {

    @Inject
    public MainInteractorImpl(SchedulerProvider provider) {
        super(provider);
    }

    @Override
    public Observable<List<Picture>> getPictures() {
        Observable<List<Picture>> call = RetrofitUtils.getApi().
                create(Api.class).getPhotos(PORTRAIT, WALLPAPER, 20);
        return call.subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }
}
