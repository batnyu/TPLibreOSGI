package edu.store.client;

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

@Component(factory = "edu.magasin.client.factory")
public class Client implements ClientStore {
	
	private String nom;
	
	private StoreForClient storeForClient;

	private List<Product> boughtProducts = new ArrayList<Product>();
	
	@Activate
	public void onStart(){
		System.out.println("Un client est arriv� !");
	}
	
	@Reference(
			service = StoreForClient.class, 
			cardinality = ReferenceCardinality.MANDATORY,
			unbind = "unbindMagasinClient",
			policy = ReferencePolicy.DYNAMIC
			)
	public void bindMagasinClient(StoreForClient aStoreForClient){
		this.storeForClient = aStoreForClient;
	}
	
	public void unbindMagasinClient(StoreForClient aStoreForClient){
		this.storeForClient = null;
	}

	@Override
	public ProductsList getProductsList() {
		return this.storeForClient.getProductsList();
	}

	@Override
	public void buyProduct(Product aProduct, int aQuantity) throws SellProductException {
		this.storeForClient.sellProduct(aProduct, aQuantity);
		
		Product newProduct = new Product(aProduct.getId(),aProduct.getLabel(),aProduct.getPrice(),aQuantity);
		this.boughtProducts.add(newProduct);
		
	}

	@Override
	public String getName() {
		return this.nom;
	}

	@Override
	public double getTotalAmount() {
		Double total = (double) 0;
		for(Product vProduct : this.boughtProducts){
			total = total + vProduct.getPrice() * vProduct.getQuantity();
		}
		return total;
	}
}