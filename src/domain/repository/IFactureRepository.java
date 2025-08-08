package domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import domain.model.ClientDirect;
import domain.model.Etat;
import domain.model.Facture;
import domain.model.Type;

public interface IFactureRepository {
	boolean addFacture(Facture facture);
	boolean archiverFacture(int facture);
	Set<Facture> getFactureByDetails(String nom, Etat etat);
	List<Facture> getFactureOfClient(ClientDirect client);
	List<Facture> rechercherFactures(String nomFacture,LocalDate dateFacture,Type type,Etat etat);
	List<Facture> getAllFactures();
}
