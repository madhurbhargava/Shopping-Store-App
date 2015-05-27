package com.thoughtworks.retailstore;

import java.io.Serializable;

import com.thoughtworks.retailstore.Category.CategoryName;

/**
 * Product or retail item used by the retail store.
 * Each product has and id, image, name, description and price 
 * @author mbhargava
 *
 */
public class Product implements Serializable{

	/**
	 * Default generated for serialization
	 */
	private static final long serialVersionUID = -3041845784275051024L;
	
	private int id;
	private String name;
	private String description;
	private double price;
	private String picture;
	private CategoryName category;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category.toString();
	}

	public void setCategory(CategoryName name) {
		this.category = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description="
				+ description + ", price=" + price + "]";
	}	
}
