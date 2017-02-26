package com.gmail.julianrosser91.alauda.data.api;


import com.gmail.julianrosser91.alauda.data.model.SetArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    // Get all sets
    @GET("/api/sets/")
    Call<SetArray> getAllSets();

    // Get specific set by slug
//    @GET("api/sets/?")
//    Call<SetArray> getSetFromSlug(@Query("slug") String slug);


}
