package com.thoughtworks.hp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.thoughtworks.hp.activities.AddProductActivity;
import com.thoughtworks.hp.activities.ShoppingListListingActivity;
import com.thoughtworks.hp.zxing.integration.android.IntentIntegrator;

public class HomeActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

//        Button webViewButton = (Button) findViewById(R.id.webview_button);
//        webViewButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, MapViewActivity.class));
//            }
//        });

        Button addProductsButton = (Button) findViewById(R.id.add_products_button);
        addProductsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ShoppingListListingActivity.class));
            }
        });
    }
}
