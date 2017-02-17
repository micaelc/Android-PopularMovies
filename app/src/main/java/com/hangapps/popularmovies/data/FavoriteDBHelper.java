package com.hangapps.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mcampos on 13/02/2017.
 */

public class FavoriteDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "movies.db";
	private static final int DATABASE_VERSION = 3;

	public FavoriteDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		/*
		CREATE TABLE favorites (
			_ID                INTEGER        PRIMARY KEY AUTOINCREMENT,
			movie_id           INTEGER        NOT NULL,
			movie_title        TEXT           NOT NULL,
			movie_overview     TEXT           NOT NULL,
			movie_release_date TEXT,
			movie_vote_average NUMERIC (2, 1),
			movie_poster       BLOB
		);
		*/

		String CREATE_TABLE = "CREATE TABLE " +
				MovieFavoriteContract.FavoriteMovie.TABLE_NAME +" (" +
				MovieFavoriteContract.FavoriteMovie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				MovieFavoriteContract.FavoriteMovie.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
				MovieFavoriteContract.FavoriteMovie.COLUMN_TITLE + " TEXT NOT NULL, " +
				MovieFavoriteContract.FavoriteMovie.COLUMN_OVERVIEW + " TEXT, " +
				MovieFavoriteContract.FavoriteMovie.COLUMN_RELEASE_DATE + " TEXT, " +
				MovieFavoriteContract.FavoriteMovie.COLUMN_VOTE_AVERAGE + " REAL (2,1), " +
				MovieFavoriteContract.FavoriteMovie.COLUMN_POSTER_FULL_PATH + " TEXT " +
				");";

		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		//DROP TABLE IF EXISTS favorites;
		String UPDATE_TABLE = "DROP TABLE IF EXISTS " + MovieFavoriteContract.FavoriteMovie.TABLE_NAME + ";";
		db.execSQL(UPDATE_TABLE);
		onCreate(db);

	}
}
