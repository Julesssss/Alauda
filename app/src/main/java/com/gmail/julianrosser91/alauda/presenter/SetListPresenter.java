package com.gmail.julianrosser91.alauda.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gmail.julianrosser91.alauda.Alauda;
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
        getDataSet(true);
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
    public void onViewClicked(Activity activity, View view) {
        Set set = (Set) view.getTag();
        int id = view.getId();
        if (id == R.id.imageview_favourite) {
            onFavouriteIconClicked(set);
        } else {
            onListItemClicked(view, set);
        }
    }

    private void onFavouriteIconClicked(Set set) {
        onFavouriteToggleSaved(!set.isFavourite());
        model.toggleFavouriteSet(set);
    }

    private void onListItemClicked(View view, Set set) {
        if (Utils.isEmpty(set.getBody())) {
            this.view.setMessage(view.getContext().getString(R.string.message_error_set_details_unavailable));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_UID, set.getUid());
            Intent intent = new Intent(view.getContext(), SetDetailActivity.class);
            intent.putExtras(bundle);
            this.view.startActivity(intent);
        }
    }

    @Override
    public void testPressed() {
        getDataSet(true);
    }

    @Override
    public void exportDbPressed() {
        view.startChooserActivity(DatabaseHelper.getExportDatabaseIntent());
    }

    @Override
    public void onActivityRestarted() {
        getDataSet(false);
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

    private void getDataSet(boolean fromServer) {
        if (view != null) {
            view.showProgressBar(true);
            model.getSetListData(fromServer);
        }
    }

    /*
     * Should refactor this method in BasePresenter class
     */
    private void onFavouriteToggleSaved(boolean favourite) {
        if (view != null) {
            if (favourite) {
                view.setMessage(Alauda.getInstance().getString(R.string.message_favourites_add));
            } else {
                view.setMessage(Alauda.getInstance().getString(R.string.message_favourites_remove));
            }
        }
    }

}
