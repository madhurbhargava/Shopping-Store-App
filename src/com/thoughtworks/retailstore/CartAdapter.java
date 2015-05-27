package com.thoughtworks.retailstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Underlying adapter for Cart items list
 * @author mbhargava
 *
 */
public class CartAdapter extends BaseAdapter {
	
	private Activity mContext;
	private HashMap<Product, Integer> mCartItems;
	private LayoutInflater mInflater;
	
	ArrayList<CartItem> mItemList;
	private CartAdapter mAdapter = null;
	
	
	
	public CartAdapter(Activity context, HashMap<Product, Integer> items) {

		mContext = context;
		mCartItems = items;
		mInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		
		mItemList = new ArrayList<CartAdapter.CartItem>();
		for(Entry entry: items.entrySet())
		{
			CartItem cit = new CartItem();
			cit.product = (Product)entry.getKey();
			cit.count = (Integer)entry.getValue();
			mItemList.add(cit);
		}
		
		mAdapter = this;
		
	}
	
	public class CartItem
	{
		public Product product;
		public Integer count;
		
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCartItems.size();
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItemList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * Viewholder to support the listitem view
	 */
	public static class ViewHolder {
		public ImageView cancel;
		public TextView count;
		public TextView name;
		public TextView price;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (convertView == null) 
		{
			convertView = mInflater.inflate(
					R.layout.cart_list_item, null, false);
			holder = new ViewHolder();
			holder.count = (TextView) convertView
					.findViewById(R.id.item_count);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_name);
			holder.price = (TextView) convertView
					.findViewById(R.id.item_price);
			
			holder.cancel = (ImageView) convertView.findViewById(R.id.item_cancel);
			
			
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.count.setText(""+mItemList.get(position).count+"x");
		holder.name.setTag(mItemList.get(position).product);
		holder.name.setText(""+mItemList.get(position).product.getName());
		holder.name.setOnClickListener(new OnClickListener() {
			
			/*
			 * (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Product product = (Product)v.getTag();
				Intent intent = new Intent(mContext, ProductDetailsActivity.class);
				intent.putExtra(Constants.KEY_PRICE, Double.toString(product.getPrice()));
				intent.putExtra(Constants.KEY_IMAGE, product.getPicture());
				intent.putExtra(Constants.KEY_ID, Integer.toString(product.getId()));
				intent.putExtra(Constants.KEY_NAME, product.getName());
				intent.putExtra(Constants.KEY_DETAILS, product.getDescription());
				mContext.startActivity(intent);
				
			}
		});
		holder.price.setText("Rs. "+mItemList.get(position).product.getPrice()*mItemList.get(position).count);
		holder.cancel.setTag(mItemList.get(position).product);
		holder.cancel.setOnClickListener(new OnClickListener() {
			
			/*
			 * (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//show confirmation alert
				
				showConfirmationDialog(v);
				
			}
		});
		notifyDataSetChanged();
		return convertView;
	}
	
	/**
	 * Shows delete item confirmation when cancel/cross on a cart item is clicked
	 * @param listItem
	 */
	private void showConfirmationDialog(final View listItem)
	{
		new AlertDialog.Builder(mContext)
	    .setTitle(mContext.getResources().getString(R.string.title_delete_cart_item))
	    .setMessage(mContext.getResources().getString(R.string.message_delete_cart_item))
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	Cart.getInstance(mContext).removeItem((Product)listItem.getTag());
				mAdapter.notifyDataSetChanged();
				
				//refresh
				mContext.finish();
				mContext.startActivity(mContext.getIntent());
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}

}
