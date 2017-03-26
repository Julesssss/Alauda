package com.gmail.julianrosser91.alauda.data;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.DeviceUtils;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.api.ApiRequests;
import com.gmail.julianrosser91.alauda.data.api.ResponseListeners;
import com.gmail.julianrosser91.alauda.data.model.Favourite;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;

import java.util.ArrayList;

import io.realm.RealmResults;

public class SetListModel implements SetListInterface.Model, ResponseListeners.AllSetsResponseListener, ResponseListeners.ImageResponseListener {

    private final SetListInterface.Presenter presenter;

    public SetListModel(SetListInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSetListData(boolean fromServer) {
        if (fromServer) {
            if (DeviceUtils.isConnectedOrConnecting(Alauda.getInstance())) {
                ApiRequests.getAllSetsRxJava(this);
            } else {
                presenter.onDataFailure(Alauda.getInstance().getString(R.string.message_error_no_network));
                getLocalSetsData();
            }
        } else {
            getLocalSetsData();
        }
    }

    @Override
    public void toggleFavouriteSet(Set set) {
        DatabaseHelper.toggleFavourite(set);
        getLocalSetsData();
    }

    /*
     * Should improve this by using an Async task to retrieve data, add favourites and save to local
     * database.
     */
    @Override
    public void onDataLoaded(ArrayList<Set> data) {
        RealmResults<Favourite> favourites = DatabaseHelper.getFavourites();

        // Update favourite field from local favourites
        for (Favourite fav : favourites) {
            if (fav.isFavourite()) {
                for (Set set : data) {
                    if (fav.getUid().equals(set.getUid())) {
                        set.setFavourite(true);
                    }
                }
            }
        }

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
