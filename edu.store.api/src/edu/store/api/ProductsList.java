package edu.store.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ProductsList extends Observable {

	private List<Product> products = new ArrayList<Product>();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> aProducts) {
		this.products = aProducts;
	}
	
	public void addProduct(Product aProduct) {
		this.products.add(aProduct);
		this.notifyChange();
	}
	
	public void notifyChange() {
		this.setChanged();
		this.notifyObservers();
	}
}
