package com.hangapps.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.hangapps.popularmovies.utils.Constants;

/**
 * Created by mcampos on 18/01/2017.
 */

public class Movie implements Parcelable {

	private int id;
	private String original_title;
	private String poster_path;
	private String overview;
	private String release_date;
	private Float vote_average;


	// Movie Constructor
	public Movie(int id, String original_title, String poster_path, String overview, String release_date, Float vote_average) {
		this.id = id;
		this.original_title = original_title;
		this.poster_path = poster_path;
		this.overview = overview;
		this.release_date = release_date;
		this.vote_average = vote_average;
	}

	@Override
	public int describeContents() {
		return 0;
	}


	// Write to Parcel
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(original_title);
		dest.writeString(poster_path);
		dest.writeString(overview);
		dest.writeString(release_date);
		dest.writeFloat(vote_average);
	}

	// Retrieve values from Parcel
	private Movie(Parcel in) {
		id = in.readInt();
		original_title = in.readString();
		poster_path = in.readString();
		overview = in.readString();
		release_date = in.readString();
		vote_average = in.readFloat();
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};

	// ############## Getters ##################

	public int getId() {
		return id;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public String getOverview() {
		return overview;
	}

	public String getRelease_date() {
		return release_date;
	}

	public Float getVote_average() {
		return vote_average;
	}

	public String getFullPosterPath(){
		return Constants.APIConstants.IMAGE_BASE_URL + Constants.APIConstants.IMAGE_SMALL_SIZE + getPoster_path();
	}
}
