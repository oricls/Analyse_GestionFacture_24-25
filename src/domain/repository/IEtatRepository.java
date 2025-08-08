package domain.repository;

import java.util.List;

import domain.model.Etat;

public interface IEtatRepository {
	boolean addEtat(Etat newEtat);
	boolean updateEtat(String nom, String newNom, String newCouleur) ;
	boolean deleteEtat(String nom);
	Etat getEtatByName(String nom);
	Etat getEtatByid(int id);
	List<Etat> getEtats();
}
