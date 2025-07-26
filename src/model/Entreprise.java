package model;

public class Entreprise extends ClientDirect {
	private String numTva;
	
	public Entreprise(String nom, String gsm, String email, String numTva) {
		super(nom, gsm, email);
		this.numTva = numTva;
	}

}
