package com.antipov.mvp_template.ui.activity.photo_detail;

import com.antipov.mvp_template.Const;
import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.base.BaseInteractor;
import com.antipov.mvp_template.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class PhotoDetailInteractorImpl extends BaseInteractor implements PhotoDetailInteractor {

    @Inject
    public PhotoDetailInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }

    @Override
    public Observable<Picture> getPicture(String id) {
        Observable<Picture> call = RetrofitUtils.getApi().
                create(Api.class).getPicture(id);
        return call.subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }
}
