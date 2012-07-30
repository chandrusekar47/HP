package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.adapters.ProductListAdapter;
import com.thoughtworks.hp.datastore.HpDatabase;
import com.thoughtworks.hp.datastore.ProductTable;
import com.thoughtworks.hp.datastore.ShoppingListProductTable;
import com.thoughtworks.hp.models.Product;
import com.thoughtworks.hp.models.ShoppingList;
import com.thoughtworks.hp.models.ShoppingListProduct;
import com.thoughtworks.hp.zxing.integration.android.IntentIntegrator;
import com.thoughtworks.hp.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends Activity implements TextWatcher {

    private ProductTable productTable;
    private ShoppingListProductTable shoppingListProductTable;
    private SQLiteOpenHelper database;

    private ProductListAdapter autoSuggestAdapter;
    private List<Product> autoSuggestedProductList = new ArrayList<Product>();

    private ProductListAdapter productListAdapter;
    private List<Product> toBuyProductList;

    private ListView autoSuggestListView;
    private long shoppingListId;
    private static final String TAG = "com.thoughtworks.hp.activities.AddProductActivity";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.add_product);

        this.shoppingListId = getIntent().getLongExtra(ShoppingList.SHOPPING_LIST_ID, 1);

        initDependencies();
        bindScanningEvents();
        initToBuyListView();
        initAutoSuggestListView();
        attachSelfAsTextWatcherToSearchBox();
    }

    private void bindScanningEvents() {
        Button scanButton = (Button) this.findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntent = new IntentIntegrator(AddProductActivity.this);
                scanIntent.initiateScan();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Log.d(TAG, "Found Scan Result as " + scanResult.getContents());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {}
        finally {
            if(this.database != null) this.database.close();
        }
    }

    private void initDependencies() {
        this.database = HpDatabase.database(getApplicationContext());
        this.productTable = new ProductTable(database);
        this.shoppingListProductTable = new ShoppingListProductTable(this.database);
    }

    private void initToBuyListView() {
        this.toBuyProductList = this.shoppingListId > 0 ? fetchProductsForShoppingList() : new ArrayList<Product>();
        this.productListAdapter = new ProductListAdapter(this, R.layout.product_line_item, toBuyProductList);
        ListView toBuyProductListView = (ListView) this.findViewById(R.id.to_buy_product_view);
        toBuyProductListView.setAdapter(this.productListAdapter);
    }

    private List<Product> fetchProductsForShoppingList() {
        return productTable.findByShoppingList(this.shoppingListId);
    }

    private void initAutoSuggestListView() {
        this.autoSuggestAdapter = new ProductListAdapter(this, R.layout.product_auto_suggest_line_item, autoSuggestedProductList);
        this.autoSuggestListView = (ListView) this.findViewById(R.id.auto_suggest_list);
        autoSuggestListView.setAdapter(this.autoSuggestAdapter);
        autoSuggestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                addSelectedProductToListing(position);
                saveSelectedProductToShoppingList(position);
                resetAutoSuggestList();
            }
        });
    }

    private void saveSelectedProductToShoppingList(int position) {
        ShoppingListProduct newShoppingListProduct = new ShoppingListProduct(autoSuggestedProductList.get(position).getId(), this.shoppingListId);
        this.shoppingListProductTable.create(newShoppingListProduct);
    }

    private void addSelectedProductToListing(int position) {
        toBuyProductList.add(autoSuggestedProductList.get(position));
        productListAdapter.notifyDataSetChanged();
    }

    private void resetAutoSuggestList() {
        autoSuggestedProductList.clear();
        autoSuggestAdapter.notifyDataSetChanged();
        ((EditText)AddProductActivity.this.findViewById(R.id.searchBox)).setText("");
        autoSuggestListView.setVisibility(View.INVISIBLE);
    }

    private List<Product> findMatchingProducts(String nameFragment) {
        return productTable.findByMatchingName(nameFragment);
    }

    private void attachSelfAsTextWatcherToSearchBox() {
        ((EditText)this.findViewById(R.id.searchBox)).addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence == null || charSequence.length() < 1) return;

        autoSuggestListView.setVisibility(View.VISIBLE);
        autoSuggestedProductList.clear();
        autoSuggestedProductList.addAll(findMatchingProducts(charSequence.toString()));
        clearAlreadyAddedProductsFrom(autoSuggestedProductList);
        autoSuggestAdapter.notifyDataSetChanged();
    }

    private void clearAlreadyAddedProductsFrom(List<Product> autoSuggestedProductList) {
        autoSuggestedProductList.removeAll(this.toBuyProductList);
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }
}