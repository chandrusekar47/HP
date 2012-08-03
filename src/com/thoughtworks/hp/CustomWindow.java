package com.thoughtworks.hp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.thoughtworks.hp.activities.ShoppingListListingActivity;
import com.thoughtworks.hp.epromos.DragActivity;
import com.thoughtworks.hp.quickactions.QuickActionWindow;

public class CustomWindow extends Activity {
	protected ImageView icon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.home_page);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window);

		icon = (ImageView) findViewById(R.id.icon);
		icon.setOnClickListener(new OnClickListener() {

			@SuppressLint("ParserError")
			@Override
			public void onClick(View arg0) {
				int[] xy = new int[2];
				arg0.getLocationInWindow(xy);
				Log.d("C345Assignment1", "x: " + xy[0] + "; y: " + xy[1]);
				Rect rect = new Rect(xy[0], xy[1], xy[0] + arg0.getWidth(), xy[1] + arg0.getHeight());
				final QuickActionWindow qa = new QuickActionWindow(CustomWindow.this, arg0, rect);

				// Add "View" item and assign the listener on event it's being
				// clicked
				qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_info_details), R.string.qa_info, new OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(getBaseContext(), DragActivity.class);
						startActivity(i);
						qa.dismiss();

					}
				});

				// Add "Feedback" item and assign the listener on event it's
				// being clicked
				qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_agenda), R.string.qa_feedback, new OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(getBaseContext(), ShoppingListListingActivity.class);
						startActivity(i);
						qa.dismiss();

					}
				});

				// Add "Wiki" item and assign the listener on event it's being
				// clicked
				qa.addItem(getResources().getDrawable(R.drawable.ic_menu_goto), R.string.qa_wiki, new OnClickListener() {
					public void onClick(View v) {
					}
				});

				// Add "Delete" item and assign the listener on event it's being
				// clicked
				qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_delete), R.string.qa_delete, new OnClickListener() {
					public void onClick(View v) {
					}
				});

				// Show the QuickAction popup after everything is initialized
				qa.show();
			}
		});

	}
}
