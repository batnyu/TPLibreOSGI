package edu.store.api;

public interface ClientStore {
	
	public ProductsList getProductsList();
	
	public void buyProduct(Product aProduct, int aQuantity) throws SellProductException;
	
	public String getName();
	
	public double getTotalAmount();
	
}
