package com.hangapps.popularmovies.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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

	Movie mMovie;


	public MovieDetailsFragment() {
		// Required empty public constructor
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

		if (mMovie != null){

			mMovieTitle.setText(mMovie.getOriginal_title());
			mMovieReleaseDate.setText(mMovie.getRelease_date());
			mMovieVoteAverage.setText(String.valueOf(mMovie.getVote_average()));
			mMovieSynopsis.setText(mMovie.getOverview());
			Picasso.with(getContext())
					.load(mMovie.getFullPosterPath())
					.placeholder(R.drawable.downloading_poster)
					.error(R.drawable.no_poster_available)
					.into(mMoviePoster);

			mFavorite.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getContext(), "pois", Toast.LENGTH_SHORT).show();
					mFavorite.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.heart_on),null, null, null);

					// if is already favorite
						// delete from database
						// restore button to default design

					// else
						// create contenvalues
						// insert movie to database through a contentResolver



				}
			});

		}

		return rootView ;
	}

}
