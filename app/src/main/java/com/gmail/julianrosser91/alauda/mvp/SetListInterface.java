package com.gmail.julianrosser91.alauda.mvp;

import android.app.Activity;
import android.content.Intent;

import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public interface SetListInterface {

    interface View {

        void setData(ArrayList<Set> sets);

        void setMessage(String message);

        void showProgressBar(Boolean visible);

        void startActivity(Intent i);

        void startChooserActivity(Intent i);

        void setEmpty(boolean empty);
    }

    /**
     * Perhaps this should be split into two interfaces, (View --> Presenter & Model --> Presenter)
     */
    interface Presenter {

        // View --> Presenter

        void reattachView(View view);

        void detachView();

        void onViewClicked(Activity activity, android.view.View setListItemView);

        void onReloadFromServer();

        void exportDbPressed();

        // Model --> Presenter

        void onDataRetrieved(ArrayList<Set> sets);

        void onDataFailure(String message);

        void onActivityRestarted();
    }

    interface Model {

        void getSetListData(boolean fromServer);

        void toggleFavouriteSet(Set set);
    }
}
