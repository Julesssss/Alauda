package com.gmail.julianrosser91.alauda.presenter;

import android.view.View;

import com.gmail.julianrosser91.alauda.mvp.SetListMVPInterface;
import com.gmail.julianrosser91.alauda.data.api.ApiRequests;
import com.gmail.julianrosser91.alauda.data.DataHelper;
import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public class SetListPresenter implements SetListMVPInterface.Presenter {

    private SetListMVPInterface.View view;
    private ArrayList<Set> sets;

    public SetListPresenter() {
        sets = new ArrayList<>();
    }

    @Override
    public void attachView(SetListMVPInterface.View view) {
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

    private void loadSetData() {
        DataHelper.getSetsData(new ApiRequests.APIResponseListener() {
            @Override
            public void onDataLoaded(ArrayList<Set> data) {
                sets = data;
                if (view != null) {
                    view.setData(data);
                }
            }

            @Override
            public void onFailure(String message) {
                view.setMessage(message);
            }
        });
    }
}
