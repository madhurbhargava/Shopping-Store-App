package com.thoughtworks.retailstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.thoughtworks.retailstore.Category.CategoryName;

import android.content.Context;
import android.content.res.AssetManager;

/**
 * Manages the retail store inventory. Items are maintained at assets/inventory.
 * Uses XmlPull Parser to pull out the inventory
 * @author mbhargava
 *
 */
public class Inventory {
	
	//list of items grouped by categories
	private ArrayList<Category> mInventoryList = null;
	
	private static Inventory sInventory = new Inventory();
	
	//items mapped by their respective id(s)
	private HashMap<Integer, Product> mInventoryMap = new HashMap<Integer, Product>();
	
	private Inventory()
	{
		
	}
	
	public static Inventory getInstance()
	{
		return sInventory;
	}
	
	public ArrayList<Category> getInventory(Context ctx) 
	{
		
		XmlPullParserFactory factory = null;
		try 
		{
			factory = XmlPullParserFactory.newInstance();
		} 
		catch (XmlPullParserException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return null;
		}
		
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
		try 
		{
			xpp = factory.newPullParser();
		} 
		catch (XmlPullParserException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

        try 
        {
        	
        	AssetManager assetManager = ctx.getAssets();
            InputStream is = assetManager.open("inventory.xml");
			xpp.setInput(new StringReader (AppUtil.getStringFromInputStream(is)));
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
        int eventType = -1;
		try 
		{
			eventType = xpp.getEventType();
		} 
		catch (XmlPullParserException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		Category category = null;
		Product product = null;
		String tagName = null;
		
        while (eventType != XmlPullParser.END_DOCUMENT) 
        {
        	if(eventType == XmlPullParser.START_DOCUMENT) 
        	{
        		
        	} 
        	else if(eventType == XmlPullParser.END_DOCUMENT) 
        	{
        		
        	} 
        	else if(eventType == XmlPullParser.START_TAG) 
        	{
        		
        		tagName = xpp.getName();
        		if(xpp.getName().equals(Constants.XML_TAG_INVENTORY))
        		{
        			//inventory reading started
        			mInventoryList = new ArrayList<Category>();
        		}
        		else if(xpp.getName().equals(Constants.XML_TAG_CATEGORY))
        		{
        			//new category found
        			String type = xpp.getAttributeValue(null, Constants.XML_ATTRIBUTE_TYPE);
        			category = new Category(CategoryName.valueOf(type));
        		}
        		else if(xpp.getName().equals(Constants.XML_TAG_ITEM))
        		{
        			//new product found
        			product = new Product();
        			product.setCategory(category.getCategory());
        		}
        			
        	} 
        	else if(eventType == XmlPullParser.END_TAG) 
        	{
        		tagName = null;
        		if(xpp.getName().equals(Constants.XML_TAG_CATEGORY))
        		{
        			mInventoryList.add(category);
        		}
        		else if(xpp.getName().equals(Constants.XML_TAG_ITEM))
        		{
        			category.addProduct(product);
        			mInventoryMap.put(product.getId(), product);
        		}
        	} 
        	else if(eventType == XmlPullParser.TEXT) 
        	{
        		if(tagName != null)
        		{
        			if(tagName.equals(Constants.XML_TAG_ID))
        			{
        				if(AppUtil.isInteger(xpp.getText()))
        				{
        					product.setId(Integer.parseInt(xpp.getText()));
        				}
        			}
        			else if(tagName.equals(Constants.XML_TAG_NAME))
        			{
        				product.setName(xpp.getText());
        			}
        			else if(tagName.equals(Constants.XML_TAG_COST))
        			{
        				if(AppUtil.isDouble(xpp.getText()))
        				{
        					product.setPrice(Double.parseDouble(xpp.getText()));
        				}
        			}
        			else if(tagName.equals(Constants.XML_TAG_DESCRIPTION))
        			{
        				product.setDescription(xpp.getText());
        			}
        			else if(tagName.equals(Constants.XML_TAG_PICTURE))
        			{
        				product.setPicture(xpp.getText());
        			}
        		}
        	}
        	
        	try 
        	{
        		eventType = xpp.next();
        	} 
        	catch (XmlPullParserException e) 
        	{
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} 
        	catch (IOException e) 
        	{
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
		return mInventoryList;
	}

	public HashMap<Integer, Product> getItemisedMapping()
	{
		return mInventoryMap;
	}
	

}
