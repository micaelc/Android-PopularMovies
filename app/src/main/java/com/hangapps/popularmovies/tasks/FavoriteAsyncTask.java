package com.hangapps.popularmovies.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.data.MovieFavoriteContract;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.utils.Constants;

import java.util.List;

/**
 * Created by mcampos on 19/02/2017.
 */

public class FavoriteAsyncTask extends AsyncTask<Void, Void, List<Movie>> {

	private Context mContext;
	private List<Movie> mMovies;

	public FavoriteAsyncTask(Context context, List<Movie> movies) {
		mContext = context;
		mMovies = movies;
	}

	@Override
	protected List<Movie> doInBackground(Void... params) {
		Cursor cursor = null;

		try {
			cursor = mContext.getContentResolver().query(
					MovieFavoriteContract.FavoriteMovie.CONTENT_URI,
					null,
					null,
					null,
					null);
		} catch (Exception e) {
			Log.e(Constants.APP_TAG, mContext.getResources().getString(R.string.error_async_fail));
			e.printStackTrace();
			return null;
		}

		if (cursor != null){
			while(cursor.moveToNext()){
				Movie movie = new Movie(
						cursor.getInt(cursor.getColumnIndex(MovieFavoriteContract.FavoriteMovie.COLUMN_MOVIE_ID)),
						cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.FavoriteMovie.COLUMN_TITLE)),
						cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.FavoriteMovie.COLUMN_POSTER_PATH)),
						cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.FavoriteMovie.COLUMN_OVERVIEW)),
						cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.FavoriteMovie.COLUMN_RELEASE_DATE)),
						cursor.getFloat(cursor.getColumnIndex(MovieFavoriteContract.FavoriteMovie.COLUMN_VOTE_AVERAGE)));

				mMovies.add(movie);
			}
		}
		cursor.close();
		return mMovies;
	}

	@Override
	protected void onPostExecute(List<Movie> movies) {
		super.onPostExecute(movies);
	}
}
