package com.hangapps.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcampos on 08/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

	private List<Movie> mMovies;
	private Context mContext;

	public MovieAdapter(List<Movie> movies, Context context) {
		mMovies = movies;
		mContext = context;
	}

	@Override
	public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View movieItem = inflater.inflate(R.layout.movie_grid_item, parent, false);
		MovieViewHolder holder = new MovieViewHolder(movieItem);

		return holder;
	}

	@Override
	public void onBindViewHolder(MovieViewHolder holder, int position) {

		Movie movie = mMovies.get(position);
		Picasso.with(mContext)
				.load(movie.getFullPosterPath())
				.placeholder(R.drawable.downloading_poster)
				.error(R.drawable.no_poster_available)
				.into(holder.moviePoster);

	}

	@Override
	public int getItemCount() {
		return mMovies.size();
	}

	public class MovieViewHolder extends RecyclerView.ViewHolder{

		@BindView(R.id.movie_poster)
		ImageView moviePoster;

		public MovieViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);


		}

	}
}
