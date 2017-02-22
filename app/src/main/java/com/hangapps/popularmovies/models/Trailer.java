package com.hangapps.popularmovies.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.hangapps.popularmovies.utils.Constants;

/**
 * Created by mcampos on 21/02/2017.
 */

public class Trailer implements Parcelable {

	private String id;
	private String key;
	private int size;
	private String type;

	public Trailer(String id, String key, int size, String type) {
		this.id = id;
		this.key = key;
		this.size = size;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrailerImgFullPath(){
		return Constants.APIConstants.YOUTUBE_IMG_BASE_URL + getKey() + "/mqdefault.jpg";
	}

	public Uri getYoutubeUri(){
		return Uri.parse(Constants.YOUTUBE_VIDEO_URL + getKey());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.key);
		dest.writeInt(this.size);
		dest.writeString(this.type);
	}

	protected Trailer(Parcel in) {
		this.id = in.readString();
		this.key = in.readString();
		this.size = in.readInt();
		this.type = in.readString();
	}

	public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
		@Override
		public Trailer createFromParcel(Parcel source) {
			return new Trailer(source);
		}

		@Override
		public Trailer[] newArray(int size) {
			return new Trailer[size];
		}
	};
}
