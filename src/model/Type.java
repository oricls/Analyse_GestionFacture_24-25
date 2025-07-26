package model;

public class Type {
	private String nomType;
	
	public Type(String nomType) {
		this.nomType = nomType;
	}

	public String getNomType() {
		return nomType;
	}

	public void setNomType(String nomType) {
		this.nomType = nomType;
	}
	
	@Override
    public String toString() {
        return String.format("%s", this.nomType);
    }
}
