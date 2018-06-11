package edu.magasin.api;

public interface MagasinCoteClient {
	
	public void vendreProduit(Produit produit, int quantite) throws VenteProduitException;
	
	public ListeProduits getListeProduits();
}