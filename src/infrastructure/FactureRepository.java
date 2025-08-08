package infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.model.ClientDirect;
import domain.model.ClientFinal;
import domain.model.Etat;
import domain.model.Facture;
import domain.model.Fournisseur;
import domain.model.Type;
import domain.repository.IFactureRepository;

public class FactureRepository implements IFactureRepository {
	private List<Facture> factures = new ArrayList<>();
	
	
	public FactureRepository(ClientDirect clientDirect) {
		factures = factureInit(clientDirect);		
	}
	
	public boolean addFacture(Facture facture) {
		factures.add(facture);
		return true;
	}
	
	public boolean archiverFacture(int facture) {
		factures.remove(facture);
		return true;
	}
	
	public Set<Facture> getFactureByDetails(String nom, Etat etat){
		Set<Facture> result = new HashSet<>();
		for (var f : factures) {
			if (f.getNumFacture().equals(nom)) {
				result.add(f);
			}
		}
		return result;
	}

	
	public List<Facture> getFactureOfClient(ClientDirect client) {
	    return factures.stream().filter(f -> f.getClientDirect().equals(client)).toList();
	}
	
	
	public List<Facture> rechercherFactures(String nomFacture,LocalDate dateFacture,Type type,Etat etat) {
	    return factures.stream()
	        .filter(f -> nomFacture == null || f.getNumFacture().equalsIgnoreCase(nomFacture))
	        .filter(f -> dateFacture == null || f.getDateFacture().equals(dateFacture))
	        .filter(f -> type == null || (f.getType() != null && f.getType().getNomType().equalsIgnoreCase(type.getNomType())))
	        .filter(f -> etat == null || (f.getEtat() != null && f.getEtat().getNom().equalsIgnoreCase(etat.getNom())))
	        .toList();
	}
	
	public List<Facture> getAllFactures() {
	    return factures;
	}
	
	
	private static List<Facture> factureInit(ClientDirect clientDirect){
		Type rentree = new Type("Rentrée");
		Type depense = new Type("Dépense");
		Etat etatPayee =new Etat("Payée", "#000000");
		Etat etatImpayee = new Etat("Non-payée", "#FF0505");

		Fournisseur btb = new Fournisseur("BetonEtTombe", "BE0000");
		Fournisseur mr = new Fournisseur("MaisonRenovation", "BE9999");
		ClientFinal jm = new ClientFinal("jean michel");
		ClientFinal jc = new ClientFinal("jean claude");
		List<Facture> factures = new ArrayList<>();
		
		factures.add(new Facture("F001", LocalDate.of(2025, 1, 10), LocalDate.of(2100, 2, 10), 1000.0, 21.0, "Chantier 1", depense, etatPayee, btb, clientDirect));
		factures.add(new Facture("F002", LocalDate.of(2025, 1, 15), LocalDate.of(2025, 2, 15), 2000.0, 21.0, "Chantier 2", rentree, etatImpayee, mr, clientDirect));
		factures.add(new Facture("F003", LocalDate.of(2025, 2, 5), LocalDate.of(2026, 3, 5), 1500.0, 6.0, "Chantier 3", depense, etatImpayee, jc, clientDirect));
		factures.add(new Facture("F004", LocalDate.of(2025, 2, 20), LocalDate.of(2027, 3, 20), 800.0, 21.0, "Chantier 4", depense, etatPayee, jm, clientDirect));
		factures.add(new Facture("F005", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 4, 1), 1200.0, 21.0, "Chantier 5", rentree, etatPayee, btb, clientDirect));
		factures.add(new Facture("F006", LocalDate.of(2025, 3, 10), LocalDate.of(2025, 4, 10), 500.0, 0.0, "Chantier 6", depense, etatImpayee, jm, clientDirect));
		factures.add(new Facture("F007", LocalDate.of(2025, 3, 20), LocalDate.of(2025, 4, 20), 2500.0, 21.0, "Chantier 7", rentree, etatPayee, btb, clientDirect));
		return factures;
	}
}
