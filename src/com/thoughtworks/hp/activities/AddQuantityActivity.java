package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.datastore.ShoppingListProductTable;
import com.thoughtworks.hp.models.ShoppingList;

public class AddQuantityActivity extends Activity {

    private long productId;
    private long shoppingListId;
    private ShoppingListProductTable shoppingListProductTable;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.update_quantity);
        shoppingListProductTable=new ShoppingListProductTable();
        productId = getIntent().getLongExtra("product_id", 1L);
        shoppingListId = getIntent().getLongExtra("Shopping_list_id", 0L);
    }

    public void incrementQuantity(View view) {
        EditText editText = (EditText) findViewById(R.id.quantity_value);
        int quantity  = Integer.parseInt(editText.getText().toString()) +1 ;
        editText.setText(String.valueOf(quantity));
    }

    public void decrementQuantity(View view) {
        EditText editText = (EditText) findViewById(R.id.quantity_value);
        int quantity  = Integer.parseInt(editText.getText().toString()) -1 ;
        editText.setText(String.valueOf(quantity));
    }

    @Override
    public void onBackPressed() {
        EditText editText = (EditText) findViewById(R.id.quantity_value);
        int quantity  = Integer.parseInt(editText.getText().toString());
        shoppingListProductTable.updateQuantityForProduct(productId,quantity);
        Intent intent = new Intent(AddQuantityActivity.this, AddProductActivity.class);
        intent.putExtra(ShoppingList.SHOPPING_LIST_ID, shoppingListId);
        startActivity(intent);
    }
}
