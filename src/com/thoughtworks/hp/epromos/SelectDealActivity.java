package com.thoughtworks.hp.epromos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thoughtworks.hp.CustomWindow;
import com.thoughtworks.hp.R;

public class SelectDealActivity extends CustomWindow {

    @Override
    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.type_of_deals);
    }

    public void goToDeal(View view) {
        Intent intent = new Intent(SelectDealActivity.this,DragActivity.class);
        startActivity(intent);
    }

}
