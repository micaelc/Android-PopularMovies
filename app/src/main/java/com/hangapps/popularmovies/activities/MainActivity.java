package com.hangapps.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.hangapps.popularmovies.BuildConfig;
import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.adapters.MovieAdapter;
import com.hangapps.popularmovies.fragments.MovieDetailsFragment;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.models.MoviesResponse;
import com.hangapps.popularmovies.network.NetworkUtils;
import com.hangapps.popularmovies.network.TmdbAip;
import com.hangapps.popularmovies.network.TmdbService;
import com.hangapps.popularmovies.tasks.FavoriteAsyncTask;
import com.hangapps.popularmovies.utils.Constants;
import com.hangapps.popularmovies.utils.MyPrefs;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

	private TmdbAip movieService;
	private ArrayList<Movie> mMovies;
	private MovieAdapter mAdapter;
	private GridLayoutManager mLayoutManager;

	private boolean mTwoPane;
	private String sortOrder;

	@BindView(R.id.rvMovieGrid)
	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Stetho.initializeWithDefaults(this);
		setContentView(R.layout.activity_movie_main);
		ButterKnife.bind(this);

		if (findViewById(R.id.movie_detail_container) != null) {
			// activity should be in two-pane mode.
			mTwoPane = true;
		}

		if (savedInstanceState == null || !savedInstanceState.containsKey(Constants.APP_TAG_MOVIES)) {
			mMovies = new ArrayList<>();
		} else {
			mMovies = savedInstanceState.getParcelableArrayList(Constants.APP_TAG_MOVIES);
		}

		// Retrofit service
		movieService = TmdbService.createService();

		mAdapter = new MovieAdapter(mMovies, this, this);
		mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_number_columns));
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);


		if (mMovies.size() == 0) {
			// get Stored Preferences related to the last Sort Order requested by the user
			sortOrder = MyPrefs.GetStringPreference(this, Constants.MyPreferences.PREF_SORT_ORDER);
			// if Preference exists, get the list with the latest sort order
			if (sortOrder == null)
				sortOrder = Constants.APIConstants.SORT_POPULARITY;
			fetchMovies(sortOrder, 1);

		}
	}


	// *****************************
	// ******** Option Menu ********
	// *****************************
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case R.id.option_menu_sort_popularity:
				MyPrefs.StorePreference(this, Constants.MyPreferences.PREF_SORT_ORDER, Constants.APIConstants.SORT_POPULARITY);
				sortOrder = Constants.APIConstants.SORT_POPULARITY;
				Log.i(Constants.APP_TAG, getResources().getString(R.string.msg_popular_movies));
				break;
			case R.id.option_menu_sort_rate:
				MyPrefs.StorePreference(this, Constants.MyPreferences.PREF_SORT_ORDER, Constants.APIConstants.SORT_RATING);
				sortOrder= Constants.APIConstants.SORT_RATING;
				Log.i(Constants.APP_TAG, getResources().getString(R.string.msg_rated_movies));
				break;
			case R.id.option_menu_sort_favorite:
				MyPrefs.StorePreference(this, Constants.MyPreferences.PREF_SORT_ORDER, Constants.APIConstants.SORT_FAVORITE);
				sortOrder = Constants.APIConstants.SORT_FAVORITE;
				Log.i(Constants.APP_TAG, getResources().getString(R.string.msg_favorite_movies));
				break;
		}
		fetchMovies(sortOrder, 1);
		return super.onOptionsItemSelected(item);
	}


	// ********************************
	// ******* Helper Methods *********
	// ********************************
	public void fetchMovies(String sortOrder, int page) {
		switch (sortOrder) {
			case Constants.APIConstants.SORT_FAVORITE:
				mMovies.clear();
				new FavoriteAsyncTask(this, mMovies).execute();
				mAdapter.notifyDataSetChanged();
				break;
			case Constants.APIConstants.SORT_POPULARITY:
			case Constants.APIConstants.SORT_RATING:

				if (NetworkUtils.isOnline(this)) {
					Call<MoviesResponse<Movie>> call = movieService.getMovies(BuildConfig.TMDB_API_KEY, sortOrder, page);
					call.enqueue(new Callback<MoviesResponse<Movie>>() {
						@Override
						public void onResponse(Call<MoviesResponse<Movie>> call, Response<MoviesResponse<Movie>> response) {
							mMovies.clear();
							mMovies.addAll(response.body().getResults());
							mAdapter.notifyDataSetChanged();
						}

						@Override
						public void onFailure(Call<MoviesResponse<Movie>> call, Throwable t) {
							Toast.makeText(MainActivity.this, getString(R.string.error_something_wrong), Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					Toast.makeText(this, getString(R.string.internet_connection_msg), Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	@Override
	public void onClick(Movie movie) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putParcelable(MovieDetailsFragment.ARG_MOVIE_ITEM, movie);
			MovieDetailsFragment fragment = new MovieDetailsFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.movie_detail_container, fragment)
					.commit();
		} else {
			StartDetailActivity(movie);
		}
	}


	public void StartDetailActivity(Movie movie) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(Constants.APP_TAG_MOVIES, movie);
		startActivity(intent);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(Constants.APP_TAG_MOVIES, mMovies);
		super.onSaveInstanceState(outState);
	}

	// refresh the favorite movie list after removing one movie from the
	// favorites and go back into the Movie Grid (Only if sort order is = favorite
	@Override
	protected void onResume() {
		super.onResume();
		if (Constants.APIConstants.SORT_FAVORITE.equals(sortOrder) && MyPrefs.GetBooleanPreference(this, Constants.MyPreferences.PREF_FAVORITE_CHANGED)){
			fetchMovies(sortOrder,1);
			MyPrefs.StorePreference(this, Constants.MyPreferences.PREF_FAVORITE_CHANGED, false);
		}
	}
}
