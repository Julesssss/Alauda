package com.gmail.julianrosser91.alauda.data;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.DeviceUtils;
import com.gmail.julianrosser91.alauda.data.api.ApiRequests;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SetListModel implements SetListInterface.Model, ApiRequests.AllSetsResponseListener, ApiRequests.ImageResponseListener {

    private final SetListInterface.Presenter presenter;

    public SetListModel(SetListInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSetListData() {
        if (DeviceUtils.isConnectedOrConnecting(Alauda.getInstance())) {
            ApiRequests.getAllSets(this);
        } else {
            getLocalSetsData();
        }
    }

    @Override
    public void onDataLoaded(ArrayList<Set> data) {
        // Save data to local database
        DatabaseHelper.saveAllSets(data);
        presenter.onDataRetrieved(data);
        loadImages(data);
    }

    private void loadImages(ArrayList<Set> sets) {
        for (Set set : sets) {
            if (set.getImageObjectEndpoint() != null && set.getImageUrl() == null) {
                ApiRequests.getImage(this, set); // todo - check this!
            }
        }
    }

    /*
     * After image objects are retrieved, we should add the url to the Set object
     */
    @Override
    public void onImageLoaded(Set set) {
        DatabaseHelper.updateSetObject(set);
        getLocalSetsData();
    }

    @Override
    public void onFailure(String message) {
        presenter.onDataFailure(message);
        getLocalSetsData();
    }

    private void getLocalSetsData() {
        ArrayList<Set> sets = new ArrayList<>();
        RealmResults<Set> realmResults = DatabaseHelper.getAllSets();
        for (Set set : realmResults) {
            sets.add(set);
        }
        presenter.onDataRetrieved(sets);
    }

}
