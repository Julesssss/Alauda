package com.gmail.julianrosser91.alauda.data.api;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.data.model.ImageObject;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.data.model.SetArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRequests {

    public static void getAllSets(final AllSetsResponseListener responseListener) {
        Alauda.getInstance().getVodInterfaceAPI().getAllSets().enqueue(new Callback<SetArray>() {
            @Override
            public void onResponse(Call<SetArray> call, Response<SetArray> response) {
                if (response.isSuccessful()) {
                    SetArray setArray = response.body();
                    responseListener.onDataLoaded(setArray.getSets());
                } else {
                    responseListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<SetArray> call, Throwable t) {
                responseListener.onFailure(t.getMessage());
                t.printStackTrace();
            }

        });
    }

    public static void getImage(final ImageResponseListener responseListener, final Set set) {
        Alauda.getInstance().getVodInterfaceAPI().getImage(set.getImageObjectEndpoint()).enqueue(new Callback<ImageObject>() {
            @Override
            public void onResponse(Call<ImageObject> call, Response<ImageObject> response) {
                if (response.isSuccessful()) {
                    // Update set object with imageUrl
                    ImageObject imageObject = response.body();
                    set.setUrl(imageObject.getUrl());
                    responseListener.onImageLoaded(set);
                } else {
                    responseListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ImageObject> call, Throwable t) {
                responseListener.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public interface AllSetsResponseListener {
        void onDataLoaded(ArrayList<Set> data);

        void onFailure(String message);
    }

    public interface ImageResponseListener {
        void onImageLoaded(Set imageObject);

        void onFailure(String message);
    }

}
