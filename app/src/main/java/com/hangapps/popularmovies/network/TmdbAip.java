package com.hangapps.popularmovies.network;

import com.hangapps.popularmovies.models.Movie;
import com.hangapps.popularmovies.models.MoviesResponse;
import com.hangapps.popularmovies.models.Review;
import com.hangapps.popularmovies.models.ReviewsResponse;
import com.hangapps.popularmovies.models.Trailer;
import com.hangapps.popularmovies.models.TrailerResponse;
import com.hangapps.popularmovies.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mcampos on 18/01/2017.
 */

public interface TmdbAip {

	@GET("3/discover/movie?")
	Call<MoviesResponse<Movie>> getMovies(
			@Query(Constants.APIConstants.APP_KEY_QUERY_API_KEY) String apiKey,
			@Query(Constants.APIConstants.APP_KEY_QUERY_SORT_BY) String sortBy,
			@Query(Constants.APIConstants.APP_KEY_QUERY_PAGE) int page);

	@GET("3/movie/{id}/videos?")
	Call<TrailerResponse<Trailer>> getTrailers(
			@Path("id") int movieId,
			@Query(Constants.APIConstants.APP_KEY_QUERY_API_KEY) String apiKey
	);

	@GET("3/movie/{id}/reviews?")
	Call<ReviewsResponse<Review>> getReviews(
			@Path("id") int movieId,
			@Query(Constants.APIConstants.APP_KEY_QUERY_API_KEY) String apiKey
	);




}
