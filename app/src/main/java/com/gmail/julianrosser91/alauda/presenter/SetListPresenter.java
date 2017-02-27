package com.gmail.julianrosser91.alauda.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gmail.julianrosser91.alauda.Constants;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.Utils;
import com.gmail.julianrosser91.alauda.data.DatabaseHelper;
import com.gmail.julianrosser91.alauda.data.SetListModel;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;
import com.gmail.julianrosser91.alauda.view.SetDetailActivity;

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
     *   View interface methods
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
    public void onSetClicked(Activity activity, View setListItemView) {
        Set set = (Set) setListItemView.getTag();

        if (Utils.isEmpty(set.getBody())) {
            view.setMessage(activity.getString(R.string.message_error_set_details_unavailable));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_UID, set.getUid());

            Intent intent = new Intent(activity, SetDetailActivity.class);
            intent.putExtras(bundle);
            view.startActivity(intent);
        }
    }

    @Override
    public void testPressed() {
        loadSetData();
    }

    @Override
    public void exportDbPressed() {
        view.startChooserActivity(DatabaseHelper.getExportDatabaseIntent());
    }

     /*
      *   Model interface methods
      */

    @Override
    public void onDataRetrieved(ArrayList<Set> data) {
        sets = data;
        if (view != null) {
            view.showProgressBar(false);
            view.setData(data);
        }
    }

    @Override
    public void onDataFailure(String message) {
        if (view != null) {
            view.showProgressBar(false);
            view.setMessage(message);
        }
    }

    private void loadSetData() {
        if (view != null) {
            view.showProgressBar(true);
            model.getSetListData();
        }
    }
}
