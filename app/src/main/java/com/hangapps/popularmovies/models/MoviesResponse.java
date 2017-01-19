package com.hangapps.popularmovies.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcampos on 19/01/2017.
 */

public class MoviesResponse {

    List<Movie> movies;

    public MoviesResponse(List<Movie> movies) {
        this.movies = new ArrayList<Movie>();
    }


    public static MoviesResponse parseJson(String response){
        Gson gson = new GsonBuilder().create();
        MoviesResponse movieResponse = gson.fromJson(response, MoviesResponse.class);
        return movieResponse;
    }
}
