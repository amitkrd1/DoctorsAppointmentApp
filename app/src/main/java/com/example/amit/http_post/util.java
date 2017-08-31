package com.example.amit.http_post;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AMIT on 8/30/2017.
 */


public class util {
    private static NetworkInfo networkInfo;

    public static boolean isConnected(Context context){

        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);


        try {
            networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }catch (Exception e){


            e.printStackTrace();
        }


        if(networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected()){


            return true;
        }


        networkInfo=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if(networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected()){


            return true;
        }

        return false;
    }


}
