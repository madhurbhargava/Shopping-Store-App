package com.thoughtworks.retailstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Displays details of the product
 * @author mbhargava
 *
 */
public class ProductDetailsActivity extends RetailStoreApplicationActivity {
	
	/*
	 * (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_details);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.thoughtworks.retailstore.RetailStoreApplicationActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		final String name = getIntent().getStringExtra(Constants.KEY_NAME);
		final String id = getIntent().getStringExtra(Constants.KEY_ID);
		final String price = getIntent().getStringExtra(Constants.KEY_PRICE);
		final String picture = getIntent().getStringExtra(Constants.KEY_IMAGE);
		final String details = getIntent().getStringExtra(Constants.KEY_DETAILS);
		
		int resID = getResources().getIdentifier(picture , "drawable", getPackageName());
		((ImageView)findViewById(R.id.image)).setImageResource(resID);
		((TextView)findViewById(R.id.price)).setText(name+" - Rs. "+price);
		((TextView)findViewById(R.id.details)).setText(details);
		((Button)findViewById(R.id.button_addtocart)).setOnClickListener(new View.OnClickListener() {
			
			/*
			 * (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Product product = new Product();
				product.setId(Integer.parseInt(id));
				product.setDescription(details);
				product.setPicture(picture);
				product.setName(name);
				product.setPrice(Double.parseDouble(price));
				Cart.getInstance(ProductDetailsActivity.this).addORUpdateItem(product);
				
			}
		});
	}

}
