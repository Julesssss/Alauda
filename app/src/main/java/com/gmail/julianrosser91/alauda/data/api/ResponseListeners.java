package com.gmail.julianrosser91.alauda.data.api;

import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public interface ResponseListeners {

    interface AllSetsResponseListener extends ResponseListener {
        void onDataLoaded(ArrayList<Set> data);
    }

    interface ImageResponseListener extends ResponseListener{
        void onImageLoaded(Set imageObject);
    }

    interface ResponseListener {
        void onFailure(String message);
    }
}
