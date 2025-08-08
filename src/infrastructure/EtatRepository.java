package infrastructure;

import java.util.ArrayList;
import java.util.List;
import domain.model.Etat;
import domain.repository.IEtatRepository;

public class EtatRepository implements IEtatRepository {
	private List<Etat> etats = new ArrayList<>();
	
	public EtatRepository() {
		etats = etatsInit();
	}
	
	public boolean addEtat(Etat newEtat) {
		boolean existe = etats.stream().anyMatch(e -> e.getNom().equalsIgnoreCase(newEtat.getNom()));
		
        if (!existe) {
            etats.add(newEtat);
            return true;
        }
        return false;
	}
	
	public boolean updateEtat(String nom, String newNom, String newCouleur) {
		for (int i = 0; i < etats.size(); i++) {
			var e = etats.get(i);
			
            if (e.getNom().equalsIgnoreCase(nom)) {
            	e.setEtat(newNom, newCouleur);
            	
                etats.set(i, e);
                return true;
            }
        }
        return false;
	}
	
	public boolean deleteEtat(String nom) {
		for (int i = 0; i < etats.size(); i++) {
			var e = etats.get(i);
			if (e.getNom().equalsIgnoreCase(nom)) {
				etats.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public Etat getEtatByName(String nom) {
		return etats.stream().filter(e -> e.getNom().equalsIgnoreCase(nom)).findFirst().orElse(null);
	}
	
	public Etat getEtatByid(int id) {
		return etats.get(id);
	}

	public List<Etat> getEtats() {
		return etats;
	}
	
	private static List<Etat> etatsInit() {
		List<Etat> etats = new ArrayList<>();
		etats.add(new Etat("Payée", "#000000"));
		etats.add(new Etat("Non-payée", "#FF0505"));
		
		return etats;
	}
}
