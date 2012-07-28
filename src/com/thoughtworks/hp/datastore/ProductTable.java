package com.thoughtworks.hp.datastore;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;
import com.thoughtworks.hp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductTable implements Table<Product> {

    private static String TABLE_NAME = "PRODUCTS";
    private SQLiteOpenHelper database;
    private static String TAG = "com.thoughtworks.hp.datastore.ProductTable";

    public ProductTable(SQLiteOpenHelper database) {
        this.database = database;
    }

    private static class ProductCursor extends SQLiteCursor {

        private static final String FIELD_LIST = " id, name, barcode_id, category, cost, uom, status ";
        private static final String ALL_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME;
        private static final String ID_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME +" WHERE id = ?";
        private static final String NAME_LIKE_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME +" WHERE name like ? ";

        public ProductCursor (SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
            super(db, driver, editTable, query);
        }

        private static class Factory implements SQLiteDatabase.CursorFactory {
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                return new ProductCursor(db, driver, editTable, query);
            }
        }

        private long getProductId() {
            return getLong(getColumnIndexOrThrow("id"));
        }

        private String getName() {
            return getString(getColumnIndexOrThrow("name"));
        }

        private String getBarcodeId() {
            return getString(getColumnIndexOrThrow("barcode_id"));
        }

        private String getCategory() {
            return getString(getColumnIndexOrThrow("category"));
        }

        private double getCost() {
            return getDouble(getColumnIndexOrThrow("cost"));
        }

        private String getUOM() {
            return getString(getColumnIndexOrThrow("uom"));
        }

        public boolean getStatus(){
            return Boolean.getBoolean(getString(getColumnIndexOrThrow("status")));
        }

        public Product getProduct() {
            return new Product(getProductId(), getName(), getBarcodeId(), getCategory(), getStatus(), getCost(), getUOM());
        }
    }

    protected List<Product> findProduct(String query, String[] params) {
        Cursor productCursor = null;
        List<Product> productList = new ArrayList<Product>();
        try {
            productCursor = database.getReadableDatabase().rawQueryWithFactory(new ProductCursor.Factory(), query, params, null);
            if(productCursor != null && productCursor.moveToFirst()) {
                do {
                    productList.add(((ProductCursor) productCursor).getProduct());
                } while(productCursor.moveToNext());
            }
        } catch(SQLException sqle) {
            Log.e(TAG, "Could not look up the products with params " + params + ". The error is: " + sqle.getMessage());
        }
        finally {
            if(productCursor != null && !productCursor.isClosed()) {
                productCursor.close();
            }
        }
        return productList;
    }

    public List<Product> findAll() {
        return findProduct(ProductCursor.ALL_QUERY, null);
    }

    public Product findById(long id) {
        String id_string = Long.toString(id);
        List<Product> productList = findProduct(ProductCursor.ID_QUERY, new String[] {id_string});
        return (productList == null || productList.isEmpty()) ? null : productList.get(0);
    }

    public List<Product> findByMatchingName(String nameFragment) {
        return findProduct(ProductCursor.NAME_LIKE_QUERY, new String[] {"%" + nameFragment + "%"});
    }

}
