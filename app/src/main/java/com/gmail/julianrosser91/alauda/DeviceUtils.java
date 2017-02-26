package com.gmail.julianrosser91.alauda;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DeviceUtils {

    public static boolean isConnectedOrConnecting(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

}
