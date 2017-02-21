package com.hangapps.popularmovies.models;

import java.util.List;

/**
 * Created by mcampos on 21/02/2017.
 */

public class ReviewsResponse<Review> {

	private int id;
	private int page;
	private List<Review> results;
	private int total_results;
	private int total_pages;

	public List<Review> getResults() {
		return results;
	}
}
