package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.adapters.ShoppingListListingAdapter;
import com.thoughtworks.hp.datastore.HpDatabase;
import com.thoughtworks.hp.datastore.ShoppingListTable;
import com.thoughtworks.hp.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListListingActivity extends Activity {

    private HpDatabase database;
    private ShoppingListTable shoppingListTable;
    private ListView shoppingListsListingView;
    private ShoppingListListingAdapter shoppingListListingAdapter;
    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.shopping_list_listing);

        initDependencies();
        initListingView();
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
        this.shoppingListTable = new ShoppingListTable(this.database);
    }

    private void initListingView() {
        updateAllShoppingLists();
        this.shoppingListListingAdapter = new ShoppingListListingAdapter(this, R.layout.shopping_list_line_item, shoppingLists);
        this.shoppingListsListingView = (ListView) this.findViewById(R.id.shopping_list_lists);
        this.shoppingListsListingView.setAdapter(this.shoppingListListingAdapter);
    }

    private void updateAllShoppingLists() {
        shoppingLists.clear();
        shoppingLists.addAll(shoppingListTable.findAll());
    }
}
