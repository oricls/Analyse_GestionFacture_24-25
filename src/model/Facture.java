package model;
import java.time.LocalDate;

public class Facture {
	private String numFacture;
	private LocalDate dateFacture;
	private LocalDate dateEcheance;
	private double montantHtva;
	private double tauxTva;
	
	private Comptable comptable;
	private String chantier;
	private Type type;
	private Etat etat;
	private Partenaire partenaire;
	private ClientDirect clientDirect;
	
	
	public Facture (String numFacture, 
					LocalDate dateFacture, LocalDate dateEcheance, 
					double montantHtva, double tauxTva,
					String chantier, Type type, Etat etat, 
					Partenaire partenaire, ClientDirect clientDirect) {
		
		this.numFacture = numFacture;
        this.dateFacture = dateFacture;
        this.dateEcheance = dateEcheance;
        this.montantHtva = montantHtva;
        this.tauxTva = tauxTva;
        
        this.chantier = chantier;
        this.type = type;
        this.etat = etat;
        this.partenaire = partenaire;
        this.clientDirect = clientDirect;
	}
	
	public double calculerMontantTva() {
		return montantHtva * (tauxTva / 100.0);
	}
	
	public double calculerMontantTtc() {
		return montantHtva + calculerMontantTva();
	}
	
	
    
    public String getNumFacture() {
		return numFacture;
	}

	public void setNumFacture(String numFacture) {
		this.numFacture = numFacture;
	}

	public LocalDate getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(LocalDate dateFacture) {
		this.dateFacture = dateFacture;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public double getMontantHtva() {
		return montantHtva;
	}

	public void setMontantHtva(double montantHtva) {
		this.montantHtva = montantHtva;
	}

	public double getTauxTva() {
		return tauxTva;
	}

	public void setTauxTva(double tauxTva) {
		this.tauxTva = tauxTva;
	}

	public Comptable getComptable() {
		return comptable;
	}

	public void setComptable(Comptable comptable) {
		this.comptable = comptable;
	}

	public String getChantier() {
		return chantier;
	}

	public void setChantier(String chantier) {
		this.chantier = chantier;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Partenaire getPartenaire() {
		return partenaire;
	}

	public void setPartenaire(Partenaire partenaire) {
		this.partenaire = partenaire;
	}

	public ClientDirect getClientDirect() {
		return clientDirect;
	}

	public void setClientDirect(ClientDirect clientDirect) {
		this.clientDirect = clientDirect;
	}

}
