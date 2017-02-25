package com.gmail.julianrosser91.alauda.api;


import com.gmail.julianrosser91.alauda.objects.SetArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterfaceApi {

    // Get all sets
    @GET("/api/sets/")
    Call<SetArray> getAllSets();

    // Get specific set by slug
//    @GET("api/sets/?")
//    Call<SetArray> getSetFromSlug(@Query("slug") String slug);


}
