package model;

public class Fournisseur extends Partenaire {
	private String numTva;
	
	public Fournisseur(String nom, String numTva) {
		super(nom);
		this.numTva = numTva;
	}

}
