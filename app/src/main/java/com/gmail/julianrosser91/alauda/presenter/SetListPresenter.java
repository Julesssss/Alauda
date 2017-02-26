package com.gmail.julianrosser91.alauda.presenter;

import android.view.View;

import com.gmail.julianrosser91.alauda.data.SetListModel;
import com.gmail.julianrosser91.alauda.data.DatabaseHelper;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;

import java.util.ArrayList;

public class SetListPresenter implements SetListInterface.Presenter {

    private SetListInterface.View view;
    private SetListInterface.Model model;
    private ArrayList<Set> sets;

    public SetListPresenter(SetListInterface.View view) {
        this.sets = new ArrayList<>();
        this.view = view;
        this.model = new SetListModel(this);
        loadSetData();
    }

    /*
        View interface methods
     */

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
    public void exportDbPressed() {
        DatabaseHelper.exportDatabase();
    }

     /*
        Model interface methods
     */

    @Override
    public void onDataRetrieved(ArrayList<Set> data) {
        sets = data;
        view.showProgressBar(false);
        if (view != null) {
            view.setData(data);
        }
    }

    @Override
    public void onDataFailure(String message) {
        view.showProgressBar(false);
        view.setMessage(message);
    }

    private void loadSetData() {
        view.showProgressBar(true);
        model.getSetListData();
    }
}
