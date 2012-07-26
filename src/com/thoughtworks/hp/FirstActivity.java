package com.thoughtworks.hp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
    }
    public void creatingList(View view) {
        Intent intent=new Intent(this, CreatingListActivity.class);
        startActivity(intent);
    }
}

