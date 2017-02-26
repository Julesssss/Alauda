package com.gmail.julianrosser91.alauda.data;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.DeviceUtils;
import com.gmail.julianrosser91.alauda.data.api.ApiRequests;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
            getLocalData();
        }
    }

    @Override
    public void onDataLoaded(ArrayList<Set> data) {
        // Save data to local database
        Realm realmDatabase = getRealmDatabase();
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealmOrUpdate(data);
        realmDatabase.commitTransaction();
        presenter.onDataRetrieved(data);

        loadImages(data);
    }

    private void loadImages(ArrayList<Set> sets) {
        for (Set set : sets) {
            if (set.getImageObjectEndpoint() != null) {
                ApiRequests.getImage(this, set);
            }
        }
    }

    /*
     * After image objects are retrieved, we should add the url to the Set object
     */
    @Override
    public void onImageLoaded(Set set) {
        Realm realmDatabase = getRealmDatabase();
        realmDatabase.beginTransaction();
        realmDatabase.insertOrUpdate(set);
        realmDatabase.commitTransaction();
        getLocalData();
    }

    @Override
    public void onFailure(String message) {
        presenter.onDataFailure(message);
        getLocalData();
    }

    private void getLocalData() {
        ArrayList<Set> sets = new ArrayList();
        Realm realmDatabase = getRealmDatabase();
        RealmResults<Set> realmResults = realmDatabase.where(Set.class).findAll();
        for (Set set : realmResults) {
            sets.add(set);
        }
        presenter.onDataRetrieved(sets);
    }

    private Realm getRealmDatabase() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }
}
