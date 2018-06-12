package com.antipov.mvp_template.api;

import com.antipov.mvp_template.pojo.Picture;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface Api {
    @GET("photos")
    Observable<List<Picture>> getFeed();
}
