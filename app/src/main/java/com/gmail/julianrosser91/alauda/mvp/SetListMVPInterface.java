package com.gmail.julianrosser91.alauda.mvp;

import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public interface SetListMVPInterface {

    interface View {

        void setData(ArrayList<Set> sets);

        void setMessage(String message);
    }

    interface Presenter {

        void attachView(View view);

        void detachView();

        void onSetClicked(android.view.View view);

        void testPressed();
    }
}
