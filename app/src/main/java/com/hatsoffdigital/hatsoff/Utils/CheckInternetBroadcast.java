package com.hatsoffdigital.hatsoff.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetBroadcast {


    public static boolean isNetworkAvilable(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return ( networkInfo !=null && networkInfo.isConnected());
    }

}
