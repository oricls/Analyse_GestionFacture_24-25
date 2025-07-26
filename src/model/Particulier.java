package model;

public class Particulier extends ClientDirect  {
	private String numNational;
	private String prenom;
	
	public Particulier(String nom, String gsm, String email) {
		super(nom, gsm, email);
		this.numNational = numNational;
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", prenom, numNational);
	}

}
