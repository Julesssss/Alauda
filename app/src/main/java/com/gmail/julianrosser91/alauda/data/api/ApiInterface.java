package com.gmail.julianrosser91.alauda.data.api;


import com.gmail.julianrosser91.alauda.data.model.Image;
import com.gmail.julianrosser91.alauda.data.model.SetArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    // Get all sets
    @GET("/api/sets/")
    Call<SetArray> getAllSets();

    @GET
    Call<Image> getImage(@Url String url);

}
