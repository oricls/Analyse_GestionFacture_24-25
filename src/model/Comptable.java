package model;

public class Comptable {

	private String nomPrenom;
    private String email;
    private String mdp;

    public Comptable(String nomPrenom, String email, String mdp) {
        this.nomPrenom = nomPrenom;
        this.email = email;
        this.mdp = mdp;
    }


    public String getNomPrenom() {
        return nomPrenom;
    }

    public String getEmail() {
        return email;
    }
    
    public String getMdp() {
		return mdp;
	}

}
