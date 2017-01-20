package com.hangapps.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hangapps.popularmovies.adapters.MovieAdapter;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.models.MoviesResponse;
import com.hangapps.popularmovies.network.ApiTmdbService;
import com.hangapps.popularmovies.network.NetworkUtils;
import com.hangapps.popularmovies.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	private GridView movieGrid;
	public MovieAdapter mMovieAdapter;
	private ApiTmdbService movieService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Creates empty list
		List<Movie> movies = new ArrayList<>();
		// creates new MovieAdapter
		mMovieAdapter = new MovieAdapter(this, movies);

		movieGrid = (GridView)findViewById(R.id.movie_grid);
		movieGrid.setAdapter(mMovieAdapter);

		movieService = ApiTmdbService.retrofit.create(ApiTmdbService.class);

		if(NetworkUtils.isOnline(this)){
			fetchMovies(Constants.APIConstants.SORT_POPULARITY, 1);
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
					fetchMovies(Constants.APIConstants.SORT_POPULARITY, 1);
					Log.i(Constants.APP_TAG, "Fetch Most Popular Movies");
					break;
				case R.id.option_menu_sort_rate:
					fetchMovies(Constants.APIConstants.SORT_RATING, 1);
					Log.i(Constants.APP_TAG, "Fetch Highest Rated Movies");
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


		Call<MoviesResponse<Movie>> call = movieService.getMovies(BuildConfig.TMDB_API_KEY, sortOrder, page);

		call.enqueue(new Callback<MoviesResponse<Movie>>() {
			@Override
			public void onResponse(Call<MoviesResponse<Movie>> call, Response<MoviesResponse<Movie>> response) {
				List<Movie> movies = response.body().getResults();
				mMovieAdapter.clear();
				for (Movie movie: movies){
					mMovieAdapter.add(movie);
				}
			}
			@Override
			public void onFailure(Call<MoviesResponse<Movie>> call, Throwable t) {
				Toast.makeText(MainActivity.this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
			}
		});

	}



}
