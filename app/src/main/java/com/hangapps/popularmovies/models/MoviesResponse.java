package com.hangapps.popularmovies.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcampos on 19/01/2017.
 */

public class MoviesResponse<Movie> {
    private int page;
    private List<Movie> results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

//    private List<Movie> movies;
//
//    public MoviesResponse(List<Movie> movies) {
//        this.movies = new ArrayList<Movie>();
//    }
//
//    public List<Movie> getMovies() {
//        return movies;
//    }
//
//    public static MoviesResponse parseJson(String response){
//        Gson gson = new GsonBuilder().create();
//        MoviesResponse movieResponse = gson.fromJson(response, MoviesResponse.class);
//        return movieResponse;
//    }
}
