package com.thoughtworks.hp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.models.Product;
import com.thoughtworks.hp.models.ShoppingListProduct;

import java.util.ArrayList;
import java.util.List;

public class BuyListAdapter extends AbstractItemListingAdapter<ShoppingListProduct> {

    private List<ShoppingListProduct> productList = new ArrayList<ShoppingListProduct>();

    private static class ShoppingListItemViewHolder extends ViewHolder {
        TextView productName;
        TextView productCost;
        TextView productQuantity;
        TextView amountAfterDiscount;
    }

    public BuyListAdapter(Context context, int layout, List<ShoppingListProduct> shoppingListProducts) {
        super(context, layout, shoppingListProducts);
        this.productList = shoppingListProducts;
    }

    @Override
    protected void populateHolderElements(AbstractItemListingAdapter.ViewHolder holder, View convertView) {
        ((ShoppingListItemViewHolder) holder).productName = (TextView) convertView.findViewById(R.id.product_name);
        ((ShoppingListItemViewHolder) holder).productCost = (TextView) convertView.findViewById(R.id.product_price);
        ((ShoppingListItemViewHolder) holder).productQuantity = (TextView) convertView.findViewById(R.id.product_quantity);
        ((ShoppingListItemViewHolder) holder).amountAfterDiscount = (TextView) convertView.findViewById(R.id.discount_price);
    }

    @Override
    protected AbstractItemListingAdapter.ViewHolder getViewHolder() {
        return new ShoppingListItemViewHolder();
    }

    @Override
    protected void populateDetailsForLineItem(AbstractItemListingAdapter.ViewHolder holder, ShoppingListProduct shoppingListProduct) {
        TextView productNameTextView = ((ShoppingListItemViewHolder) holder).productName;
        productNameTextView.setText(shoppingListProduct.getName());

        TextView productCostTextView = ((ShoppingListItemViewHolder) holder).productCost;
        productCostTextView.setText(Double.toString(shoppingListProduct.totalCost()));

        TextView textView= ((ShoppingListItemViewHolder) holder).productQuantity;
        String quantityMultipliedByUnitCount = shoppingListProduct.getQuantity() + " X " + Product.INDIAN_CURRENCY_SYMBOL + shoppingListProduct.getUnitCost();
        textView.setText(quantityMultipliedByUnitCount);
    }

}
