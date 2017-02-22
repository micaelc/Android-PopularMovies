package com.hangapps.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcampos on 21/02/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

	private List<Trailer>mTrailers;
	private Context mContext;

	public TrailerAdapter(List<Trailer> trailers, Context context) {
		mTrailers = trailers;
		mContext = context;
	}

	@Override
	public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View trailerItem = inflater.inflate(R.layout.trailer_item, parent,false);
		TrailerViewHolder holder = new TrailerViewHolder(trailerItem);
		return holder;
	}

	@Override
	public void onBindViewHolder(TrailerViewHolder holder, int position) {

		Trailer trailer = mTrailers.get(position);
		Picasso.with(mContext)
				.load(trailer.getTrailerImgFullPath())
				.placeholder(R.drawable.downloading_thumb)
				.error(R.drawable.no_thumb_available)
				.into(holder.trailerThumbnail);
	}

	@Override
	public int getItemCount() {
		return mTrailers.size();
	}

	public class TrailerViewHolder extends RecyclerView.ViewHolder{

		@BindView(R.id.iv_trailer_thumbnail)
		ImageView trailerThumbnail;

		public TrailerViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);

		}
	}
}
