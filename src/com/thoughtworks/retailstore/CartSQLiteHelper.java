package com.thoughtworks.retailstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper class for cart related transacts
 * @author mbhargava
 *
 */
public class CartSQLiteHelper extends SQLiteOpenHelper 
{

  //database items
  public static final String TABLE_CART_ITEMS = "cart_items";
  public static final String COLUMN_ITEM_ID = "item_id";
  public static final String COLUMN_ITEM_COUNT = "item_count";

  private static final String DATABASE_NAME = "cart.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_CART_ITEMS + "(" + COLUMN_ITEM_ID
      + " integer primary key, " + COLUMN_ITEM_COUNT
      + " integer);";

  public CartSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /*
   * (non-Javadoc)
   * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
   */
  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  /*
   * (non-Javadoc)
   * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(CartSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEMS);
    onCreate(db);
  }

} 
