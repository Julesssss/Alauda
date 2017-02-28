package com.gmail.julianrosser91.alauda.data.api;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Image;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.data.model.SetArray;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRequests {

    public static void getAllSets(final ResponseListeners.AllSetsResponseListener responseListener) {
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
                handleFailure(responseListener, t);
            }

        });
    }

    public static void getImage(final ResponseListeners.ImageResponseListener responseListener, final Set set) {
        Alauda.getInstance().getVodInterfaceAPI().getImage(set.getImageObjectEndpoint()).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (response.isSuccessful()) {
                    // Update set object with imageUrl
                    Image image = response.body();
                    set.setImageUrl(image.getUrl());
                    responseListener.onImageLoaded(set);
                } else {
                    responseListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                handleFailure(responseListener, t);
            }
        });
    }

    private static void handleFailure(ResponseListeners.ResponseListener responseListener, Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            responseListener.onFailure(Alauda.getInstance().getString(R.string.message_error_unknown_host));
        } else if (throwable instanceof SocketTimeoutException) {
            responseListener.onFailure(Alauda.getInstance().getString(R.string.message_error_connection_timeout));
        } else {
            responseListener.onFailure(throwable.getMessage());
        }
        throwable.printStackTrace();
    }

}
