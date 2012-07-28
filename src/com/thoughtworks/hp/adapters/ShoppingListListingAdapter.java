package com.thoughtworks.hp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListListingAdapter extends ArrayAdapter<ShoppingList> {

    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
    private Context context;
    private int layout;

    private static class ViewHolder {
        TextView shoppingListName;
    }

    public ShoppingListListingAdapter(Context context, int layout, List<ShoppingList> shoppingLists) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.shoppingLists = shoppingLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = setTagWithViewHolder(convertView);
        }

        holder = (ViewHolder) convertView.getTag();

        ShoppingList shoppingList = shoppingLists.get(position);
        if(shoppingList != null) populateShoppingListDetailsForLineItem(holder, shoppingList);

        return convertView;
    }

    private View setTagWithViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        LayoutInflater view = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = view.inflate(layout, null);

        holder.shoppingListName = (TextView) convertView.findViewById(R.id.shopping_list_name_text);

        convertView.setTag(holder);
        return convertView;
    }

    private void populateShoppingListDetailsForLineItem(ViewHolder holder, ShoppingList shoppingList) {
        TextView shoppingListName = holder.shoppingListName;
        shoppingListName.setText(shoppingList.getName());
    }
}
