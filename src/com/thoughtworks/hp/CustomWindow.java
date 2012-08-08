package com.thoughtworks.hp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class CustomWindow extends Activity {
	protected ImageView icon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.home_page);
	}
}
