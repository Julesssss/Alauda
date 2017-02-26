package com.gmail.julianrosser91.alauda.mvp;

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
    }

    /**
     * Perhaps this should be split into two interfaces, (View --> Presenter & Model --> Presenter)
     */
    interface Presenter {

        // View --> Presenter

        void reattachView(View view);

        void detachView();

        void onSetClicked(android.view.View view);

        void testPressed();

        void exportDbPressed();

        // Model --> Presenter

        void onDataRetrieved(ArrayList<Set> sets);

        void onDataFailure(String message);
    }

    interface Model {

        void getSetListData();
    }
}
