package com.thoughtworks.hp;

import android.annotation.SuppressLint;
import android.os.Bundle;

@SuppressLint("ParserError")
public class HomePage extends CustomWindow {

	@SuppressLint("ParserError")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_page);
//		Button shoppingListButton = (Button) findViewById(R.id.button1);
//		HomeNavigationHandler navigationHandler = new HomeNavigationHandler();
//		shoppingListButton.setOnClickListener(navigationHandler);
//
//		Button dealsButton = (Button) findViewById(R.id.button2);
//		dealsButton.setOnClickListener(navigationHandler);
	}

//	private class HomeNavigationHandler implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.button1:
//				Intent action = new Intent(HomePage.this,
//						ShoppingListListingActivity.class);
//				startActivity(action);
//				break;
//
//			case R.id.button2:
//				Intent deals = new Intent(HomePage.this, SelectDealActivity.class);
//				startActivity(deals);
//				break;
//
//			}
//
//		}
//
//	}
}
