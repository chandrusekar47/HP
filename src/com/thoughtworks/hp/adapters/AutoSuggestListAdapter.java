package com.thoughtworks.hp.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.Product;

public class AutoSuggestListAdapter extends ArrayAdapter<Product> {

    private List<Product> productList = new ArrayList<Product>();
    private Context context;
    private int layout;

    private static class ViewHolder {
        TextView productName;
        TextView productUOM;
        TextView productPrice;
    }

    public AutoSuggestListAdapter(Context context, int layout, List<Product> products) {
        super(context, layout, products);
        this.context = context;
        this.productList = products;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = setTagWithViewHolder(convertView);
        }

        holder = (ViewHolder) convertView.getTag();

        Product product = productList.get(position);
        if(product != null) populateProductDetailsForLineItem(holder, product);

        return convertView;
    }

    private void populateProductDetailsForLineItem(ViewHolder holder, Product product) {
        TextView productNameTextView = holder.productName;
        productNameTextView.setText(product.getName());

        TextView productCostTextView = holder.productPrice;
        productCostTextView.setText(product.getCostAsString());

        TextView productUOMTextView = holder.productUOM;
        productUOMTextView.setText(product.getUom());
    }

    private View setTagWithViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        LayoutInflater view = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = view.inflate(layout, null);

        holder.productName = (TextView) convertView.findViewById(R.id.product_name);
        holder.productPrice = (TextView) convertView.findViewById(R.id.product_price);
        holder.productUOM = (TextView) convertView.findViewById(R.id.product_uom);

        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public int getCount() {
        return productList.size();
    }
}
