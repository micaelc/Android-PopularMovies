package com.hangapps.popularmovies.utils;

/**
 * Created by mcampos on 18/01/2017.
 */

public class Constants {

	public final static String APP_TAG = "POPULAR_MOVIE";

	public final static class APIConstants{
		public static final String BASE_URL = "http://api.themoviedb.org/";
		public static final String APP_KEY_QUERY_API_KEY = "api_key";
		public static final String APP_KEY_QUERY_SORT_BY = "sort_by";
		public static final String APP_KEY_QUERY_PAGE = "page";
		public static final String SORT_POPULARITY = "popularity.desc";
		public static final String SORT_RATING = "vote_average.desc";
		public static final String SORT_FAVORITE = "favorite";
		public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
		public final static String IMAGE_SMALL_SIZE = "w185";
	}

	public final static class MyPreferences{
		public static final String MY_FREFS_NAME = "my_shared_prefs";
		public static final String PREF_SORT_ORDER = "sort_order";

	}
}
