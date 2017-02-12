package com.hangapps.popularmovies.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hangapps.popularmovies.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mcampos on 12/02/2017.
 */

public class TmdbService {

	public static TmdbAip createService(){

		// GSON

		// OkHttpClient
		OkHttpClient httpClient = new OkHttpClient.Builder()
				.addNetworkInterceptor(new StethoInterceptor())
				.build();

		// Retrofit
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Constants.APIConstants.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient)
				.build();

		// Create
		return retrofit.create(TmdbAip.class);

	}
}
