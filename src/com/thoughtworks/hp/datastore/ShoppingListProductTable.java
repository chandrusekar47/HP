package com.thoughtworks.hp.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;
import com.thoughtworks.hp.models.ShoppingListProduct;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListProductTable implements Table<ShoppingListProduct> {

    public static final String TABLE_NAME = "SHOPPING_LIST_PRODUCT_MAPPINGS";
    private SQLiteOpenHelper database;
    private static String TAG = "com.thoughtworks.hp.datastore.ShoppingListProductTable";

    public ShoppingListProductTable(SQLiteOpenHelper database) {
        this.database = database;
    }

    private static class ShoppingListProductCursor extends SQLiteCursor {

        private static final String FIELD_LIST = " product_id, shopping_list_id ";
        private static final String ALL_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME;
        private static final String SHOPPING_LIST_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME +" WHERE shopping_list_id = ?";

        public ShoppingListProductCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
            super(db, driver, editTable, query);
        }

        private static class Factory implements SQLiteDatabase.CursorFactory {
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                return new ShoppingListProductCursor(db, driver, editTable, query);
            }
        }

        private long getProductId() {
            return getLong(getColumnIndexOrThrow("shopping_list_id"));
        }

        private long getShoppingListId() {
            return getLong(getColumnIndexOrThrow("shopping_list_id"));
        }

        public ShoppingListProduct getShoppingListProduct() {
            return new ShoppingListProduct(getProductId(), getShoppingListId());
        }
    }

    protected List<ShoppingListProduct> findShoppingListProduct(String query, String[] params) {
        Cursor shoppingListProductCursor = null;
        List<ShoppingListProduct> shoppingListProducts = new ArrayList<ShoppingListProduct>();
        try {
            shoppingListProductCursor = database.getReadableDatabase().rawQueryWithFactory(new ShoppingListProductCursor.Factory(), query, params, null);
            if(shoppingListProductCursor != null && shoppingListProductCursor.moveToFirst()) {
                do {
                    shoppingListProducts.add(((ShoppingListProductCursor) shoppingListProductCursor).getShoppingListProduct());
                } while(shoppingListProductCursor.moveToNext());
            }
        } catch(SQLException sqle) {
            Log.e(TAG, "Could not look up the shopping list products with params " + params + ". The error is: " + sqle.getMessage());
        }
        finally {
            if(shoppingListProductCursor != null && !shoppingListProductCursor.isClosed()) {
                shoppingListProductCursor.close();
            }
        }
        return shoppingListProducts;
    }

    public List<ShoppingListProduct> findAll() {
        return findShoppingListProduct(ShoppingListProductCursor.ALL_QUERY, null);
    }

    public ShoppingListProduct findById(long id) {
        return null;
    }

    public ShoppingListProduct create(ShoppingListProduct newShoppingListProduct) {
        if (newShoppingListProduct != null) {
            database.getWritableDatabase().beginTransaction();
            try {
                ContentValues dbValues = new ContentValues();
                dbValues.put("shopping_list_id", newShoppingListProduct.getShoppingListId());
                dbValues.put("product_id", newShoppingListProduct.getProductId());
                database.getWritableDatabase().insertOrThrow(TABLE_NAME, "product_id", dbValues);
                database.getWritableDatabase().setTransactionSuccessful();
            } catch (SQLException sqle) {
                Log.e(TAG, "Could not create new shopping list product. Exception is :" + sqle.getMessage());
            } finally {
                database.getWritableDatabase().endTransaction();
            }
        }
        return newShoppingListProduct;
    }
}
