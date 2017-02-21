package com.hangapps.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mcampos on 21/02/2017.
 */

public class Review implements Parcelable {

	private String id;
	private String author;
	private String content;
	private String url;

	public Review(String id, String author, String content, String url) {
		this.id = id;
		this.author = author;
		this.content = content;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.author);
		dest.writeString(this.content);
		dest.writeString(this.url);
	}

	protected Review(Parcel in) {
		this.id = in.readString();
		this.author = in.readString();
		this.content = in.readString();
		this.url = in.readString();
	}

	public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
		@Override
		public Review createFromParcel(Parcel source) {
			return new Review(source);
		}

		@Override
		public Review[] newArray(int size) {
			return new Review[size];
		}
	};
}
