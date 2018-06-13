package edu.store.store;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.store.api.ClientStore;
import edu.store.api.ProductsList;
import edu.store.api.StoreForClient;
import edu.store.api.Product;
import edu.store.api.SellProductException;

@Component
public class Store implements StoreForClient {

	private ProductsList productsList = new ProductsList();
	private List<ClientStore> clients = new ArrayList<ClientStore>();
	
	@Activate
	public void onStart(){
		System.out.println("Store created !");
	}
	
	@Reference(
			service = ClientStore.class,
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC
	)
	public void bindClient(ClientStore client) {
		this.clients.add(client);
	}
	
	public void unbindClient(ClientStore client) {
		this.clients.remove(client);
	}
	
	@Override
	public ProductsList getProductsList() {
		return this.productsList;
	}

	@Override
	public void sellProduct(Product aProduct, int aQuantity) throws SellProductException {
		Product productToSell = null;
		for(Product vProduit : this.productsList.getProducts()) {
			if(vProduit.getId() == aProduct.getId()) {
				productToSell = aProduct;
				break;
			}
		}
		
		if(productToSell.getQuantity() > aQuantity || productToSell == null) {
			throw new SellProductException("The product is no more in stock or doesn't exist.");
		}
		
		productToSell.setQuantity(productToSell.getQuantity() - aQuantity);
		productsList.notifyChange();
	}
	
	
}