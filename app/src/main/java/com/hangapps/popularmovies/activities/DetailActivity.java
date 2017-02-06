package com.hangapps.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

	@BindView(R.id.movie_title) TextView mMovieTitle;
	@BindView(R.id.movie_release_date) TextView mMovieReleaseDate;
	@BindView(R.id.movie_poster) ImageView mMoviePoster;
	@BindView(R.id.movie_rating) TextView mMovieVoteAverage;
	@BindView(R.id.movie_synopsis) TextView mMovieSynopsis;
	Movie mMovie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		ButterKnife.bind(this);

		if(savedInstanceState == null || !savedInstanceState.containsKey(Constants.APP_TAG)) {
			Bundle b = getIntent().getExtras();
			mMovie = b.getParcelable(Constants.APP_TAG);
		}
		else {
			mMovie = savedInstanceState.getParcelable(Constants.APP_TAG);
		}

		mMovieTitle.setText(mMovie.getOriginal_title());
		mMovieReleaseDate.setText(mMovie.getRelease_date());
		mMovieVoteAverage.setText(String.valueOf(mMovie.getVote_average()));
		mMovieSynopsis.setText(mMovie.getOverview());
		Picasso.with(this).load(mMovie.getFullPosterPath()).placeholder(R.drawable.downloading_poster).error(R.drawable.no_poster_available).into(mMoviePoster);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(Constants.APP_TAG, mMovie);
		super.onSaveInstanceState(outState);
	}
}
