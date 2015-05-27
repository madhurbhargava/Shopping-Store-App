package com.thoughtworks.retailstore;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * List of products in each category
 * @author mbhargava
 *
 */
public class ProductListActivity extends RetailStoreApplicationActivity 
{
	/*
	 * (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        // Get the Bundle Object        
        Bundle bundleObject = getIntent().getExtras();
                 
        // Get ArrayList Bundle
        @SuppressWarnings("unchecked")
		final ArrayList<Product> prodList = (ArrayList<Product>) bundleObject.getSerializable(Constants.KEY_PRODUCTS);
        String[] values = new String[prodList.size()];
        for(int i = 0; i < values.length; i++)
        {
        	values[i] = prodList.get(i).getName();
        }
       
        final ListView listView = (ListView) findViewById(R.id.categoryList);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.category_list_item, R.id.category_name, values));
        listView.setOnItemClickListener(new OnItemClickListener(){
           
        	/*
        	 * (non-Javadoc)
        	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
        	 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Product product = prodList.get(position);
			    Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
				intent.putExtra(Constants.KEY_PRICE, Double.toString(product.getPrice()));
				intent.putExtra(Constants.KEY_IMAGE, product.getPicture());
				intent.putExtra(Constants.KEY_ID, Integer.toString(product.getId()));
				intent.putExtra(Constants.KEY_NAME, product.getName());
				intent.putExtra(Constants.KEY_DETAILS, product.getDescription());
				startActivity(intent);
			}
        });
    }
}
