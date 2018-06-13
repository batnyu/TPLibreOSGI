package edu.magasin.client;

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

@Component(factory = "edu.magasin.client.factory")
public class Client implements ClientAPI {
	
	private String nom;
	
	private MagasinCoteClient magasinClient;

	private List<Produit> produitsAchetes = new ArrayList<Produit>();
	
	@Activate
	public void onStart(){
		System.out.println("Un client est arrivé !");
	}
	
	@Reference(
			service = MagasinCoteClient.class, 
			cardinality = ReferenceCardinality.MANDATORY,
			unbind = "unbindMagasinClient",
			policy = ReferencePolicy.DYNAMIC
			)
	public void bindMagasinClient(MagasinCoteClient magasinClient){
		this.magasinClient = magasinClient;
	}
	
	public void unbindMagasinClient(MagasinCoteClient magasinClient){
		this.magasinClient = null;
	}

	@Override
	public ListeProduits getListeProduits() {
		return this.magasinClient.getListeProduits();
	}

	@Override
	public void acheterProduit(Produit produit, int quantite) throws VenteProduitException {
		this.magasinClient.vendreProduit(produit, quantite);
		
		Produit nouveauProduit = new Produit(produit.getId(),produit.getLibelle(),produit.getPrix(),quantite);
		this.produitsAchetes.add(nouveauProduit);
		
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public double getPrixTotalAchats() {
		Double total = (double) 0;
		for(Produit produit : this.produitsAchetes){
			total = total + produit.getPrix() * produit.getQuantite();
		}
		return total;
	}
}
