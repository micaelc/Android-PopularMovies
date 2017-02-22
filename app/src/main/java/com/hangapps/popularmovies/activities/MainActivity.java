package com.hangapps.popularmovies.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

		if(savedInstanceState == null || !savedInstanceState.containsKey(Constants.APP_TAG_MOVIES)) {
			mMovies = new ArrayList<>();
		}
		else {
			mMovies = savedInstanceState.getParcelableArrayList(Constants.APP_TAG_MOVIES);
		}

		// Retrofit service
		movieService = TmdbService.createService();

		mAdapter = new MovieAdapter(mMovies, this, this);
		mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_number_columns));
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);


		if(mMovies.size() == 0){

			if(NetworkUtils.isOnline(this)){
				// get Stored Preferences related to the last Sort Order requested by the user
				String sortOrder = GetPreference(Constants.MyPreferences.PREF_SORT_ORDER);
				// if Preference exists, get the list with the latest sort order
				if (sortOrder != null){
					fetchMovies(sortOrder, 1);
				}else { // if not, get the list with default values
					fetchMovies(Constants.APIConstants.SORT_POPULARITY, 1);
				}
			}
		}
	}


	// *****************************
	// ******** Option Menu ********
	// *****************************
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (NetworkUtils.isOnline(this)){
			switch (itemId){
				case R.id.option_menu_sort_popularity:
					StorePreference(Constants.MyPreferences.PREF_SORT_ORDER, Constants.APIConstants.SORT_POPULARITY);
					fetchMovies(Constants.APIConstants.SORT_POPULARITY, 1);
					Log.i(Constants.APP_TAG, "Fetch Most Popular Movies");
					break;
				case R.id.option_menu_sort_rate:
					StorePreference(Constants.MyPreferences.PREF_SORT_ORDER, Constants.APIConstants.SORT_RATING);
					fetchMovies(Constants.APIConstants.SORT_RATING, 1);
					Log.i(Constants.APP_TAG, "Fetch Highest Rated Movies");
					break;
				case R.id.option_menu_sort_favorite:
					StorePreference(Constants.MyPreferences.PREF_SORT_ORDER, Constants.APIConstants.SORT_FAVORITE);
					fetchMovies(Constants.APIConstants.SORT_FAVORITE, 1);
					Log.i(Constants.APP_TAG, "Fetch Favorite Movies");
					break;
			}
		} else {
			Toast.makeText(this, getString(R.string.internet_connection_msg) , Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}


	// ********************************
	// ******* Helper Methods *********
	// ********************************
	public void fetchMovies(String sortOrder, int page){

		switch (sortOrder){
			case Constants.APIConstants.SORT_FAVORITE:
				mMovies.clear();
				new FavoriteAsyncTask(this, mMovies).execute();
				mAdapter.notifyDataSetChanged();
				break;
			case Constants.APIConstants.SORT_POPULARITY:
			case Constants.APIConstants.SORT_RATING:

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


	public void StartDetailActivity (Movie movie){
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(Constants.APP_TAG_MOVIES, movie);
		startActivity(intent);

	}

	// ***************************************
	// ******** Preferences & State **********
	// ***************************************

	public void StorePreference(String key, String value){
		SharedPreferences prefs = getSharedPreferences(Constants.MyPreferences.MY_FREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String GetPreference(String key){
		SharedPreferences prefs = getSharedPreferences(Constants.MyPreferences.MY_FREFS_NAME, MODE_PRIVATE);
		if (key != null){
			String value = prefs.getString(key, null);
			if(value != null){
				return value;
			}
		}
		return null;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(Constants.APP_TAG_MOVIES, mMovies);
		super.onSaveInstanceState(outState);
	}


}
