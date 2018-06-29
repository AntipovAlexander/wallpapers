package com.antipov.mvp_template.api;

import com.antipov.mvp_template.pojo.Picture;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface Api {
    @GET("photos/random")
    Observable<List<Picture>> getPhotos(@Query("orientation") String orientation,
                                        @Query("query") String query,
                                        @Query("count") int count);


    @GET("photos/{id}")
    Observable<Picture> getPicture(@Path("id") String id);
}
