package com.hangapps.popularmovies.network;

import com.hangapps.popularmovies.BuildConfig;
import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mcampos on 18/01/2017.
 */

public interface TmdbAPI {

	@GET("3/discover/movie?")
	Call<List<Movie>> getMovies(
			@Query(Constants.APIConstants.APP_KEY_QUERY_API_KEY) String apiKey,
			@Query(Constants.APIConstants.APP_KEY_QUERY_SORT_BY) String sortBy,
			@Query(Constants.APIConstants.APP_KEY_QUERY_PAGE) int page);

}
