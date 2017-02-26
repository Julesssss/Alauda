package com.gmail.julianrosser91.alauda.data;

import com.gmail.julianrosser91.alauda.data.api.ApiRequests;

public class DataHelper {

    public static void getSetsData(ApiRequests.APIResponseListener listener) {
        // todo - logic here for web / cache
        // check local or download?
        ApiRequests.getAllSets(listener);

    }

}
