package com.gmail.julianrosser91.alauda.data.api;

import android.util.Log;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Image;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.data.model.SetArray;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRequests {

    private static String TAG = ApiRequests.class.getSimpleName();

    public static void getAllSetsRxJava(final ResponseListeners.AllSetsResponseListener responseListener) {
        final ArrayList<Set> sets = new ArrayList<>();

        Alauda.getInstance().getVodInterfaceAPI().getAllSetsRxJava()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<SetArray, Observable<Set>>() {
                    @Override
                    public Observable<Set> apply(SetArray setArray) throws Exception {
                        return Observable.fromIterable(setArray.getSetsAsList());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Set>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(Set set) {
                        Log.d(TAG, "onNext: " + set.getTitle());
                        sets.add(set);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: ");
                        handleFailure(responseListener, t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        responseListener.onDataLoaded(sets);
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
