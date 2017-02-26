package com.gmail.julianrosser91.alauda.data;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.DeviceUtils;
import com.gmail.julianrosser91.alauda.data.api.ApiRequests;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class SetListModel implements SetListInterface.Model, ApiRequests.APIResponseListener {

    private final SetListInterface.Presenter presenter;

    public SetListModel(SetListInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSetListData() {
        if (DeviceUtils.isConnectedOrConnecting(Alauda.getInstance())) {
            ApiRequests.getAllSets(this);
        } else {
            getLocalDatabase();
        }
    }

    @Override
    public void onDataLoaded(ArrayList<Set> data) {
        // Save data to local database
        Realm realmDatabase = Realm.getDefaultInstance();
        realmDatabase.beginTransaction();
        realmDatabase.copyToRealmOrUpdate(data);
        realmDatabase.commitTransaction();
        presenter.onDataRetrieved(data);
    }

    @Override
    public void onFailure(String message) {
        presenter.onDataFailure(message);
        getLocalDatabase();
    }

    private void getLocalDatabase() {
        ArrayList<Set> sets = new ArrayList();
        Realm realmDatabase = Realm.getDefaultInstance();
        RealmResults<Set> realmResults = realmDatabase.where(Set.class).findAll();

        for (Set set : realmResults) {
            sets.add(set);
        }
        presenter.onDataRetrieved(sets);
    }
}
