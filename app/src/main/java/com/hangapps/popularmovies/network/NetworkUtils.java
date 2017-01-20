package com.hangapps.popularmovies.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.utils.Constants;

/**
 * Created by mcampos on 20/01/2017.
 */

public class NetworkUtils {


	/**
	 * Method that will verify the internet connectivity
	 * In order to get this to work android.permission.ACCESS_NETWORK_STATE is needed
	 *
 	 * @param context
	 * @return True or False
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
			Log.w(Constants.APP_TAG, context.getString(R.string.internet_connection_msg));
			return false;
		}
		return true;
	}

}
