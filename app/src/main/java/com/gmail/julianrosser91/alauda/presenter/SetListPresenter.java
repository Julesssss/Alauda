package com.gmail.julianrosser91.alauda.presenter;

import android.view.View;

import com.gmail.julianrosser91.alauda.mvp.SetListInterface;
import com.gmail.julianrosser91.alauda.data.api.ApiRequests;
import com.gmail.julianrosser91.alauda.data.DataHelper;
import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public class SetListPresenter implements SetListInterface.Presenter {

    private SetListInterface.View view;
    private ArrayList<Set> sets;

    public SetListPresenter(SetListInterface.View view) {
        this.sets = new ArrayList<>();
        this.view = view;
        loadSetData();
    }

    @Override
    public void reattachView(SetListInterface.View view) {
        this.view = view;
        view.setData(sets);
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onSetClicked(View setListItemView) {
        Set set = (Set) setListItemView.getTag();
        view.setMessage(set.getTitle());
    }

    @Override
    public void testPressed() {
        loadSetData();
    }

    @Override
    public void onDataRetrieved() {

    }

    private void loadSetData() {
        view.showProgressBar(true);
        DataHelper.getSetsData(new ApiRequests.APIResponseListener() {
            @Override
            public void onDataLoaded(ArrayList<Set> data) {
                sets = data;
                view.showProgressBar(false);
                if (view != null) {
                    view.setData(data);
                }
            }

            @Override
            public void onFailure(String message) {
                view.showProgressBar(false);
                view.setMessage(message);
            }
        });
    }
}
