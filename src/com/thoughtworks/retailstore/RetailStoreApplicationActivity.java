package com.thoughtworks.retailstore;

import java.util.HashMap;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Master Activity for the app, handles actionbar
 * @author mbhargava
 *
 */
public class RetailStoreApplicationActivity extends ActionBarActivity 
{
	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!(this instanceof CategoryListActivity))
		{
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        if((this instanceof CartDetailsActivity))
        {
        	menu.findItem(R.id.action_cart).setVisible(false);
        }
        
        return (super.onCreateOptionsMenu(menu));
    }

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        if (id == R.id.action_cart) {
        	HashMap<Product, Integer> map = Cart.getInstance(this).getCartItems();
        	if(map.size() > 0)
        	{
        		Intent intent = new Intent(this, CartDetailsActivity.class);
        		startActivity(intent);
        	}
        	else
        	{
        		Toast.makeText(this, this.getResources().
        				getString(R.string.message_cart_empty), Toast.LENGTH_SHORT).show();
        	}
        	
            return true;
        }
        else if(id  == android.R.id.home)
        {
            // back icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
