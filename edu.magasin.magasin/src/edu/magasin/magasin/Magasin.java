package edu.magasin.magasin;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.magasin.api.ClientAPI;
import edu.magasin.api.ListeProduits;
import edu.magasin.api.MagasinCoteClient;
import edu.magasin.api.Produit;
import edu.magasin.api.VenteProduitException;

@Component
public class Magasin implements MagasinCoteClient {

	private ListeProduits listeProduits = new ListeProduits();
	private List<ClientAPI> clients = new ArrayList<ClientAPI>();
	
	@Activate
	public void onStart(){
		System.out.println("Magasin cr�� !");
	}
	
	@Reference(
			service = ClientAPI.class,
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC
	)
	public void bindClient(ClientAPI client) {
		this.clients.add(client);
	}
	
	public void unbindClient(ClientAPI client) {
		this.clients.remove(client);
	}
	
	@Override
	public ListeProduits getListeProduits() {
		return this.listeProduits;
	}

	@Override
	public void vendreProduit(Produit produit, int quantite) throws VenteProduitException {
		Produit produitAVendre = null;
		for(Produit aProduit : this.listeProduits.getProduits()) {
			if(produit.getId() == aProduit.getId()) {
				produitAVendre = aProduit;
				break;
			}
		}
		
		if(produitAVendre.getQuantite() > quantite || produitAVendre == null) {
			throw new VenteProduitException("Le produit n'a plus de stock ou n'existe pas.");
		}
		
		produitAVendre.setQuantite(produitAVendre.getQuantite() - quantite);
		listeProduits.notifyChange();
	}
	
	
}
