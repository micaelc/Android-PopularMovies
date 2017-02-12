package com.hangapps.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by mcampos on 12/02/2017.
 */

public class MovieFavoriteContract {

	public static final class FavoriteMovie implements BaseColumns {
		public static final String TABLE_NAME = "favoriteMovies";
		public static final String COLUMN_MOVIE_ID = "movie_id";
		public static final String COLUMN_TITLE = "movie_title";
		public static final String COLUMN_OVERVIEW = "movie_overview";
		public static final String COLUMN_RELEASE_DATE = "movie_release_date";
		public static final String COLUMN_VOTE_AVERAGE = "movie_vote_average";
		public static final String COLUMN_POSTER = "movie_poster";

	}
}
