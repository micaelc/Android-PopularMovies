package com.hangapps.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hangapps.popularmovies.R;
import com.hangapps.popularmovies.models.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcampos on 22/02/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

	private List<Review> mReviews;
	private Context mContext;

	public ReviewAdapter(List<Review> reviews, Context context) {
		mReviews = reviews;
		mContext = context;
	}

	@Override
	public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View reviewItem = inflater.inflate(R.layout.review_item,parent,false);
		ReviewViewHolder holder = new ReviewViewHolder(reviewItem);
		return holder;
	}

	@Override
	public void onBindViewHolder(ReviewViewHolder holder, int position) {
		Review review = mReviews.get(position);
		holder.mReviewAuthor.setText(review.getAuthor());
		holder.mReviewComment.setText(review.getContent());
	}

	@Override
	public int getItemCount() {
		return mReviews.size();
	}

	public class ReviewViewHolder extends RecyclerView.ViewHolder{

		@BindView(R.id.tv_review_author)
		TextView mReviewAuthor;
		@BindView(R.id.tv_review_comment)
		TextView mReviewComment;

		public ReviewViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
