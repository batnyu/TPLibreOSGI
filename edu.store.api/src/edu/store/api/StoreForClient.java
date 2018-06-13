package edu.store.api;

public interface StoreForClient {
	
	public void sellProduct(Product aProduct, int aQuantity) throws SellProductException;
	
	public ProductsList getProductsList();
}
