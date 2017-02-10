package com.hangapps.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.fragments.MovieDetailsFragment;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.utils.Constants;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

	Movie mMovie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_details);
		ButterKnife.bind(this);

		if(savedInstanceState == null) {
			Bundle b = getIntent().getExtras();
			mMovie = b.getParcelable(Constants.APP_TAG);

			Bundle arguments = new Bundle();
			arguments.putParcelable(MovieDetailsFragment.ARG_MOVIE_ITEM, mMovie);
			MovieDetailsFragment fragment = new MovieDetailsFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.movie_detail_container, fragment)
					.commit();
		}

	}

}
