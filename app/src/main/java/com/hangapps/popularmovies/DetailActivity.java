package com.hangapps.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

	TextView mMovieTitle;
	TextView mMovieReleaseDate;
	ImageView mMoviePoster;
	TextView mMovieVoteAverage;
	TextView mMovieSynopsis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Bundle b = getIntent().getExtras();
		Movie mMovie = b.getParcelable(Constants.APP_TAG);

		mMovieTitle = (TextView)findViewById(R.id.movie_title);
		mMovieReleaseDate = (TextView)findViewById(R.id.movie_release_date);
		mMovieVoteAverage = (TextView)findViewById(R.id.movie_rating);
		mMovieSynopsis = (TextView)findViewById(R.id.movie_synopsis);
		mMoviePoster = (ImageView)findViewById(R.id.movie_poster);

		mMovieTitle.setText(mMovie.getOriginal_title());
		mMovieReleaseDate.setText(mMovie.getRelease_date());
		mMovieVoteAverage.setText(mMovie.getVote_average().toString());
		mMovieSynopsis.setText(mMovie.getOverview());
		Picasso.with(this).load(mMovie.getFullPosterPath()).placeholder(R.drawable.downloading_poster).error(R.drawable.no_poster_available).into(mMoviePoster);

	}
}
