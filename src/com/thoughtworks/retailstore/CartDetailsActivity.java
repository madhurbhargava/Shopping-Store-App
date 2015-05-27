package com.thoughtworks.retailstore;

import java.util.HashMap;
import java.util.Map;

import android.widget.ListView;
import android.widget.TextView;

/**
 * Shows Cart items as list
 * @author mbhargava
 *
 */
public class CartDetailsActivity extends RetailStoreApplicationActivity {
	
	//adapter for list of cart items
	private CartAdapter mCartAdapter;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_items_list);
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.retailstore.RetailStoreApplicationActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HashMap<Product, Integer> map = Cart.getInstance(this).getCartItems(); 
		double cost = calculateTotalCost(map);
		((TextView)findViewById(R.id.cost_total)).setText("Total cost: Rs. "+cost);
		
		mCartAdapter = new CartAdapter(this, map);
		((ListView)findViewById(R.id.list_cart_items)).setAdapter(mCartAdapter);
	}
	
	/**
	 * Calculates total cart cost
	 * @param map, hashmap of cart items with item as key and quantity of item as value
	 * @return total cart cost
	 */
	private double calculateTotalCost(HashMap<Product, Integer> map)
	{
		double cost  = 0;
		
		for(Map.Entry<Product, Integer> entry : map.entrySet())
		{
			cost = cost + (entry.getKey().getPrice() * entry.getValue());
		}
		
		return cost;
	}
	
	

}
