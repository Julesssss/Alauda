package com.gmail.julianrosser91.alauda.mvp;

import com.gmail.julianrosser91.alauda.data.model.Set;

public interface SetDetailInterface {

    interface View {

        void setData(Set set);

        void setMessage(String message);

    }

    /**
     * Perhaps this should be split into two interfaces, (View --> Presenter & Model --> Presenter)
     */
    interface Presenter {

        // View --> Presenter

        void reattachView(View view);

        void detachView();

        // Model --> Presenter

        void onDataRetrieved(Set set);

        void onDataFailure(String message);
    }

    interface Model {

        void getSetFromUid(String uid);
    }
}
