package com.gmail.julianrosser91.alauda.data;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetDetailInterface;

public class SetDetailModel implements SetDetailInterface.Model {

    private final SetDetailInterface.Presenter presenter;

    public SetDetailModel(SetDetailInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getSetFromUid(String uid) {
        Set set = DatabaseHelper.getSetFromUid(uid);
        if (set != null) {
            presenter.onDataRetrieved(set);
        } else {
            presenter.onDataFailure(Alauda.getInstance().getString(R.string.message_error_database_no_matching_uid));
        }
    }

    @Override
    public void toggleFavourite(Set set) {
        set = DatabaseHelper.toggleFavourite(set);
        presenter.onFavouriteToggleSaved(set);
    }
}
