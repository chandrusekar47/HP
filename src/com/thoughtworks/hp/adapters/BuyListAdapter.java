package com.thoughtworks.hp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyListAdapter extends ArrayAdapter<Product> {

    private Map<Product, Integer> productQuantityMap;
    private List<Product> productList = new ArrayList<Product>();
    private Context context;
    private int layout;
    private static final String INDIAN_CURRENCY_SYMBOL = "Rs. ";

    private static class ViewHolder {
        TextView productName;
        TextView productUOM;
        TextView productPrice;
        TextView productQuantity;
    }

    public BuyListAdapter(Context context, int layout, Map<Product,Integer> productQuantityMap, List<Product> products) {
        super(context, layout, products);
        this.context = context;
        this.productList = products;
        this.layout = layout;
        this.productQuantityMap = productQuantityMap;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = setTagWithViewHolder(convertView);
        }

        holder = (ViewHolder) convertView.getTag();

        Product product = productList.get(position);
        int quantity=this.productQuantityMap.get(product);
        if(product != null) populateProductDetailsForLineItem(holder, product,quantity);

        return convertView;
    }

    private void populateProductDetailsForLineItem(ViewHolder holder, Product product, int quantity) {
        TextView productNameTextView = holder.productName;
        productNameTextView.setText(product.getName());

        TextView productCostTextView = holder.productPrice;
        String costAsString = INDIAN_CURRENCY_SYMBOL + Double.toString(Math.round(product.getPrice() * quantity));
        productCostTextView.setText(costAsString);

        TextView productUOMTextView = holder.productUOM;
        productUOMTextView.setText(product.getUom());

        TextView textView=holder.productQuantity;
        textView.setText(String.valueOf(quantity));
    }

    private View setTagWithViewHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        LayoutInflater view = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = view.inflate(layout, null);

        holder.productName = (TextView) convertView.findViewById(R.id.product_name);
        holder.productPrice = (TextView) convertView.findViewById(R.id.product_price);
        holder.productUOM = (TextView) convertView.findViewById(R.id.product_uom);
        holder.productQuantity = (TextView) convertView.findViewById(R.id.product_quantity);

        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public int getCount() {
        return productList.size();
    }
}
