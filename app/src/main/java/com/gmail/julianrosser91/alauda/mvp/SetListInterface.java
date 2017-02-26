package com.gmail.julianrosser91.alauda.mvp;

import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public interface SetListInterface {

    interface View {

        void setData(ArrayList<Set> sets);

        void setMessage(String message);

        void showProgressBar(Boolean visible);
    }

    interface Presenter {

        void reattachView(View view);

        void detachView();

        void onSetClicked(android.view.View view);

        void testPressed();
    }
}
