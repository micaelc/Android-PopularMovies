package com.hangapps.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mcampos on 20/01/2017.
 */

public class MovieGridAdapter extends ArrayAdapter<Movie>{


	public MovieGridAdapter(Context context, List<Movie> movies) {
		super(context, 0, movies);
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Movie movie = getItem(position);

		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_item,parent,false);
		}

		ImageView moviePoster = (ImageView)convertView.findViewById(R.id.movie_poster);
		Picasso.with(getContext()).load(movie.getFullPosterPath()).placeholder(R.drawable.downloading_poster).error(R.drawable.no_poster_available).into(moviePoster);

		return convertView;
	}


}
