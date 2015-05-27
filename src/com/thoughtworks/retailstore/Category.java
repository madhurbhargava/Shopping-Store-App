package com.thoughtworks.retailstore;

import java.util.ArrayList;

/**
 * Each category is a list of products
 * @author mbhargava
 *
 */
public class Category {
	
	public static enum CategoryName
	{
		Electronics("Electronics"),
		Furniture("Furniture");
		
		private final String name;

    
		private CategoryName(final String name) 
		{
			this.name = name;
		}

    
    
		public String toString() {
			return name;
		}
	}
	
	private CategoryName name;
	private ArrayList<Product> products;
	
	public Category(CategoryName type)
	{
		this.name = type;
	}
	
	public ArrayList<Product> getProducts() {
        return products;
    }

    
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * @param item The item to add
     */
    public void addProduct(Product item){
        if(null == products){
            products = new ArrayList<Product>();
        }
        products.add(item);
    }
    
    public CategoryName getCategory()
    {
    	return name;
    }

}
