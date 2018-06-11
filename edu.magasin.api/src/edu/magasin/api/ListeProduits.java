package edu.magasin.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ListeProduits extends Observable {

	private List<Produit> produits = new ArrayList<Produit>();

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	
	public void ajouterProduit(Produit produit) {
		this.produits.add(produit);
		this.notifyChange();
	}
	
	public void notifyChange() {
		this.setChanged();
		this.notifyObservers();
	}
}
