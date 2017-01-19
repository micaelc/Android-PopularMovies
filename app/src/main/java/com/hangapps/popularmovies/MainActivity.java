package com.hangapps.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.models.MoviesResponse;
import com.hangapps.popularmovies.network.ApiTmdbService;
import com.hangapps.popularmovies.utils.Constants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.option_menu_get_action){
			Toast.makeText(this, "clickei", Toast.LENGTH_SHORT).show();

			ApiTmdbService service = ApiTmdbService.retrofit.create(ApiTmdbService.class);
			Call<MoviesResponse> call = service.getMovies(BuildConfig.TMDB_API_KEY, Constants.APIConstants.SORT_POPULARITY,1);

			call.enqueue(new Callback<MoviesResponse>() {
				@Override
				public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

					MoviesResponse movies = response.body();
					Toast.makeText(MainActivity.this, "e não é que deu ???", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(Call<MoviesResponse> call, Throwable t) {
					Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
				}
			});


		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView mHelloWorldTextView = (TextView)findViewById(R.id.tv_hello_world);
		String tmdbApiKey = BuildConfig.TMDB_API_KEY;
		mHelloWorldTextView.setText(tmdbApiKey);




	}
}
