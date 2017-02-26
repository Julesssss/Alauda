package com.gmail.julianrosser91.alauda.data.api;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.data.model.SetArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRequests {

    public interface APIResponseListener {
        void onDataLoaded(ArrayList<Set> data);
        void onFailure(String message);
    }

    public static void getAllSets(final APIResponseListener responseListener) {
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
}
