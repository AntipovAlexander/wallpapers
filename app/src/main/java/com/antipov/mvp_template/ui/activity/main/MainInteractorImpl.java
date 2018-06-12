package com.antipov.mvp_template.ui.activity.main;

import com.antipov.mvp_template.Const;
import com.antipov.mvp_template.api.Api;
import com.antipov.mvp_template.api.RetrofitUtils;
import com.antipov.mvp_template.ui.activity.base.BaseInteractor;
import com.antipov.mvp_template.pojo.Picture;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public class MainInteractorImpl extends BaseInteractor implements MainInteractor {

    @Inject
    public MainInteractorImpl() {
    }

    @Override
    public Observable<List<Picture>> getPictures() {
        Observable<List<Picture>> call = RetrofitUtils.getApi().
                create(Api.class).getFeed();
        return call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(Const.RETRY_COUNT);
    }
}
