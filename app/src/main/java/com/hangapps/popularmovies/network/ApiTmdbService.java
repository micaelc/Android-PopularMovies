package com.hangapps.popularmovies.network;

import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.models.MoviesResponse;
import com.hangapps.popularmovies.utils.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static retrofit2.Retrofit.Builder;

/**
 * Created by mcampos on 18/01/2017.
 */

public interface ApiTmdbService {

	@GET("3/discover/movie?")
	Call<MoviesResponse<Movie>> getMovies(
			@Query(Constants.APIConstants.APP_KEY_QUERY_API_KEY) String apiKey,
			@Query(Constants.APIConstants.APP_KEY_QUERY_SORT_BY) String sortBy,
			@Query(Constants.APIConstants.APP_KEY_QUERY_PAGE) int page);




}
