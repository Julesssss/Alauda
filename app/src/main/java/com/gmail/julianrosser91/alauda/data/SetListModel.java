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

    /*
     * We need to minimise the amount of API calls. Here we ensure that we only retrieve an image object
     * once, as when it is returned we save the url to the Set object. To prevent out of dat images being
     * used, we could reload images after a certain amount of time has passed.
     */
    private void loadImages(ArrayList<Set> sets) {
        for (Set set : sets) {
            if (set.getImageObjectEndpoint() != null && set.getImageUrl() == null) {
                ApiRequests.getImage(this, set);
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
