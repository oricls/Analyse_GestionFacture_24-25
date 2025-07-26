package model;

public class Etat {
	private String nomEtat;
	private String couleurHex;
	
	public Etat (String nomEtat, String couleurHex) {
		this.nomEtat =nomEtat;
		this.couleurHex = couleurHex;
	}

	public String getNom() {
		return nomEtat;
	}

	public String getCouleurHex() {
		return couleurHex;
	}	
	
	public void setEtat(String nomEtat, String couleurHex) {
		this.nomEtat = nomEtat;
		this.couleurHex = couleurHex;
	}
	
	
	@Override
    public String toString() {
        return String.format("%s (%s)", this.nomEtat, this.couleurHex);
    }
}
