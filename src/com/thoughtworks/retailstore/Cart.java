package com.thoughtworks.retailstore;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


/**
 * Cart contains cart items. Cart is persisted in the sqlite db.
 * Cart is a singleton.
 * @author mbhargava
 *
 */
public class Cart {
	
	
	private static final Cart sCart = new Cart();
	private static Context sContext = null;
	
	// Database fields
	private SQLiteDatabase mDatabase;
	private static CartSQLiteHelper mDbHelper;
	private String[] mAllColumns = { CartSQLiteHelper.COLUMN_ITEM_ID,
	      CartSQLiteHelper.COLUMN_ITEM_COUNT };
	
	private Cart()
	{
		
	}
	
	public static Cart getInstance(Context ctx)
	{
		sContext = ctx;
		mDbHelper = new CartSQLiteHelper(sContext);
		return sCart;
	}
	
	//Returns map of products and counts of various items in it
	public HashMap<Product, Integer> getCartItems()
	{
		Inventory.getInstance().getInventory(sContext);
		HashMap<Integer, Product> productInventory = Inventory.getInstance().getItemisedMapping();
		HashMap<Product, Integer> cart = new HashMap<Product, Integer>();
		open();
		// Select All Query  
        String selectQuery = "SELECT  * FROM " + CartSQLiteHelper.TABLE_CART_ITEMS;   
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {  
            do {  
                int pId = cursor.getInt(0);
                int itemCount = cursor.getInt(1);
                Product product = productInventory.get(pId);
                cart.put(product, itemCount);
            } while (cursor.moveToNext());  
        }  
		
		close();
		return cart;
	}
	
	//Adds an item to the cart if the item does not exist. Increments the item count, if it does already exist.
	public void addORUpdateItem(Product product)
	{
		open();
		//if the item does not exist, then create item id with item count as 0
		Cursor cur = mDatabase.rawQuery("SELECT * FROM " + CartSQLiteHelper.TABLE_CART_ITEMS + " WHERE "+CartSQLiteHelper.COLUMN_ITEM_ID +"= '" + Integer.toString(product.getId()) + "'", null);
		if (cur.getCount() > 0) { // This will get the number of rows
		    // update
			
			Cursor cursor = mDatabase.query(CartSQLiteHelper.TABLE_CART_ITEMS,
			        mAllColumns, CartSQLiteHelper.COLUMN_ITEM_ID + " = " + product.getId(), null,
			        null, null, null);
			
			
			cursor.moveToFirst();
			int itemCount = cursor.getInt(1);
			itemCount++;
			ContentValues values = new ContentValues();
		    values.put(CartSQLiteHelper.COLUMN_ITEM_COUNT, itemCount);
		    mDatabase.update(CartSQLiteHelper.TABLE_CART_ITEMS, values, CartSQLiteHelper.COLUMN_ITEM_ID+"="+product.getId(), null);
			
		}
		else
		{
			//insert
			ContentValues values = new ContentValues();
		    values.put(CartSQLiteHelper.COLUMN_ITEM_ID, product.getId());
		    values.put(CartSQLiteHelper.COLUMN_ITEM_COUNT, 1);
		    mDatabase.insert(CartSQLiteHelper.TABLE_CART_ITEMS, null,
		        values);
		}
		close();
	}
	
	//Remove a product from the cart
	public void removeItem(Product product)
	{
		open();
		//if item id exists in this list then decrease the item count
		//if the item does not exist, then create item id with item count as 0
		Cursor cursor = mDatabase.query(CartSQLiteHelper.TABLE_CART_ITEMS,
		        mAllColumns, CartSQLiteHelper.COLUMN_ITEM_ID + " = " + product.getId(), null,
		        null, null, null);
		
		
		cursor.moveToFirst();
		int itemCount = cursor.getInt(1);
		itemCount--;
		if(itemCount == 0)
		{
			mDatabase.delete(CartSQLiteHelper.TABLE_CART_ITEMS, CartSQLiteHelper.COLUMN_ITEM_ID + "=" + product.getId(), null);
		}
		else
		{
			ContentValues values = new ContentValues();
		    values.put(CartSQLiteHelper.COLUMN_ITEM_COUNT, itemCount);
		    mDatabase.update(CartSQLiteHelper.TABLE_CART_ITEMS, values, CartSQLiteHelper.COLUMN_ITEM_ID+"="+product.getId(), null);
		}
		close();
	}
	
	private void open() throws SQLException {
	    mDatabase = mDbHelper.getWritableDatabase();
	  }

	  private void close() {
	    mDbHelper.close();
	  }

}
