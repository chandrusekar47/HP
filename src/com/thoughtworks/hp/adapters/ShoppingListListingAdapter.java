package com.thoughtworks.hp.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.ShoppingList;

public class ShoppingListListingAdapter extends AbstractItemListingAdapter<ShoppingList> {

    static class ShoppingListViewHolder extends ViewHolder {
        TextView shoppingListName;
    }

    public ShoppingListListingAdapter(Context context, int layout, List<ShoppingList> shoppingLists) {
        super(context, layout, shoppingLists);
    }

    @Override
    protected void populateHolderElements(AbstractItemListingAdapter.ViewHolder holder, View convertView) {
        ((ShoppingListViewHolder)holder).shoppingListName = (TextView) convertView.findViewById(R.id.shopping_list_name_text);
    }

    @Override
    protected AbstractItemListingAdapter.ViewHolder getViewHolder() {
        return new ShoppingListViewHolder();
    }

    @Override
    protected void populateDetailsForLineItem(AbstractItemListingAdapter.ViewHolder holder, ShoppingList shoppingList) {
        TextView shoppingListName = ((ShoppingListViewHolder)holder).shoppingListName;
        shoppingListName.setText(shoppingList.getName());
    }
}
