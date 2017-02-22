package com.hangapps.popularmovies.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hangapps.popularmovies.BuildConfig;
import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.adapters.TrailerAdapter;
import com.hangapps.popularmovies.data.MovieFavoriteContract;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.models.Trailer;
import com.hangapps.popularmovies.models.TrailerResponse;
import com.hangapps.popularmovies.network.NetworkUtils;
import com.hangapps.popularmovies.network.TmdbAip;
import com.hangapps.popularmovies.network.TmdbService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hangapps.popularmovies.data.MovieFavoriteContract.FavoriteMovie.COLUMN_MOVIE_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {

	public static final String ARG_MOVIE_ITEM = "movieItem";

	@BindView(R.id.movie_title)
	TextView mMovieTitle;
	@BindView(R.id.movie_release_date)
	TextView mMovieReleaseDate;
	@BindView(R.id.movie_poster)
	ImageView mMoviePoster;
	@BindView(R.id.movie_rating)
	TextView mMovieVoteAverage;
	@BindView(R.id.movie_synopsis)
	TextView mMovieSynopsis;
	@BindView(R.id.bt_favorite)
	Button mFavorite;

	@BindView(R.id.rv_trailers)
	RecyclerView mTrailersRecyclerView;

	private List<Trailer> mTrailers = new ArrayList<>();
	private TmdbAip service;
	private TrailerAdapter mTrailerAdapter;
	private LinearLayoutManager mTrailerLayoutManager;

	private Movie mMovie;
	private Drawable icon;

	public MovieDetailsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_MOVIE_ITEM)) {
			mMovie = getArguments().getParcelable(ARG_MOVIE_ITEM);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.movie_details, container, false);
		ButterKnife.bind(this, rootView);

		if (mMovie != null) {

			mMovieTitle.setText(mMovie.getOriginal_title());
			mMovieReleaseDate.setText(mMovie.getRelease_date());
			mMovieVoteAverage.setText(String.valueOf(mMovie.getVote_average()));
			mMovieSynopsis.setText(mMovie.getOverview());
			Picasso.with(getContext())
					.load(mMovie.getFullPosterPath())
					.placeholder(R.drawable.downloading_poster)
					.error(R.drawable.no_poster_available)
					.into(mMoviePoster);

			updateFavoriteIcon(isFavorite());
			mFavorite.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isFavorite()) {
						// delete from favorites
						String stringId = Integer.toString(mMovie.getId());
						Uri uri = MovieFavoriteContract.FavoriteMovie.CONTENT_URI;
						uri = uri.buildUpon().appendPath(stringId).build();
						int moviesDeleted = getContext().getContentResolver().delete(uri, null, null);

						if (moviesDeleted > 0) {
							Toast.makeText(getActivity(), R.string.favorite_removed, Toast.LENGTH_LONG).show();
							updateFavoriteIcon(false);
						}
					} else {
						// add to favorites
						ContentValues cv = new ContentValues();
						cv.put(MovieFavoriteContract.FavoriteMovie.COLUMN_MOVIE_ID, mMovie.getId());
						cv.put(MovieFavoriteContract.FavoriteMovie.COLUMN_TITLE, mMovie.getOriginal_title());
						cv.put(MovieFavoriteContract.FavoriteMovie.COLUMN_OVERVIEW, mMovie.getOverview());
						cv.put(MovieFavoriteContract.FavoriteMovie.COLUMN_RELEASE_DATE, mMovie.getRelease_date());
						cv.put(MovieFavoriteContract.FavoriteMovie.COLUMN_VOTE_AVERAGE, mMovie.getVote_average());
						cv.put(MovieFavoriteContract.FavoriteMovie.COLUMN_POSTER_PATH, mMovie.getPoster_path());

						getContext().getContentResolver().insert(MovieFavoriteContract.FavoriteMovie.CONTENT_URI, cv);
						Toast.makeText(getActivity(), R.string.favorite_added, Toast.LENGTH_LONG).show();
						updateFavoriteIcon(true);
					}
				}
			});

			service = TmdbService.createService();
			mTrailerAdapter = new TrailerAdapter(mTrailers, getActivity());
			mTrailerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
			mTrailersRecyclerView.setLayoutManager(mTrailerLayoutManager);
			mTrailersRecyclerView.setHasFixedSize(true);
			mTrailersRecyclerView.setAdapter(mTrailerAdapter);

			if(mTrailers.size() == 0){
				if(NetworkUtils.isOnline(getActivity())){
					Call<TrailerResponse<Trailer>> call = service.getTrailers(mMovie.getId(), BuildConfig.TMDB_API_KEY);

					call.enqueue(new Callback<TrailerResponse<Trailer>>() {
						@Override
						public void onResponse(Call<TrailerResponse<Trailer>> call, Response<TrailerResponse<Trailer>> response) {
							mTrailers.clear();
							mTrailers.addAll(response.body().getResults());
							mTrailerAdapter.notifyDataSetChanged();
						}
						@Override
						public void onFailure(Call<TrailerResponse<Trailer>> call, Throwable t) {
							Toast.makeText(getActivity(), getString(R.string.error_something_wrong), Toast.LENGTH_SHORT).show();
						}
					});
				}

			}

		}

		return rootView;
	}

	private boolean isFavorite() {

		boolean favorite = false;

		String stringId = Integer.toString(mMovie.getId());
		Uri uri = MovieFavoriteContract.FavoriteMovie.CONTENT_URI;
		uri = uri.buildUpon().appendPath(stringId).build();

		Cursor cursor = getContext().getContentResolver().query(
				uri,
				new String[]{COLUMN_MOVIE_ID},
				null,
				null,
				null);

		if (cursor != null && cursor.moveToFirst()) {
			favorite = true;
			cursor.close();
		}
		return favorite;
	}

	private void updateFavoriteIcon(boolean alreadyFavorite) {
		if (alreadyFavorite)
			icon = getContext().getResources().getDrawable(R.drawable.heart_on);
		else
			icon = getContext().getResources().getDrawable(R.drawable.heart_off);

		mFavorite.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
	}
}
