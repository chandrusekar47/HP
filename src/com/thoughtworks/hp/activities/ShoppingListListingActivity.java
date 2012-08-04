package com.thoughtworks.hp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import com.thoughtworks.hp.CustomWindow;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.adapters.ShoppingListListingAdapter;
import com.thoughtworks.hp.datastore.ShoppingListTable;
import com.thoughtworks.hp.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListListingActivity extends CustomWindow {

    private ShoppingListTable shoppingListTable;
    private ListView shoppingListsListingView;
    private ShoppingListListingAdapter shoppingListListingAdapter;
    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.shopping_list_listing);

        initDependencies();
        bindEventsToAddNewList();
        initListingView();
    }

    private void initDependencies() {
        this.shoppingListTable = new ShoppingListTable();
    }

    private void initListingView() {
        updateAllShoppingLists();
        this.shoppingListListingAdapter = new ShoppingListListingAdapter(this, R.layout.shopping_list_line_item, shoppingLists);
        this.shoppingListsListingView = (ListView) this.findViewById(R.id.shopping_list_lists);
        this.shoppingListsListingView.setAdapter(this.shoppingListListingAdapter);
        this.shoppingListsListingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                long shoppingListId = shoppingLists.get(position).getId();
                startActivityToAddProductsToShoppingListWith(shoppingListId);
            }
        });
    }

    private void updateAllShoppingLists() {
        shoppingLists.clear();
        shoppingLists.addAll(shoppingListTable.findAll());
    }

    private void updateListingView() {
        updateAllShoppingLists();
        shoppingListListingAdapter.notifyDataSetChanged();
    }

    private void bindEventsToAddNewList() {
        ImageView addNewShoppingListButton = (ImageView) this.findViewById(R.id.add_new_shopping_list_button);
        addNewShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewShoppingListAndRefreshView();
            }
        });
    }

    private void addNewShoppingListAndRefreshView() {
        //All of this needs to move to a separate thread instead of main. Will do that soon.
        String newListName = ((EditText) ShoppingListListingActivity.this.findViewById(R.id.shopping_list_name)).getText().toString();
        ShoppingList newShoppingList = new ShoppingList(-1, newListName);
        shoppingListTable.create(newShoppingList);

        updateListingView();
        clearListNameTextBox();
        startActivityToAddProductsToShoppingListWith(newShoppingList.getId());
    }

    private void clearListNameTextBox() {
        ((EditText) ShoppingListListingActivity.this.findViewById(R.id.shopping_list_name)).setText("");
    }

    private void startActivityToAddProductsToShoppingListWith(long id) {
        Intent addItemsToShoppingList = new Intent(ShoppingListListingActivity.this, AddProductActivity.class);
        addItemsToShoppingList.putExtra(ShoppingList.SHOPPING_LIST_ID, id);
        startActivity(addItemsToShoppingList);
    }
}
