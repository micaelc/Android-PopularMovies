package com.hangapps.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by mcampos on 13/02/2017.
 */

public class FavoriteContentProvider extends ContentProvider {

	public static final int FAVORITES = 100;

	private static UriMatcher sUriMatcher = buildUriMatcher();

	private FavoriteDBHelper mDBHelper;


	public static final UriMatcher buildUriMatcher() {

		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(MovieFavoriteContract.AUTHORITY, MovieFavoriteContract.PATH_FAVORITES, FAVORITES);

		return uriMatcher;
	}


	@Override
	public boolean onCreate() {
		Context context = getContext();
		mDBHelper = new FavoriteDBHelper(context);
		return true;
	}

	@Nullable
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Nullable
	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mDBHelper.getWritableDatabase();

		int match = sUriMatcher.match(uri);
		Uri returnUri;

		switch (match) {
			case FAVORITES:
				long id = db.insert(MovieFavoriteContract.FavoriteMovie.TABLE_NAME, null, values);
				if (id > 0){
					returnUri = ContentUris.withAppendedId(MovieFavoriteContract.FavoriteMovie.CONTENT_URI, id);
				} else throw new android.database.SQLException("Failed to insert row into " + uri);
				break;
			default:
				throw new UnsupportedOperationException("Unknown Uri " + uri);
		}

		// Notify the resolver if the uri has been changed, and return the newly inserted URI
		getContext().getContentResolver().notifyChange(uri, null);

		return returnUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}
}