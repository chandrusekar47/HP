package com.thoughtworks.hp.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;
import com.thoughtworks.hp.HypercityApplication;
import com.thoughtworks.hp.models.ShoppingListProduct;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListProductTable implements Table<ShoppingListProduct> {

    public static final String TABLE_NAME = "SHOPPING_LIST_PRODUCT_MAPPINGS";
    private SQLiteOpenHelper database;
    private static String TAG = ShoppingListProduct.class.getSimpleName();

    public ShoppingListProductTable() {
        this.database = new HypercityApplication().database();
    }

    private static class ShoppingListProductCursor extends SQLiteCursor {

        private static final String FIELD_LIST = " product_id, shopping_list_id ";
        private static final String ALL_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME;
        public static String FIND_QUANTITY_BY_PRODUCT_AND_SHOPPINGLIST = "SELECT quantity FROM "+ TABLE_NAME+ " WHERE product_id=? AND shopping_list_id = ?";

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

        private int getQuantity(){
            return getInt(getColumnIndexOrThrow("quantity"));
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
                dbValues.put("quantity",newShoppingListProduct.getQuantity());
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
    public void updateQuantityForProduct(long productId, int quantity) {
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            ContentValues dbValues = new ContentValues();
            dbValues.put("quantity", quantity);
            writableDatabase.update(TABLE_NAME, dbValues, "product_id =" + productId, null);
            writableDatabase.setTransactionSuccessful();
        } catch (SQLException sqle) {
            Log.e(TAG, "Could not update shopping list product. Exception is :" + sqle.getMessage());
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public int findQuantityOnProductAndShoppingList(long shoppingListID, long productID) {
        Cursor quantityListCursor = null;
        try {
            quantityListCursor = database.getReadableDatabase().rawQueryWithFactory(new ShoppingListProductCursor.Factory(), ShoppingListProductCursor.FIND_QUANTITY_BY_PRODUCT_AND_SHOPPINGLIST, new String[]{Long.toString(productID), Long.toString(shoppingListID)} , null);
            if(quantityListCursor != null && quantityListCursor.moveToFirst()) {
                return((ShoppingListProductCursor)quantityListCursor).getQuantity();
            }
        } catch(SQLException sqle) {
            Log.e(TAG, "Could not get quantity values and the error is " + sqle.getMessage());
        }
        finally {
            if(quantityListCursor != null && !quantityListCursor.isClosed()) {
                quantityListCursor.close();
            }
        }
        return -1;
    }
}
