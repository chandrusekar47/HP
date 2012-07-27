package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.thoughtworks.hp.R;

public class AddProductActivity extends Activity {

    private ArrayAdapter<String> autoSuggestAdapter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.add_product);

        initAdapter();
        initProductFilter();
    }

    private void initProductFilter() {
        AutoCompleteTextView searchBox = (AutoCompleteTextView) this.findViewById(R.id.searchBox);
        searchBox.setAdapter(autoSuggestAdapter);
        searchBox.setThreshold(1);
    }

    private void initAdapter() {
        autoSuggestAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchMatchingProducts());
    }

    private String[] fetchMatchingProducts() {
        return new String[] {"Hello", "Hi", "Ham", "Haste", "Holiday", "Hawsome"};
    }

}
