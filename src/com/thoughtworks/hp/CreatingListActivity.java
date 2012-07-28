package com.thoughtworks.hp;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.thoughtworks.hp.datastore.ProductTable;
import com.thoughtworks.hp.models.Product;

import java.util.List;

public class CreatingListActivity extends Activity {

    ProductTable productTable;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_screen);
        displayAddedProducts();
        AutoCompleteTextView textView= (AutoCompleteTextView) findViewById(R.id.productList);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,productTable.findAll());
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                     arg0.getItemAtPosition(arg2).toString();
            }
        });
    }


    private void displayAddedProducts() {
        TextView textView = (TextView) findViewById(R.id.productsAdded);
        textView.setText(buildStringFromProductList(true));
    }

    public String buildStringFromProductList(boolean status){
        StringBuilder stringProduct = new StringBuilder();
        List<Product> productList = productTable.findAll();
        for(Product product: productList) {
             stringProduct.append(product.toString());
        }
        return stringProduct.toString();
    }
}
