package com.thoughtworks.hp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.thoughtworks.hp.activities.ShoppingListListingActivity;
import com.thoughtworks.hp.epromos.DragActivity;

@SuppressLint("ParserError")
public class HomePage extends Activity {

	@SuppressLint("ParserError")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_page);
		Button shoppingListButton = (Button) findViewById(R.id.button1);
		HomeNavigationHandler navigationHandler = new HomeNavigationHandler();
		shoppingListButton.setOnClickListener(navigationHandler);

		Button dealsButton = (Button) findViewById(R.id.button2);
		dealsButton.setOnClickListener(navigationHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home_page, menu);
		return true;
	}

	private class HomeNavigationHandler implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				Intent action = new Intent(HomePage.this,
						ShoppingListListingActivity.class);
				startActivity(action);
				break;

			case R.id.button2:
				Intent deals = new Intent(HomePage.this, DragActivity.class);
				startActivity(deals);
				break;

			}

		}

	}
}
