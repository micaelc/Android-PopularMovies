package com.hangapps.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView mHelloWorldTextView = (TextView)findViewById(R.id.tv_hello_world);
		String tmdbApiKey = BuildConfig.TMDB_API_KEY;
		mHelloWorldTextView.setText(tmdbApiKey);
	}
}
