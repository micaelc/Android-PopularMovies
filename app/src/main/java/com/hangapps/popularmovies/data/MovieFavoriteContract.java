package com.hangapps.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mcampos on 12/02/2017.
 */

public class MovieFavoriteContract {

	// URI components
	public static final String AUTHORITY = "com.hangapps.popularmovies";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	public static final String PATH_FAVORITES = "favorites";

	public static final class FavoriteMovie implements BaseColumns {

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

		public static final String TABLE_NAME = "favorites";
		public static final String COLUMN_MOVIE_ID = "movie_id";
		public static final String COLUMN_TITLE = "movie_title";
		public static final String COLUMN_OVERVIEW = "movie_overview";
		public static final String COLUMN_RELEASE_DATE = "movie_release_date";
		public static final String COLUMN_VOTE_AVERAGE = "movie_vote_average";
		public static final String COLUMN_POSTER = "movie_poster";

	}
}
