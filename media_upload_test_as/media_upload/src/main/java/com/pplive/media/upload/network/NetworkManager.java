package com.pplive.media.upload.network;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

 

public class NetworkManager extends BroadcastReceiver {

	private static final String TAG = NetworkManager.class.getSimpleName();

	private static NetworkState sCurrentNetworkState = NetworkState.UNKNOWN;

	public static void init(Context context) {

		sCurrentNetworkState = getNetworkState(context);
		Log.w(TAG, "Network State: " + sCurrentNetworkState);
		// checkWifiSpeed(context);
	}

	private static NetworkState getNetworkState(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Service.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (null != wifi && null != mobile
				&& NetworkInfo.State.CONNECTED != wifi.getState()
				&& NetworkInfo.State.CONNECTED != mobile.getState()
				&& NetworkInfo.State.CONNECTING != mobile.getState()) {
			return NetworkState.DISCONNECTED;
		} else if (null != wifi
				&& (NetworkInfo.State.CONNECTED == wifi.getState() || NetworkInfo.State.CONNECTING == wifi
						.getState())) {
			return NetworkState.WIFI;
		} else if (null != mobile
				&& (NetworkInfo.State.CONNECTED == mobile.getState() || NetworkInfo.State.CONNECTING == mobile
						.getState())) {
			if (isFastMobileNetwork(context)) {
				return NetworkState.FAST_MOBILE;
			} else {
				return NetworkState.MOBILE;
			}
		}
		
		if (isNetworkAvailable(context)) {
			return NetworkState.UNKNOWN;
		}

		return NetworkState.DISCONNECTED;
	}

	public static NetworkState getCurrentNetworkState() {
		Log.w(TAG, "Current Network State: " + sCurrentNetworkState);
		return sCurrentNetworkState;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Service.CONNECTIVITY_SERVICE);

		NetworkInfo info = manager.getActiveNetworkInfo();

		return null != info && info.isConnectedOrConnecting();
	}

 

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean isGpsEnable(Context context) {
		LocationManager locationManager = ((LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		int type = telephonyManager.getNetworkType();
		switch (type) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false; // ~ 14-64 kbps
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return true; // ~ 1-2 Mbps
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true; // ~ 400-1000 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true; // ~ 600-1400 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return true; // ~ 5 Mbps
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return false; // ~ 100 kbps
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return true; // ~ 2-14 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return true; // ~ 700-1700 kbps
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return true; // ~ 10-20 Mbps
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return true; // ~ 1-23 Mbps
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return false; // ~ 25 kbps
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return true; // ~ 400-7000 kbps
		case TelephonyManager.NETWORK_TYPE_LTE:
			return true; // ~ 10+ Mbps
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}
	
	public static boolean isNetWorkLow(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		int type = telephonyManager.getNetworkType();
		switch (type) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return true; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return true; // ~ 14-64 kbps
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return true; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return false; // ~ 1-2 Mbps
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return false; // ~ 400-1000 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return false; // ~ 600-1400 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return false; // ~ 5 Mbps
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return true; // ~ 100 kbps
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return false; // ~ 2-14 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return false; // ~ 700-1700 kbps
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return false; // ~ 10-20 Mbps
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return false; // ~ 1-23 Mbps
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return true; // ~ 25 kbps
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return false; // ~ 400-7000 kbps
		case TelephonyManager.NETWORK_TYPE_LTE:
			return false; // ~ 10+ Mbps
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return true;
		default:
			return true;
		}
	}

	public enum NetworkState {
		WIFI, MOBILE, FAST_MOBILE, DISCONNECTED, UNKNOWN;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.w(TAG, "onReceive");
		Log.w(TAG, "action: " + intent.getAction());

		if (ConnectivityManager.CONNECTIVITY_ACTION == intent.getAction()) {
			NetworkState state = getNetworkState(context);

			Log.w(TAG, "state: " + state);

			if (null != state) {
				sCurrentNetworkState = state;
			}

			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Service.CONNECTIVITY_SERVICE);
			NetworkInfo wifi = manager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			Log.w(TAG, "Detail State: " + wifi.getDetailedState());

			if (NetworkState.WIFI == getCurrentNetworkState()
					&& NetworkInfo.DetailedState.CONNECTED == wifi
							.getDetailedState()) {

				// checkWifiSpeed(context);
			}

		} else if (WifiManager.NETWORK_STATE_CHANGED_ACTION == intent
				.getAction()) {

		}
	}

}
