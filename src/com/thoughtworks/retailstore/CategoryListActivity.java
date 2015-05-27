package com.thoughtworks.retailstore;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * List of retail item categories that app supports
 * @author mbhargava
 *
 */
public class CategoryListActivity extends RetailStoreApplicationActivity
{

	/*
	 * (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        final ArrayList<Category> catList = Inventory.getInstance().getInventory(this);
        String[] values = new String[catList.size()];
        for(int i = 0; i < values.length; i++)
        {
        	values[i] = catList.get(i).getCategory().name();
        }
       
        final ListView listView = (ListView) findViewById(R.id.categoryList);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.category_list_item, R.id.category_name, values));
        listView.setOnItemClickListener(new OnItemClickListener(){
           
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String catName = ((TextView)view.findViewById(R.id.category_name)).getText().toString();
			     
				
			    ArrayList<Product> prodList = new ArrayList<Product>();
			    //get the product list for this category
			    for(int i = 0; i < catList.size(); i++)
			    {
			    	if(catList.get(i).getCategory().toString().equals(catName))
			    	{
			    		prodList = catList.get(i).getProducts();
			    		break;
			    	}
			    	
			    }
			    
			    Intent intent = new Intent(CategoryListActivity.this, ProductListActivity.class);
			   
			    Bundle bundleObject = new Bundle();
			    bundleObject.putSerializable(Constants.KEY_PRODUCTS, prodList);
			                     
			    // Put Bundle in to Intent and call start Activity
			    intent.putExtras(bundleObject);
			    
			    startActivity(intent);
			}
        });
    }
}
