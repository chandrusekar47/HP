package com.thoughtworks.hp.datastore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface Table<T> {

    public T findById(long id);

    public List<T> findAll(boolean status);

    public void manageProductsList(ContentValues contentValues);

}
